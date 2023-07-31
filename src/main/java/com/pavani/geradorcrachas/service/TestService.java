package com.pavani.geradorcrachas.service;

import com.pavani.geradorcrachas.dao.CrachaDao;
import com.pavani.geradorcrachas.dao.LayoutCrachaDao;
import com.pavani.geradorcrachas.model.entities.Cracha;
import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;
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

public class TestService {

    public static void main(String[] args) {
        try {
            LayoutCrachaDao dao = new LayoutCrachaDao();
            LayoutCracha teste = dao.getDefault();    //puxando do banco de dados

            CrachaFuncionario objeto = new CrachaFuncionario();
            objeto.setNomeVisivel("Yasmin da Conceição");
            objeto.setCodigoFuncionario(923L);
            objeto.setAdmissaoFuncionario(LocalDate.now());
            objeto.setApelido("Yaz");
            Path path = Paths.get("C:/Users/Administrador/Desktop/colaborador.png");
            objeto.setFoto(Files.readAllBytes(path));

            byte[] imagemFinal;
            GeradorCrachaService service = new GeradorCrachaService(teste);
            imagemFinal = service.gerarCracha(objeto);

            File file = new File("C:/Users/Administrador/Desktop/testeGerar.png");
            FileOutputStream out = new FileOutputStream(file);
            out.write(imagemFinal);
            out.close();
        } catch (Exception ex) {
            System.out.println("Erro");
        }
    }
}
