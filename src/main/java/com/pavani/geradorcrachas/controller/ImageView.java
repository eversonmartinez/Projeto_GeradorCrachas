package com.pavani.geradorcrachas.controller;

import com.pavani.geradorcrachas.dao.CrachaDao;
import com.pavani.geradorcrachas.dao.CrachaFuncionarioDao;
import com.pavani.geradorcrachas.model.entities.Cracha;
import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;
import com.pavani.geradorcrachas.model.entities.LayoutCracha;
import com.pavani.geradorcrachas.service.GeradorCrachaService;
import com.sun.tools.jconsole.JConsoleContext;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseId;
import jakarta.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Named
@RequestScoped
@ManagedBean (name = "ImageView")
public class ImageView implements Serializable {

    public ImageView (){
    }

    public StreamedContent previewCracha(CrachaFuncionario informacoes, LayoutCracha layout){
        byte[] buffer;
        FacesContext fc = FacesContext.getCurrentInstance();

        if(fc.getRenderResponse() || fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE){
            return new DefaultStreamedContent();
        }

        GeradorCrachaService service = new GeradorCrachaService(layout);

        try {
            String tempNome = informacoes.getNomeVisivel() == null ? " " : informacoes.getNomeVisivel();
            LocalDate tempAdmissao = informacoes.getAdmissaoFuncionario() == null ? LocalDate.now() : informacoes.getAdmissaoFuncionario();
            Long tempCodigo = informacoes.getCodigoFuncionario() == null ? 0L : informacoes.getCodigoFuncionario();
            String tempApelido = informacoes.getApelido() == null ? " " : informacoes.getApelido();
            byte[] tempFoto = informacoes.getFoto();

            buffer = service.gerarCracha(tempNome, tempAdmissao, tempCodigo, tempApelido, tempFoto);
        }catch (IOException ex){
            buffer = GeradorCrachaService.crachaVazioSemLayout();
        }

        InputStream input = new ByteArrayInputStream(buffer);
        StreamedContent stream = DefaultStreamedContent.builder().contentType("image/png").stream(() -> input).build();//DefaultStreamedContent(input, "image/jpeg");
        return stream;
    }

    public StreamedContent previewCracha(LayoutCracha layout){
        byte[] buffer;
        FacesContext fc = FacesContext.getCurrentInstance();

        if(fc.getRenderResponse() || fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE){
            return new DefaultStreamedContent();
        }

        if(layout.getImagem() == null)
            return noImage();

        GeradorCrachaService service = new GeradorCrachaService(layout);

        try {
            buffer = service.gerarTesteLayout();
        }catch (IOException ex){
            buffer = GeradorCrachaService.crachaVazioSemLayout();
        }

        InputStream input = new ByteArrayInputStream(buffer);
        StreamedContent stream = DefaultStreamedContent.builder().contentType("image/png").stream(() -> input).build();//DefaultStreamedContent(input, "image/jpeg");
        return stream;
    }



    public StreamedContent downloadCracha(Long id){
        byte[] buffer;
        FacesContext fc = FacesContext.getCurrentInstance();

        if(fc.getRenderResponse()){
            return new DefaultStreamedContent();
        }

        Cracha cracha = new CrachaDao().findById(id);

        buffer = cracha.getCrachaFinalizado();

        InputStream input = new ByteArrayInputStream(buffer);

        String nomeArquivo = "cracha-" + new CrachaFuncionarioDao().findById(id).getNomeVisivel();
        StreamedContent stream = DefaultStreamedContent.builder().name(nomeArquivo).contentType("image/png").stream(() -> input).build();//DefaultStreamedContent(input, "image/jpeg");
        return stream;
    }

    public StreamedContent cropper(CrachaFuncionario informacoes){
        byte[] buffer;
        FacesContext fc = FacesContext.getCurrentInstance();

        if(fc.getRenderResponse() || fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE){
            return new DefaultStreamedContent();
        }

        //GeradorCrachaService service = new GeradorCrachaService(layout);

        //try {
            //String tempNome = informacoes.getNomeVisivel() == null ? " " : informacoes.getNomeVisivel();
            //LocalDate tempAdmissao = informacoes.getAdmissaoFuncionario() == null ? LocalDate.now() : informacoes.getAdmissaoFuncionario();
            //Long tempCodigo = informacoes.getCodigoFuncionario() == null ? 0L : informacoes.getCodigoFuncionario();
            //String tempApelido = informacoes.getApelido() == null ? " " : informacoes.getApelido();
            byte[] tempFoto = informacoes.getFoto();

            //buffer = service.gerarCracha(tempNome, tempAdmissao, tempCodigo, tempApelido, tempFoto);
        //}catch (IOException ex){
        //    buffer = GeradorCrachaService.crachaVazioSemLayout();
        //}
            buffer = tempFoto;
        InputStream input = new ByteArrayInputStream(buffer);
        StreamedContent stream = DefaultStreamedContent.builder().contentType("image/png").stream(() -> input).build();//DefaultStreamedContent(input, "image/jpeg");
        return stream;
    }

    public static StreamedContent visualizacaoBytes(byte[] bytes){
        byte[] buffer;
        FacesContext fc = FacesContext.getCurrentInstance();

        if(fc.getRenderResponse() || fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE){
            return new DefaultStreamedContent();
        }

        buffer = bytes;

        InputStream input = new ByteArrayInputStream(buffer);
        StreamedContent stream = DefaultStreamedContent.builder().contentType("image/png").stream(() -> input).build();//DefaultStreamedContent(input, "image/jpeg");
        return stream;
    }

    public static StreamedContent visualizacaoInputStream(InputStream input){
        FacesContext fc = FacesContext.getCurrentInstance();

        if(fc.getRenderResponse() || fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE){
            return new DefaultStreamedContent();
        }

        return DefaultStreamedContent.builder().contentType("image/png").stream(() -> input).build();//DefaultStreamedContent(input, "image/jpeg");
    }

    public static StreamedContent noImage(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("layout-cracha\\no-image.png");
        return visualizacaoInputStream(is);
    }

}
