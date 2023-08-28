package com.pavani.geradorcrachas.service;

import com.pavani.geradorcrachas.dao.LayoutCrachaDao;
import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;
import com.pavani.geradorcrachas.model.entities.LayoutCracha;
import com.pavani.geradorcrachas.model.exceptions.GeradorCrachaException;
import com.pavani.geradorcrachas.util.FontUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;

public class GeradorCrachaService {

    private LayoutCracha layout;

    //private File layout;

    public GeradorCrachaService(){
        this.layout = new LayoutCrachaDao().getDefault();
    }

    public GeradorCrachaService(LayoutCracha layout){
        this.layout = layout;
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

    public void preview(){}

    public byte[] gerarCracha(CrachaFuncionario informacoes) throws IOException {
        if(informacoes.getFoto() == null)
            throw new GeradorCrachaException("Imagem de funcionário inválida");

        return gerarCracha(
                informacoes.getNomeVisivel(),
                informacoes.getAdmissaoFuncionario(),
                informacoes.getCodigoFuncionario(),
                informacoes.getApelido(),
                informacoes.getFoto()
        );
    }

    public byte[] gerarTesteLayout() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("layout-cracha\\no-image.png");
        byte[] foto = (ConversorBytesService.toByteArrayUsingIS(is));
        return gerarCracha("Campo nome", LocalDate.now(), 9999L, "Apelido", foto);
    }

    public byte[] gerarCracha(String nome, LocalDate admissao, Long codigo, String apelido, byte[] fotoCracha) throws IOException {

        if(layout == null)
            throw new GeradorCrachaException("Layout não foi encontrado no servidor");
        if(this.layout.getImagem() == null)
            throw new GeradorCrachaException("Por algum motivo a imagem do layout não pode ser encontrada");
        if(fotoCracha == null)
            throw new GeradorCrachaException("Imagem de funcionário inválida");

        String nome1 = randomName();
        String nome2 = randomName();
        while(nome2.equals(nome1));
            nome2=randomName();

        File imagemLayout = new File(System.getProperty("java.io.tmpdir"), "nome1");
        FileOutputStream out = new FileOutputStream(imagemLayout);
        out.write(this.layout.getImagem());

        File foto = new File(System.getProperty("java.io.tmpdir"), "nome2");
        foto.createNewFile();
        out = new FileOutputStream(foto);
        out.write(fotoCracha);

        BufferedImage template = ImageIO.read(imagemLayout);
        BufferedImage fotoFuncionario = ImageIO.read(foto);

        Graphics2D graphicImage = template.createGraphics();

        int x = 0;
        int y = 1;

        graphicImage.setFont(FontUtil.customFontBold(layout.getTamanhoFonteNome()));
        graphicImage.setPaint(Color.white);
        graphicImage.drawString(nome, layout.getPosicaoNome()[x], layout.getPosicaoNome()[y]);

//        graphicImage.setFont(FontUtil.customFont(layout.getTamanhoFonteDescricao()));
        graphicImage.setFont(FontUtil.fontSofiaPro(layout.getTamanhoFonteDescricao()));
        graphicImage.drawString(escreverData(admissao), layout.getPosicaoAdmissao()[x], layout.getPosicaoAdmissao()[y]);


        graphicImage.drawString(String.valueOf(codigo), layout.getPosicaoCodigo()[x], layout.getPosicaoCodigo()[y]);

        if(apelido.length() < 9)
            graphicImage.setFont(FontUtil.customFontBold(layout.getTamanhoFonteApelido()));

        else{
            int diferenca = apelido.length() - 8;
            graphicImage.setFont(FontUtil.customFontBold(layout.getTamanhoFonteApelido() - (8*diferenca)));
        }

        graphicImage.drawString(apelido, layout.getPosicaoApelido()[x], layout.getPosicaoApelido()[y]);

        graphicImage.drawImage(fotoFuncionario, layout.getPosicaoImagem()[x], layout.getPosicaoImagem()[y],
                layout.getTamanhoImagem()[x], layout.getTamanhoImagem()[y], null);

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

    public static byte[] crachaVazioSemLayout(){
        return new LayoutCrachaDao().getDefault().getImagem();
    }

    private String randomName(){
        int i = (int) (Math.random() * 50);
        return "temporary" + i;
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
