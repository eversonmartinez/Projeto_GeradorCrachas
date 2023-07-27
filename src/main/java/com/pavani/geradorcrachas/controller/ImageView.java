package com.pavani.geradorcrachas.controller;

import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;
import com.pavani.geradorcrachas.model.entities.LayoutCracha;
import com.pavani.geradorcrachas.service.GeradorCrachaService;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
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

        if(fc.getRenderResponse()){
            return new DefaultStreamedContent();
        }

        GeradorCrachaService service = new GeradorCrachaService(layout);

        try {
            buffer = service.gerarCracha(informacoes);
        }catch (IOException ex){
            buffer = GeradorCrachaService.crachaVazioSemLayout();
        }

        InputStream input = new ByteArrayInputStream(buffer);
        StreamedContent stream = DefaultStreamedContent.builder().contentType("image/jpeg").stream(() -> input).build();//DefaultStreamedContent(input, "image/jpeg");
        return stream;
    }


}
