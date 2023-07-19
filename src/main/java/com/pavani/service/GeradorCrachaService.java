package com.pavani.service;

import com.pavani.model.entities.CrachaFuncionario;
import jakarta.ejb.Local;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class GeradorCrachaService {

    private int[] posicaoNome = new int[] {97, 243};
    private int[] posicaoAdmissao = new int[] {93, 308};
    private int[] posicaoCodigo = new int[] {105, 372};
    private int[] posicaoApelido = new int[] {100, 582};
    private int[] posicaoImagem = new int[] {0, 1623};

    private File layout;

    public GeradorCrachaService() throws IOException {
        puxarLayout();
    }

    public File getLayout(){
        return this.layout;
    }

    public void setLayout(File layout) throws IllegalArgumentException{
        if(!layout.isDirectory()){
            if(isAPhoto(layout)){
                this.layout = layout;
                return;
            }
        }

        throw new IllegalArgumentException("Apenas imagens podem ser utilizadas como layout");
    }

    public int[] getPosicaoNome() {
        return posicaoNome;
    }

    public void setPosicaoNome(int[] posicaoNome) {
        this.posicaoNome = posicaoNome;
    }

    public int[] getPosicaoAdmissao() {
        return posicaoAdmissao;
    }

    public void setPosicaoAdmissao(int[] posicaoAdmissao) {
        this.posicaoAdmissao = posicaoAdmissao;
    }

    public int[] getPosicaoCodigo() {
        return posicaoCodigo;
    }

    public void setPosicaoCodigo(int[] posicaoCodigo) {
        this.posicaoCodigo = posicaoCodigo;
    }

    public int[] getPosicaoApelido() {
        return posicaoApelido;
    }

    public void setPosicaoApelido(int[] posicaoApelido) {
        this.posicaoApelido = posicaoApelido;
    }

    public int[] getPosicaoImagem() {
        return posicaoImagem;
    }

    public void setPosicaoImagem(int[] posicaoImagem) {
        this.posicaoImagem = posicaoImagem;
    }

    public void puxarLayout() throws IOException {
        String path = this.getClass().getResource("../../../../").getPath();
        layout = new File(path + "\\resources\\layout-cracha\\layout.png");
        System.out.println(layout.getAbsolutePath());
        if(layout == null)
            throw new IOException("Layout não foi encontrado no servidor");
    }

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
        System.out.println(layout.getAbsolutePath());
        BufferedImage template = ImageIO.read(this.layout);

        String path = this.getClass().getResource("../../../../").getPath();
        File foto = new File(path + "\\resources\\temp\\funcionario.png");
        foto.createNewFile();
        FileOutputStream out = new FileOutputStream(foto);
        out.write(fotoCracha);
        BufferedImage fotoFuncionario = ImageIO.read(foto);

        Graphics2D graphicImage = template.createGraphics();

        int x = 0;
        int y = 1;

        graphicImage.setFont(new Font("Akhbar", Font.BOLD, 47));
        graphicImage.setPaint(Color.black);
        graphicImage.drawString(nome, posicaoNome[x], posicaoNome[y]);
        graphicImage.drawString(escreverData(admissao), posicaoAdmissao[x], posicaoAdmissao[y]);
        graphicImage.drawString(String.valueOf(codigo), posicaoCodigo[x], posicaoCodigo[y]);
        graphicImage.drawString(apelido, posicaoApelido[x], posicaoApelido[y]);

        graphicImage.drawImage(fotoFuncionario, posicaoImagem[x], posicaoImagem[y], 1024, 986, null);

        graphicImage.dispose();
        ImageIO.write(template, "PNG", foto);

        return Files.readAllBytes(foto.toPath());
    }

    private String escreverData(LocalDate data){
        int mes = data.getMonth().getValue();
        int ano = data.getYear();
        if(mes == 1)
            return "Janeiro, " + ano;
        if(mes == 1)
            return "Fevereiro, " + ano;
        if(mes == 1)
            return "Março, " + ano;
        if(mes == 1)
            return "Abril, " + ano;
        if(mes == 1)
            return "Maio, " + ano;
        if(mes == 1)
            return "Junho, " + ano;
        if(mes == 1)
            return "Julho, " + ano;
        if(mes == 1)
            return "Agosto, " + ano;
        if(mes == 1)
            return "Setembro, " + ano;
        if(mes == 1)
            return "Outubro, " + ano;
        if(mes == 1)
            return "Novembro, " + ano;
        if(mes == 1)
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
