package com.pavani.geradorcrachas.controller;

import com.pavani.geradorcrachas.dao.FuncionarioDao;
import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;
import com.pavani.geradorcrachas.model.entities.Funcionario;
import com.pavani.geradorcrachas.util.MessageUtil;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@ManagedBean(name = "FuncionarioController")
@SessionScoped

public class FuncionarioController implements Serializable {

    private FuncionarioDao dao;
    private Funcionario objeto;


    public FuncionarioController(){
        dao=new FuncionarioDao();
        dao.setOrdem("nome");
        objeto = new Funcionario();
    }

    public String listar(){
        return "/funcionarios/listar?faces-redirect=true";
    }

    public String novo(){
        objeto = new Funcionario();
        return "formulario?faces-redirect=true";
    }

    public String salvar(){
        objeto.getCracha().atualizarInformacoes();
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
        return "listar?faces-redirect=true";
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

    public String mostrarCracha(){
        if(objeto.getId()!=null)
            return("/images/crachas/" + objeto.getId());

        return("/images/crachas/none");
    }

    public boolean gerarCracha(){

            CrachaFuncionario cracha = new CrachaFuncionario(objeto);

            return true;
    }

    public FuncionarioDao getDao(){
        return dao;
    }

    public Funcionario getObjeto() {
        return objeto;
    }

    public void setObjeto(Funcionario objeto) {
        this.objeto = objeto;
    }

}
