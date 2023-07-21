package com.pavani.geradorcrachas.controller;

import com.pavani.geradorcrachas.dao.CrachaFuncionarioDao;
import com.pavani.geradorcrachas.dao.LayoutCrachaDao;
import com.pavani.geradorcrachas.model.entities.Cracha;
import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;
import com.pavani.geradorcrachas.model.entities.LayoutCracha;
import com.pavani.geradorcrachas.model.exceptions.GeradorCrachaException;
import com.pavani.geradorcrachas.service.GeradorCrachaService;
import com.pavani.geradorcrachas.util.MessageUtil;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@ManagedBean(name = "CrachaFuncionarioController")
@SessionScoped
public class CrachaFuncionarioController {

    private CrachaFuncionarioDao dao;
    private CrachaFuncionario objeto;

    private transient UploadedFile file;

    public CrachaFuncionarioController(){
        dao=new CrachaFuncionarioDao();
        objeto = new CrachaFuncionario();}

    public String listar(){
        return "/crachas-funcionarios/listar?faces-redirect=true";
    }

    public String novo(){
        objeto = new CrachaFuncionario();
        return "formulario?faces-redirect=true";
    }

    public String salvar(){
        boolean gravou;
        upload();
        gerarCracha();
        gravou = (objeto.getId() == null ? dao.persist(objeto) : dao.merge(objeto));
        if(gravou){
            MessageUtil.infoMessage(dao.getMensagem());
            file = null;
            return "listar?faces-redirect=true";
        }

        else{
            MessageUtil.errorMessage(dao.getMensagem());
            file = null;
            return("formulario?faces-redirect=true");
        }
    }

    public String editar(Long id){
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

    public void uploadTemporario(FileUploadEvent event) throws IOException {
        file = event.getFile();
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

    public boolean gerarCracha(){
        try{
            if(objeto.getFoto() == null)
                return false;
            LayoutCracha layout = new LayoutCrachaDao().getDefault();
            GeradorCrachaService service = new GeradorCrachaService(layout);
            byte[] crachaFinalizado = service.gerarCracha(objeto);

            Cracha cracha = new Cracha(objeto, layout, crachaFinalizado);
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

    public CrachaFuncionarioDao getDao(){
        return dao;
    }

    public CrachaFuncionario getObjeto() {
        return objeto;
    }

    public void setObjeto(CrachaFuncionario objeto) {
        this.objeto = objeto;
    }
}
