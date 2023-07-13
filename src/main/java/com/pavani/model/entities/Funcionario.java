package com.pavani.model.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class Funcionario implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_funcionario", sequenceName = "seq_funcionario_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_funcionario", strategy = GenerationType.SEQUENCE)
    private Long Id;
    @NotNull
    @NotBlank
    @Column(name = "nome", nullable = false, length = 70)
    private String nome;
    @NotNull
    private LocalDate admissao;
    @NotNull
    private Long codigo;    //código de que?

    @OneToOne(mappedBy = "funcinoario", cascade = CascadeType.ALL)
    private CrachaFuncionario cracha;

    public Funcionario(){}

    public Funcionario(String nome, LocalDate admissao, Long codigo) {
        this.nome = nome;
        this.admissao = admissao;
        this.codigo = codigo;
    }

    public Long getId() {
        return Id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getAdmissao() {
        return admissao;
    }

    public void setAdmissao(LocalDate admissao) {
        this.admissao = admissao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
}
