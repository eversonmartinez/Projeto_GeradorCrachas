package com.pavani.model.entities;

import com.pavani.util.FileDAO;
import com.pavani.util.IFileDAO;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import org.hibernate.service.spi.InjectService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@Entity
public class CrachaFuncionario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nomeVisivel", nullable = true, length = 20)
    private String nomeVisivel;
    @Column(name = "apelido", nullable = true, length = 10)
    private String apelido;
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

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public void setFoto(InputStream foto) throws IOException{
            File file = new File("image.png");
            FileDAO.save(foto, file);
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
}
