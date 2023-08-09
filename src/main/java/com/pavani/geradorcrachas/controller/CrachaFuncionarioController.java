package com.pavani.geradorcrachas.controller;

import com.pavani.geradorcrachas.dao.CrachaFuncionarioDao;
import com.pavani.geradorcrachas.dao.LayoutCrachaDao;
import com.pavani.geradorcrachas.model.entities.Cracha;
import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;
import com.pavani.geradorcrachas.model.entities.LayoutCracha;
import com.pavani.geradorcrachas.model.exceptions.GeradorCrachaException;
import com.pavani.geradorcrachas.util.MessageUtil;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import java.io.*;

@ManagedBean(name = "CrachaFuncionarioController")
@SessionScoped

public class CrachaFuncionarioController implements Serializable {

    private CrachaFuncionarioDao dao;
    private CrachaFuncionario objeto;

    //Criar a possibilidade de alterar o layout
    private LayoutCracha layout;

    private transient UploadedFile file;

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
            return "listar?faces-redirect=true";
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
        return("formulario?faces-redirect=true");
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
        return "listar?faces-redirect=true";
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file){
        this.file = file;
    }

    public boolean upload(){
        if(file != null){
            try {
                byte[] arquivoByte = toByteArrayUsingJava(file.getInputStream());

                objeto.setFoto(arquivoByte);

                MessageUtil.infoMessage("File Uploaded");
                return true;
            }catch(IOException e){
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
            MessageUtil.infoMessage("Sucesso", file.getFileName() + " carregado temporariamente para pré-visualização");
            return;
        }

        MessageUtil.errorMessage("Erro", "O arquivo" + file.getFileName() + "não pôde ser carregado!");
    }

    private byte[] toByteArrayUsingJava(InputStream inputS) throws IOException{
        ByteArrayOutputStream byteArrayOtpS = new ByteArrayOutputStream();
        int readByte = inputS.read();
        while (readByte != -1){
            byteArrayOtpS.write(readByte);
            readByte = inputS.read();
        }
        return byteArrayOtpS.toByteArray();
    }

    //TEMPORARIAMENTE SENDO UTILIZADO PARA PEGAR UMA FOTO DO BANCO E SALVAR NA MINHA MÁQUINA
    public void testFoto() throws IOException {
        dao.testeGetFoto(objeto.getId());
    }

    public String mostrarCracha(){
        if(objeto.getId()!=null)
            return("/images/crachas/" + objeto.getId());

        return("/images/crachas/none");
    }

    public StreamedContent previewCracha(){
        upload();
        ImageView view = new ImageView();
        if(this.objeto.getFoto() == null)
            fotoVazia();

        return view.previewCracha(this.objeto, this.layout);
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
                objeto.setFoto(toByteArrayUsingJava(is));
            }catch(IOException ioe) {
                return;
            }
        }
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
    }
}
