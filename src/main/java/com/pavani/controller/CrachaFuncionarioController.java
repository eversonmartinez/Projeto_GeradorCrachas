package com.pavani.controller;

import com.pavani.dao.CrachaFuncionarioDao;
import com.pavani.model.entities.CrachaFuncionario;
import com.pavani.service.GeradorCrachaService;
import com.pavani.util.MessageUtil;
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
        gravou = (objeto.getId() == null ? dao.persist(objeto) : dao.merge(objeto));
        if(gravou){
            MessageUtil.infoMessage(dao.getMensagem());
            return "listar?faces-redirect=true";
        }

        else{
            MessageUtil.errorMessage(dao.getMensagem());
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

    public void upload(){
        if(file != null){
            try {
                byte[] arquivoByte = toByteArrayUsingJava(file.getInputStream());

                objeto.setFoto(arquivoByte);

                MessageUtil.infoMessage("File Uploaded");
            }catch(IOException e){
                e.printStackTrace();
                MessageUtil.errorMessage("Não foi possível salvar o arquivo");
            }
        }
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

    public byte[] mostrarCracha(){

        //        try{
//            GeradorCrachaService service = new GeradorCrachaService();
//            if(objeto.getId()!=null && objeto.getFoto()!= null) {
//                return service.gerarCracha(dao.findById(objeto.getId()));
//            }
//            else {
//                return service.crachaVazio();
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return null;
//            }

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
