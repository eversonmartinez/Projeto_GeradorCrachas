package com.pavani.geradorcrachas.masks;

import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;
import com.pavani.geradorcrachas.model.entities.Funcionario;

import java.time.LocalDate;

public class CrachaFuncionarioMask {

    private Long id;
    private String nome;
    private String apelido;
    private Long codigo;

    public CrachaFuncionarioMask(CrachaFuncionario crachaFuncionario){
        this.id = crachaFuncionario.getId();
        this.nome = crachaFuncionario.getNomeVisivel();
        this.apelido = crachaFuncionario.getApelido();
        this.codigo = crachaFuncionario.getCodigoFuncionario();
    }

    public Long getId(){
        return this.id;
    }

    public String getNome() {
        return nome;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getApelido() {
        return apelido;
    }
}