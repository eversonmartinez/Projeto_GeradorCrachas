package com.pavani.geradorcrachas.controller;

import com.pavani.geradorcrachas.dao.CrachaFuncionarioDao;
import com.pavani.geradorcrachas.dao.LayoutCrachaDao;
import com.pavani.geradorcrachas.model.entities.Cracha;
import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;
import com.pavani.geradorcrachas.model.entities.LayoutCracha;
import com.pavani.geradorcrachas.model.exceptions.GeradorCrachaException;
import com.pavani.geradorcrachas.service.ConversorBytesService;
import com.pavani.geradorcrachas.util.MessageUtil;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.nio.file.Files;

@ManagedBean(name = "CrachaFuncionarioController")
@SessionScoped

public class CrachaFuncionarioController implements Serializable {

    private CrachaFuncionarioDao dao;
    private CrachaFuncionario objeto;

    //Criar a possibilidade de alterar o layout
    private LayoutCracha layout;

    private transient UploadedFile file;
    private transient CroppedImage croppedFile;
    private transient CroppedImage backupCroppedFile;
    private transient byte[] fotoFuncionarioRecortada;
    private boolean cropperAtivo = false;

    private StreamedContent previewCropper;

    public void saveCrop(){
        this.objeto.setFoto(fotoFuncionarioRecortada);
    }

    public void crop(){
        if(croppedFile == null)
            return;

        String imageName = getRandomImageName();

        FileImageOutputStream imageOutput;
        try{
            File cropped = new File(System.getProperty("java.io.tmpdir"), imageName);
            imageOutput = new FileImageOutputStream(cropped);
            imageOutput.write(croppedFile.getBytes(), 0, croppedFile.getBytes().length);
            imageOutput.close();
            this.fotoFuncionarioRecortada = Files.readAllBytes(cropped.toPath());
            croppedFile = backupCroppedFile;
            backupCroppedFile = null;
        }catch(Exception e){
            MessageUtil.errorMessage("Erro", "Falha no serviço de recorte de imagens");
        }

        MessageUtil.infoMessage("Sucesso");
    }

    private String getRandomImageName(){
        int i = (int) (Math.random() * 50);

        return "crop-" + i;
    }

    public CroppedImage getCroppedFile() {
        return croppedFile;
    }

    public void setCroppedFile(CroppedImage croppedFile) {
        this.croppedFile = croppedFile;
    }

    public CrachaFuncionarioController(){
        dao=new CrachaFuncionarioDao();
        objeto = new CrachaFuncionario();
        layout = new LayoutCrachaDao().getDefault();
    }

    public String listar(){
        return "/crachas-funcionarios/listar?faces-redirect=true";
    }

    public String novo(){
        objeto = new CrachaFuncionario();
        layout = new LayoutCrachaDao().getDefault();
        fotoVazia();
        return "formulario?faces-redirect=true";
    }

    public String salvar(){
        boolean gravou;
        upload();
        gerarCracha();
        gravou = (objeto.getId() == null ? dao.persist(objeto) : dao.merge(objeto));
        if(gravou){
            MessageUtil.infoMessage(dao.getMensagem());
            limparController();
            return "/funcionarios/listar?faces-redirect=true";
        }

        else{
            MessageUtil.errorMessage(dao.getMensagem());
            file = null;
            return("formulario?faces-redirect=true");
        }
    }

    public String editar(Long id){
        if(objeto!=null){
            objeto = null;
        }
        objeto = dao.findById(id);
        layout = new LayoutCrachaDao().getDefault();
        return("/crachas-funcionarios/formulario?faces-redirect=true");
    }

    public void remover(Long id){
        objeto = dao.findById(id);
        if(dao.remove(objeto)){
            MessageUtil.infoMessage(dao.getMensagem());
        }
        else{
            MessageUtil.errorMessage(dao.getMensagem());
        }
    }

    public String cancelar(){
        limparController();
        return "/crachas-funcionarios/listar?faces-redirect=true";
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file){
        this.file = file;
    }

    public boolean upload(){
        if(file != null && fotoFuncionarioRecortada == null) {
                try {
                    byte[] arquivoByte = ConversorBytesService.toByteArrayUsingIS(file.getInputStream());

                    objeto.setFoto(arquivoByte);

                    MessageUtil.infoMessage("File Uploaded");
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    MessageUtil.errorMessage("Não foi possível salvar o arquivo");
                    return false;
                }
            }
        return false;
    }

    public void uploadTemporario(FileUploadEvent event){
        file = event.getFile();
        if(file == null){
            MessageUtil.errorMessage("Erro", "O arquivo" + file.getFileName() + "não pôde ser carregado!");
            return;
        }
        fotoFuncionarioRecortada = null;
        MessageUtil.infoMessage("Sucesso", file.getFileName() + " carregado temporariamente para pré-visualização");
    }

//    private byte[] toByteArrayUsingJava(InputStream inputS) throws IOException{
//        ByteArrayOutputStream byteArrayOtpS = new ByteArrayOutputStream();
//        int readByte = inputS.read();
//        while (readByte != -1){
//            byteArrayOtpS.write(readByte);
//            readByte = inputS.read();
//        }
//        return byteArrayOtpS.toByteArray();
//    }

    public String mostrarCracha(){
        if(objeto.getId()!=null)
            return("/images/crachas/" + objeto.getId());

        return("/images/crachas/none");
    }

    public void handlePreviewCracha(){
        upload();
        if(objeto.getFoto() == null)
            fotoVazia();

        if(croppedFile != null)
            backupCroppedFile = croppedFile;
    }

    public boolean gerarCracha(){
        try{
            if(objeto.getFoto() == null)
                return false;
            Cracha cracha = new Cracha(objeto, layout);
            objeto.setCracha(cracha);
            return true;
        }
        catch (IOException ioe){
            MessageUtil.errorMessage("Houve um erro ao manipular arquivos no servidor");
            ioe.printStackTrace();
            return false;
        }
        catch (GeradorCrachaException gce){
            MessageUtil.errorMessage(gce.getMessage());
            return false;
        }
        catch (Exception ex){
            ex.printStackTrace();
            MessageUtil.errorMessage("Não foi possível gerar o crachá");
            return false;
        }
    }
    public String downloadCracha(){
        if(objeto.getId() == null || objeto.getFoto() == null){
            MessageUtil.errorMessage("Não existe uma imagem de crachá para ser baixada");
            return null;
        }

        FacesContext fc = FacesContext.getCurrentInstance();
        return ("../download/images/crachas/" + objeto.getId() + "?faces-redirect=true");
    }

    private void fotoVazia(){
        if(objeto.getFoto() == null){
            try {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                InputStream is = classLoader.getResourceAsStream("layout-cracha\\no-image.png");
                objeto.setFoto(ConversorBytesService.toByteArrayUsingIS(is));
            }catch(IOException ioe) {
                return;
            }
        }
    }

    public CrachaFuncionario objetoPreview(){
        if(fotoFuncionarioRecortada == null)
            return this.objeto;

        CrachaFuncionario crachaCropped = this.objeto;
        crachaCropped.setFoto(fotoFuncionarioRecortada);
        return crachaCropped;
    }

    public StreamedContent getPreviewCropper(){
        return this.previewCropper;
    }

    public void showPreviewCropper(){
        this.previewCropper = null;
        this.previewCropper = new ImageView().cropper(this.objeto);
    }

    public CrachaFuncionarioDao getDao(){
        return dao;
    }

    public CrachaFuncionario getObjeto() {
        return objeto;
    }

    public void setObjeto(CrachaFuncionario objeto) {
        this.objeto = objeto;
    }

    public LayoutCracha getLayout() {
        return layout;
    }

    private void limparController(){
        objeto = null;
        file = null;
        croppedFile = null;
        fotoFuncionarioRecortada = null;
        cropperAtivo = false;
        this.previewCropper = null;
    }
}
