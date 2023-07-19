package com.pavani.service;

import com.pavani.model.entities.CrachaFuncionario;
import jakarta.ejb.Local;

import java.time.LocalDate;

public class GeradorCrachaService {

    private CrachaFuncionario informacoes;
    private static double[] posicaoNome = new double[2];
    private static double[] posicaoAdmissao = new double[2];
    private static double[] posicaoCodigo = new double[2];
    private static double[] posicaoApelido = new double[2];
    private static double[] posicaoImagem = new double[2];

    public GeradorCrachaService(){

    }

    public void preview(){}

    public static byte[] gerarCracha(CrachaFuncionario informacoes){
        //javaIO.drawText(posicaoNome[0], posicaoNome[1], informacoes.getNomeVisivel);
        //javaIO.drawText(posicaoAdmissao[0], posicaoAdmissao[1], escreverData(informacoes.getAdmissao));
        //javaIO.drawText(posicaoCodigo[0], posicaoCodio[1], informacoes.getCodigo);
        //javaIO.drawText(posicaoApelido[0], posicaoApelido[1], informacoes.getApelido);
        //foto = foto.convert(int resolucaox, int resolucaoy);
        //javaIO.drawImage(posicaoImagem[0], posicaoImagem[1], foto);
    }

    public static byte[] gerarCracha(String nome, LocalDate admissao, Long codigo, String apelido, byte[] foto){
        //javaIO.drawText(posicaoNome[0], posicaoNome[1], nome);
        //javaIO.drawText(posicaoAdmissao[0], posicaoAdmissao[1], escreverData(informacoes.getAdmissao));
        //javaIO.drawText(posicaoCodigo[0], posicaoCodio[1], informacoes.getCodigo);
        //javaIO.drawText(posicaoApelido[0], posicaoApelido[1], informacoes.getApelido);
        //foto = foto.convert(int resolucaox, int resolucaoy);
        //javaIO.drawImage(posicaoImagem[0], posicaoImagem[1], foto);
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
    }
}
