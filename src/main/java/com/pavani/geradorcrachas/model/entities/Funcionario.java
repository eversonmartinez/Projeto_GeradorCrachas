package com.pavani.geradorcrachas.model.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
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

    @OneToOne(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private CrachaFuncionario cracha;

    public Funcionario(){
        cracha = new CrachaFuncionario(this);
    }

    public Funcionario(String nome, LocalDate admissao, Long codigo) {
        this.nome = nome;
        this.admissao = admissao;
        this.codigo = codigo;
        cracha = new CrachaFuncionario(this);
    }

    public Long getId() {
        return Id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        cracha.modificarNomeFuncionario(this.nome);
    }

    public LocalDate getAdmissao() {
        return admissao;
    }

    public void setAdmissao(LocalDate admissao) {
        this.admissao = admissao;
        this.cracha.setAdmissaoFuncionario(this.admissao);
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
        cracha.setCodigoFuncionario(this.codigo);
    }
}
