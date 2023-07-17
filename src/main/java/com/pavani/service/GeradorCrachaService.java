package com.pavani.service;

import com.pavani.model.entities.CrachaFuncionario;

public class GeradorCrachaService {

    private CrachaFuncionario informacoes;
    private static double[] posicaoNome = new double[2];
    private static double[] posicaoAdmissao = new double[2];
    private static double[] posicaoCodigo = new double[2];
    private static double[] posicaoApelido = new double[2];
    private static double[] posicaoImagem = new double[2];

    public GeradorCrachaService(){

    }

    public GeradorCrachaService(CrachaFuncionario informacoes){
        this.informacoes = informacoes;
    }
    public void preview(){}

    public void gerarCracha(){}
}
