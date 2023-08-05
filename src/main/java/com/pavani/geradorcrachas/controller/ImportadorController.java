package com.pavani.geradorcrachas.controller;

import com.pavani.geradorcrachas.dao.FuncionarioDao;
import com.pavani.geradorcrachas.model.entities.Funcionario;
import com.pavani.geradorcrachas.service.ImportadorFuncionarioService;
import com.pavani.geradorcrachas.util.MessageUtil;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@ManagedBean(name = "ImportadorController")
@ViewScoped
public class ImportadorController {

    private FuncionarioDao dao;
    private transient UploadedFile file;

    public ImportadorController(){
        dao = new FuncionarioDao();
    }

    public String acessar(){
        return "/importador/menu?faces-redirect=true";
    }

    public String importarArquivo(){
        if(file != null){
            try {

                ImportadorFuncionarioService service = new ImportadorFuncionarioService();
                List<Funcionario> funcionarios = service.importarTxt(file.getInputStream()); //criar conversao de uploaded file para file (ou modificar service)

                MessageUtil.infoMessage(service.getMessage());

                int erroDao = 0;
                for(Funcionario f : funcionarios){
                    if(novoFuncionario(f))
                        if(!dao.persist(f))
                            erroDao++;
                }

                if(erroDao == 0)
                    MessageUtil.infoMessage("File Uploaded");
                else{
                    MessageUtil.errorMessage("Erro ao importar");
                }

            }catch(IOException e){
                e.printStackTrace();
                MessageUtil.errorMessage("Não foi possível salvar o arquivo");
            }
        }
        return "menu?faces-redirect=true";
    }

    /*Aqui eu poderia utilizar stream para fazer uma consulta mais leve. Estudar isso futuramente.*/
    public boolean novoFuncionario(Funcionario importado){  //verificar se o funcionario ja existe no banco
        List<Funcionario> existentes = dao.getListaTodos();
        boolean existe = false;

        for (Funcionario f : existentes){       //estou comparando apenas os códigos
            if(f.getCodigo() == importado.getCodigo()) {
                existe = true;
                break;
            }
        }
        return (!existe);
    }

    public String uploadMessage(){
        if(file == null){
            return "Nenhum arquivo carregado";
        }

        return "Arquivo carregado";
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file){
        this.file = file;
    }

    public void upload(FileUploadEvent event) throws IOException {
        file = event.getFile();
    }
}
