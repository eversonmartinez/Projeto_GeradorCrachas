package com.pavani.geradorcrachas.model.entities;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.io.*;
import java.time.LocalDate;

@Entity
public class CrachaFuncionario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nomeVisivel", nullable = true, length = 20)
    private String nomeVisivel;
    @Column(name = "apelido", nullable = true, length = 10)
    private String apelido;

    private LocalDate admissaoFuncionario;

    @Column(name="codigoFuncionario", length = 4)
    private Long codigoFuncionario;

    @Lob
    private byte[] foto;
    //@OneToOne(mappedBy = "id.crachaFuncionario", cascade = CascadeType.ALL)

    @OneToOne(mappedBy = "id.crachaFuncionario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Cracha cracha;

    //MapsId//
    @OneToOne(optional = true)
    private Funcionario funcionario;

    public CrachaFuncionario(){}

    public CrachaFuncionario(Funcionario funcionario) {
       this.funcionario = funcionario;
       this.admissaoFuncionario = funcionario.getAdmissao();
       this.codigoFuncionario = funcionario.getCodigo();
       this.nomeVisivel = criarNomeVisivel(funcionario.getNome());
       if(funcionario.getNome() != null)
           this.apelido = funcionario.getNome().split(" ")[0];
    }

    private String criarNomeVisivel(String nome){
        if(nome == null)
            return "";

        if(nome.length() <=20){         //Everson Miranda (total = 15)
            return nome;
        }

        String[] parteNome = nome.split(" ");
        int totalPartes = parteNome.length;

        String resultante = parteNome[0];

        if((resultante + parteNome[totalPartes-1]).length() < 20){  //Everson Tom Miranda Martinez (Everson+Martinez total = 15 )
            if(parteNome[totalPartes-2].length() > 3) {              //Miranda(total = 7)
                if ((resultante + parteNome[1] + parteNome[totalPartes - 1]).length() < 19) { //Everson+Tom+Martinez ( total = 18)
                    return resultante + " " + parteNome[1] + " " + parteNome[totalPartes - 1];    //Everson Tom Martinez ( total = 20)
                }
            }

            else{   //Everson Tom de Martinez //de (total = 2)
                if ((resultante + parteNome[totalPartes-2] + parteNome[totalPartes-1]).length() < 19){//Everson+de+Martinez(total=17)
                    return resultante + " " + parteNome[totalPartes-2] + " " + parteNome[totalPartes-1];//Everson de Martinez(total=19)
                }
            }
            //Everson Miranda Martinez (total = 24)
            return resultante + " " + parteNome[parteNome.length-1];    //Everson Martinez(total=16)
        }

        if((resultante + parteNome[1]).length() < 20){  //Everson Miranda Martinezitos
            if(parteNome[1].length()>3)
                return resultante + " " + parteNome[1];
        }

        return resultante;  //Everson

    }

    public void modificarNomeFuncionario(String novoNome){
        if(this.nomeVisivel != null)
            return;

        this.nomeVisivel = criarNomeVisivel(novoNome);
    }

    public String getNomeCompleto(){
        return funcionario.getNome();
    }


    //    GETTERS E SETTERS PADRÕES
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

    public LocalDate getAdmissaoFuncionario(){
        return this.admissaoFuncionario;
    }

    public void setAdmissaoFuncionario(LocalDate admissaoFuncionario) {
        this.admissaoFuncionario = admissaoFuncionario;
    }

    public Long getCodigoFuncionario(){
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(Long codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
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

    public Cracha getCracha() {
        return cracha;
    }

    public void setCracha(Cracha cracha) {
        this.cracha = cracha;
    }
}
