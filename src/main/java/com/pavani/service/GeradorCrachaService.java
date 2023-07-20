package com.pavani.service;

import com.pavani.dao.LayoutCrachaDao;
import com.pavani.model.entities.CrachaFuncionario;
import com.pavani.model.entities.LayoutCracha;
import jakarta.ejb.Local;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.facelets.Facelet;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class GeradorCrachaService {

    private LayoutCracha layout;

    //private File layout;

    public GeradorCrachaService() throws IOException {
        this.layout = new LayoutCrachaDao().getDefault();
    }

    public LayoutCracha getLayout(){
        return this.layout;
    }

//    public void setLayout(File layout) throws IllegalArgumentException{
//        if(!layout.isDirectory()){
//            if(isAPhoto(layout)){
//                this.layout = layout;
//                return;
//            }
//        }
//
//        throw new IllegalArgumentException("Apenas imagens podem ser utilizadas como layout");
//    }

    public void setLayout(LayoutCracha layout){
        this.layout = layout;
    }


//    public void puxarLayout() throws IOException {
//        String path = this.getClass().getResource("../../../../").getPath();
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        String path = ec.getRealPath("/");
//        layout = new File(System.getProperty("java.io.tmpdir"), "temporary1");
//        System.out.println(layout.getAbsolutePath());
//
//        FileOutputStream out = new FileOutputStream(layout);
//        LayoutCracha arquivoBanco = new LayoutCrachaDao().getDefault();
//        out.write(arquivoBanco.getImagem());
//        if(layout == null)
//            throw new IOException("Layout não foi encontrado no servidor");
//    }

    public void preview(){}

    public byte[] gerarCracha(CrachaFuncionario informacoes) throws IOException {
        return gerarCracha(
                informacoes.getNomeVisivel(),
                informacoes.getAdmissaoFuncionario(),
                informacoes.getCodigoFuncionario(),
                informacoes.getApelido(),
                informacoes.getFoto()
        );
    }

    public byte[] gerarCracha(String nome, LocalDate admissao, Long codigo, String apelido, byte[] fotoCracha) throws IOException {

        if(layout == null)
            throw new NullPointerException("Layout não foi encontrado no servidor");
        if(this.layout.getImagem() == null)
            throw new NullPointerException("Por algum motivo a imagem do layout não pode ser encontrada");

        File imagemLayout = new File(System.getProperty("java.io.tmpdir"), "temporary1");
        FileOutputStream out = new FileOutputStream(imagemLayout);
        out.write(this.layout.getImagem());

        File foto = new File(System.getProperty("java.io.tmpdir"), "temporary2");
        foto.createNewFile();
        out = new FileOutputStream(foto);
        out.write(fotoCracha);

        BufferedImage template = ImageIO.read(imagemLayout);
        BufferedImage fotoFuncionario = ImageIO.read(foto);

        Graphics2D graphicImage = template.createGraphics();

        int x = 0;
        int y = 1;

        graphicImage.setFont(new Font("Akhbar", Font.BOLD, 60));
        graphicImage.setPaint(Color.white);
        graphicImage.drawString(nome, layout.getPosicaoNome()[x], layout.getPosicaoNome()[y]);
        graphicImage.drawString(escreverData(admissao), layout.getPosicaoAdmissao()[x], layout.getPosicaoAdmissao()[y]);
        graphicImage.drawString(String.valueOf(codigo), layout.getPosicaoCodigo()[x], layout.getPosicaoCodigo()[y]);

        graphicImage.setFont(new Font("Akhbar", Font.BOLD, 168));
        graphicImage.drawString(apelido, layout.getPosicaoApelido()[x], layout.getPosicaoApelido()[y]);

        graphicImage.drawImage(fotoFuncionario, layout.getPosicaoImagem()[x], layout.getPosicaoImagem()[y], 1024, 986, null);

        graphicImage.dispose();

        ImageIO.write(template, "PNG", foto);

        return Files.readAllBytes(foto.toPath());
    }

    public byte[] crachaVazio(){
        if(layout == null)
            throw new NullPointerException("Layout não foi encontrado no servidor");

        if(this.layout.getImagem() == null)
            throw new NullPointerException("Por algum motivo a imagem do layout não pode ser encontrada");

        return layout.getImagem();
    }

    private String escreverData(LocalDate data){
        int mes = data.getMonth().getValue();
        int ano = data.getYear();
        if(mes == 1)
            return "Janeiro, " + ano;
        if(mes == 2)
            return "Fevereiro, " + ano;
        if(mes == 3)
            return "Março, " + ano;
        if(mes == 4)
            return "Abril, " + ano;
        if(mes == 5)
            return "Maio, " + ano;
        if(mes == 6)
            return "Junho, " + ano;
        if(mes == 7)
            return "Julho, " + ano;
        if(mes == 8)
            return "Agosto, " + ano;
        if(mes == 9)
            return "Setembro, " + ano;
        if(mes == 10)
            return "Outubro, " + ano;
        if(mes == 11)
            return "Novembro, " + ano;
        if(mes == 12)
            return "Dezembro, " + ano;

        return String.valueOf(ano);
    }

    public boolean isAPhoto(File file){
        int index = file.getName().lastIndexOf(".");

        if(index<=0)
            return false;

        String extensao = file.getName().substring(index+1);
        if(extensao.equals("jpg"))
            return true;

        if(extensao.equals("png"))
            return true;

        if(extensao.equals("jpeg"))
            return true;

        return false;
    }
}
