package com.pavani.controller;

import com.pavani.dao.CrachaFuncionarioDao;
import com.pavani.model.entities.CrachaFuncionario;
import com.pavani.util.MessageUtil;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import java.io.IOException;
import java.io.InputStream;

@ManagedBean(name = "CrachaFuncionarioController")
@SessionScoped
public class CrachaFuncionarioController {

    private CrachaFuncionarioDao dao;
    private CrachaFuncionario objeto;

    private UploadedFile file;

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
                InputStream inputStream = file.getInputStream();
                objeto.setFoto(inputStream);
                MessageUtil.infoMessage("File Uploaded");
            }catch(IOException e){
                e.printStackTrace();
                MessageUtil.errorMessage("Não foi possível salvar o arquivo");
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
}
