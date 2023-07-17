package com.pavani.model.entities;

import jakarta.persistence.*;

import java.io.*;

@Entity
public class CrachaFuncionario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nomeVisivel", nullable = true, length = 20)
    private String nomeVisivel;
    @Column(name = "apelido", nullable = true, length = 10)
    private String apelido;
    @Lob
    private byte[] foto;

    //MapsId//
    @OneToOne(optional = true)
    private Funcionario funcionario;

    public CrachaFuncionario(){}

    public CrachaFuncionario(Funcionario funcionario) {
       this.funcionario = funcionario;
    }

    public Long getId() {
        return id;
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

    public void testeGetFoto() throws IOException {

        File teste = new File("C:\\Users\\Administrador\\Desktop\\Teste.png");
        FileOutputStream out = new FileOutputStream(teste);
        out.write(this.foto);
        out.close();
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
}
