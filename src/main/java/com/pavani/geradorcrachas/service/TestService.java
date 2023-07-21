package com.pavani.geradorcrachas.service;

import com.pavani.geradorcrachas.dao.CrachaDao;
import com.pavani.geradorcrachas.model.entities.Cracha;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

public class TestService {

    public static void main(String[] args){
        CrachaDao dao = new CrachaDao();
        Cracha cracha = dao.findById(2L);
        System.out.println(cracha.getCrachaFuncionario().getNomeVisivel());
    }

}
