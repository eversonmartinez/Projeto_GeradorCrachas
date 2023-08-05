package com.pavani.geradorcrachas.service;

import com.pavani.geradorcrachas.model.entities.Funcionario;
import jakarta.ejb.Local;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImportadorFuncionarioService {

    private String message;

    public ImportadorFuncionarioService(){}

    public List<Funcionario> importarTxt(File txt){

        if(txt.isDirectory()){
            throw new IllegalArgumentException();
        }

        message ="Importação concluída com sucesso";

        try (BufferedReader br = new BufferedReader(new FileReader(txt))){
            List<Funcionario> listaRetorno = new ArrayList<>();
            String line = br.readLine();

            while (line!=null){

                String[] particoes = line.split(",");

                if(particoes.length == 3){
                    String nome = particoes[0];
                    nome = nome.replaceAll("\"", "");
                    nome = capitalize(nome);
                    LocalDate admissao = LocalDate.parse(particoes[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    Long codigo = Long.valueOf(particoes[2]);
                    Funcionario funcionario = new Funcionario(nome, admissao, codigo);
                    listaRetorno.add(funcionario);
                }

                else{
                    message = "Erro ao importar um dos funcionários, verifique seu arquivo";
                }

                line = br.readLine();
            }

            if(listaRetorno.size() == 0){
                message = "Nenhum funcionário encontrado no arquivo";
            }

            return listaRetorno;

        }catch (IOException ex){
            ex.printStackTrace();
            message = "Erro ao importar";
            return null;
        }
    }

    public List<Funcionario> importarTxt(InputStream txt) {

        message ="Importação concluída com sucesso";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(txt, "UTF-8"))){
            List<Funcionario> listaRetorno = new ArrayList<>();
            String line = br.readLine();

            while (line!=null){

                String[] particoes = line.split(",");

                if(particoes.length == 3){
                    String nome = particoes[0];
                    nome = nome.replaceAll("\"", "");
                    nome = capitalize(nome);
                    LocalDate admissao = LocalDate.parse(particoes[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    Long codigo = Long.valueOf(particoes[2]);
                    Funcionario funcionario = new Funcionario(nome, admissao, codigo);
                    listaRetorno.add(funcionario);
                }

                else{
                    message = "Erro ao importar um dos funcionários, verifique seu arquivo";
                }

                line = br.readLine();
            }

            if(listaRetorno.size() == 0){
                message = "Nenhum funcionário encontrado no arquivo";
            }

            return listaRetorno;

        }catch(UnsupportedEncodingException uee){
            uee.printStackTrace();
            message = "Possível erro na codificação do arquivo";
            return null;
        }catch (IOException ioe){
            ioe.printStackTrace();
            message = "Erro ao importar";
            return null;
        }
    }

    private String capitalize(String string){
        String[] temporaria = string.split(" ");
        String retorno = "";
        int size = temporaria.length;

        for(int i = 0; i < size; i++){
            String up = temporaria[i].substring(0,1).toUpperCase();
            String low = temporaria[i].substring(1).toLowerCase();
            retorno += up + low + " ";
        }

        return retorno;
    }

    public String getMessage() {
        return message;
    }
}
