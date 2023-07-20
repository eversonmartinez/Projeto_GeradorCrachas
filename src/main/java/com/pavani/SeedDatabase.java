package com.pavani;

import com.pavani.dao.LayoutCrachaDao;
import com.pavani.model.entities.LayoutCracha;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//Utilizei esse métoodo para inserir uma imagem de crachá dentro do próprio banco de dados.
//Pode ser rodado a mão, para facilitar o processo.
public class SeedDatabase {

    public static void main(String[] args){
        layoutCrachaPadrao();
    }

    private static void layoutCrachaPadrao(){
        try {
            LayoutCracha padrao = new LayoutCracha("Padrão");
            Path path = Paths.get("src\\main\\resources\\layout-cracha\\layout.png");
            padrao.setImagem(Files.readAllBytes(path));
            LayoutCrachaDao dao = new LayoutCrachaDao();
            dao.persist(padrao);

            LayoutCracha teste = dao.getDefault();    //puxando do banco de dados
            File file = new File("C:/Users/Administrador/Desktop/teste.png");
            FileOutputStream out = new FileOutputStream(file);
            out.write(teste.getImagem());
            out.close();
        }catch(IOException ex){
            //posso colocar uma mensagem parecida com a do javafx
            System.out.println("Imagem não encontrada no servidor");
        }
    }
}
