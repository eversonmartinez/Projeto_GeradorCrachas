package com.pavani.service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class TestService {

    public static void main(String[] args){
        try{
            File foto = new File("src\\main\\resources\\layout-cracha\\layout.png");
            BufferedImage template = ImageIO.read(foto);
            System.out.println(foto.getAbsolutePath());
            URI path = foto.toURI();

            String paths = path.getPath();
            System.out.println(paths);

        }catch (Exception ex){
            System.out.println("Erou");
        }
    }

}
