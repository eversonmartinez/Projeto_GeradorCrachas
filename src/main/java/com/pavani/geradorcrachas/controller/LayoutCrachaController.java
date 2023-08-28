package com.pavani.geradorcrachas.controller;

import com.pavani.geradorcrachas.dao.LayoutCrachaDao;
import com.pavani.geradorcrachas.model.entities.LayoutCracha;
import com.pavani.geradorcrachas.service.ConversorBytesService;
import com.pavani.geradorcrachas.util.MessageUtil;
import jakarta.faces.bean.CustomScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;

import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.NoResultException;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import java.io.*;



@ManagedBean(name = "LayoutCrachaController")
@SessionScoped

public class LayoutCrachaController implements Serializable {

    private LayoutCrachaDao dao;
    private LayoutCracha objeto;
    private Boolean modoPreview = false;
    private transient UploadedFile file;

    public LayoutCrachaController(){
        dao=new LayoutCrachaDao();
        try {
            objeto = dao.getDefault();
        }catch (NoResultException nre){
            objeto = new LayoutCracha();
        }

    }

    public String acessar(){
        return "/layouts/formulario?faces-redirect=true";
    }

    public String novo(){
        objeto = new LayoutCracha();
        return "/layouts/formulario?faces-redirect=true";
    }

    public String salvar(){
        boolean gravou;
        upload();
        gravou = (objeto.getId() == null ? dao.persist(objeto) : dao.merge(objeto));
        if(gravou){
            MessageUtil.infoMessage(dao.getMensagem());
            limparController();
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return "/layouts/formulario?faces-redirect=true";
        }

        else{
            MessageUtil.errorMessage(dao.getMensagem());
            file = null;
            return "/layouts/formulario?faces-redirect=true";
        }
    }

    public String editar(Long id){
        if(objeto!=null){
            objeto = null;
        }
        objeto = dao.findById(id);
        return("/layouts/formulario?faces-redirect=true");
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
        return "/layouts/formulario?faces-redirect=true";
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file){
        this.file = file;
    }

    public boolean upload(){
        if(file != null) {
                try {
                    byte[] arquivoByte = ConversorBytesService.toByteArrayUsingIS(file.getInputStream());

                    objeto.setImagem(arquivoByte);

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

        MessageUtil.infoMessage("Sucesso", file.getFileName() + " carregado temporariamente para pré-visualização");
    }

    public String mostrarLayout(){
        if(objeto.getId()!=null)
            return("/images/layouts/" + objeto.getId());

        return("/images/crachas/none");
    }

//    public StreamedContent mostrarLayout(){
//        if(modoPreview){
//            try {
//                if (objeto.getImagem() == null)
//                    MessageUtil.errorMessage("Nenhuma imagem enviada ainda!");
//                else
//                return new ImageView().previewCracha(this.objeto);
//            }catch (Exception ex){
//
//            }
//        }
//        if(objeto.getId()==null || objeto.getImagem() == null) {
//            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//            InputStream is = classLoader.getResourceAsStream("layout-cracha\\no-image.png");
//            return ImageView.visualizacaoInputStream(is);
//        }
//
//        return ImageView.visualizacaoBytes(objeto.getImagem());
//    }

    public void handlePreviewLayout(){
        upload();
        modoPreview = true;
//        PrimeFaces.current().ajax().update("previewImage");
    }

    private void fotoVazia(){
        if(objeto.getImagem() == null){
            try {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                InputStream is = classLoader.getResourceAsStream("layout-cracha\\no-image.png");
                objeto.setImagem(ConversorBytesService.toByteArrayUsingIS(file.getInputStream()));
            }catch(IOException ioe) {
                return;
            }
        }
    }

    public LayoutCrachaDao getDao(){
        return dao;
    }

    public LayoutCracha getObjeto() {
        return objeto;
    }

    public void setObjeto(LayoutCracha objeto) {
        this.objeto = objeto;
    }

    private void limparController(){
        objeto = dao.getDefault();
        file = null;
        modoPreview = false;
    }

    public Boolean getModoPreview() {
        return modoPreview;
    }
}
