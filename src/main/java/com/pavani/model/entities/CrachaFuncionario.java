package com.pavani.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import java.io.Serializable;

public class CrachaFuncionario implements Serializable {
    @Column(name = "nomeVisivel", nullable = true, length = 20)
    private String nomeVisivel;
    @Column(name = "apelido", nullable = true, length = 10)
    private String apelido;
    private byte[] foto;

    @MapsId
    @OneToOne
    private Funcionario funcionario;

    public CrachaFuncionario(){}

    public CrachaFuncionario(Funcionario funcionario) {
       this.funcionario = funcionario;
    }

    public String getNomeVisivel() {
        return nomeVisivel;
    }

    public void setNomeVisivel(String nomeVisivel) {
        this.nomeVisivel = nomeVisivel;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
}
