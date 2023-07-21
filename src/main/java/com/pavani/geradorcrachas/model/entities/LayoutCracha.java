package com.pavani.geradorcrachas.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

//posso colocar dentro de uma classe abstrata pro caso de existirem outras posições possíveis de inserção em outros modelos
@Entity
public class LayoutCracha implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    private String nome;
    @NotNull
    private byte[] imagem;
    @NotNull
    private int[] posicaoNome;
    @NotNull
    private int[] posicaoAdmissao;
    @NotNull
    private int[] posicaoCodigo;
    @NotNull
    private int[] posicaoApelido;
    @NotNull
    private int[] posicaoImagem;

    public LayoutCracha(){
        posicaoNome = new int[] {95, 247};
        posicaoAdmissao = new int[] {83, 309};
        posicaoCodigo = new int[] {86, 374};
        posicaoApelido = new int[] {93, 585};
        posicaoImagem = new int[] {0, 637};
    }

    public LayoutCracha(String nome) {
        this.nome = nome;
        posicaoNome = new int[] {95, 247};
        posicaoAdmissao = new int[] {87, 309};
        posicaoCodigo = new int[] {96, 373};
        posicaoApelido = new int[] {97, 583};
        posicaoImagem = new int[] {0, 637};
    }

    public LayoutCracha(String nome, int xNome, int yNome, int xAdmissao, int yAdmissao, int xCodigo, int yCodigo,
                        int xApelido, int yApelido, int xImagem, int yImagem){
        this.nome = nome;
        posicaoNome = new int[] {xNome, yNome};
        posicaoAdmissao = new int[] {xAdmissao, yAdmissao};
        posicaoCodigo = new int[] {xCodigo, yCodigo};
        posicaoApelido = new int[] {xApelido, yApelido};
        posicaoImagem = new int[] {xImagem, yImagem};
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        if(imagem != null)
            this.imagem = imagem;
    }

    public int[] getPosicaoNome() {
        return posicaoNome;
    }

    public void setPosicaoNome(int[] posicaoNome) {
        this.posicaoNome = posicaoNome;
    }

    public void setPosicaoNome(int x, int y) {
        this.posicaoNome[0] = x;
        this.posicaoNome[1] = y;
    }

    public int[] getPosicaoAdmissao() {
        return posicaoAdmissao;
    }

    public void setPosicaoAdmissao(int[] posicaoAdmissao) {
        this.posicaoAdmissao = posicaoAdmissao;
    }

    public void setPosicaoAdmissao(int x, int y) {
        this.posicaoAdmissao[0] = x;
        this.posicaoAdmissao[1] = y;
    }

    public int[] getPosicaoCodigo() {
        return posicaoCodigo;
    }

    public void setPosicaoCodigo(int[] posicaoCodigo) {
        this.posicaoCodigo = posicaoCodigo;
    }

    public void setPosicaoCodigo(int x, int y) {
        this.posicaoCodigo[0] = x;
        this.posicaoCodigo[1] = y;
    }

    public int[] getPosicaoApelido() {
        return posicaoApelido;
    }

    public void setPosicaoApelido(int[] posicaoApelido) {
        this.posicaoApelido = posicaoApelido;
    }

    public void setPosicaoApelido(int x, int y) {
        this.posicaoApelido[0] = x;
        this.posicaoApelido[1] = y;
    }

    public int[] getPosicaoImagem() {
        return posicaoImagem;
    }

    public void setPosicaoImagem(int[] posicaoImagem) {
        this.posicaoImagem = posicaoImagem;
    }

    public void setPosicaoImagem(int x, int y) {
        this.posicaoImagem[0] = x;
        this.posicaoImagem[1] = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LayoutCracha that = (LayoutCracha) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
