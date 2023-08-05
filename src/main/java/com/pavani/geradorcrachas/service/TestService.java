package com.pavani.geradorcrachas.service;

import com.pavani.geradorcrachas.dao.CrachaDao;
import com.pavani.geradorcrachas.dao.LayoutCrachaDao;
import com.pavani.geradorcrachas.model.entities.Cracha;
import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;
import com.pavani.geradorcrachas.model.entities.Funcionario;
import com.pavani.geradorcrachas.model.entities.LayoutCracha;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class TestService {

    public static void main(String[] args) {
        Funcionario funcionario = new Funcionario("Everson Miranda Martinez", LocalDate.now(), 123L);
        System.out.println(funcionario.getCracha().getNomeVisivel());
    }
}
