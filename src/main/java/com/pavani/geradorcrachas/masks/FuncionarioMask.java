package com.pavani.geradorcrachas.masks;

import com.pavani.geradorcrachas.model.entities.Funcionario;

import java.time.LocalDate;

public class FuncionarioMask {

    private Long id;
    private String nome;
    private Long codigo;
    private LocalDate admissao;

    public FuncionarioMask(Funcionario funcionario){
        this.id = funcionario.getId();
        this.nome = funcionario.getNome();
        this.codigo = funcionario.getCodigo();
        this.admissao = funcionario.getAdmissao();
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

    public LocalDate getAdmissao() {
        return admissao;
    }
}