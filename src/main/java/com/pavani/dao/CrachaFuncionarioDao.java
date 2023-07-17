package com.pavani.dao;

import com.pavani.model.entities.CrachaFuncionario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CrachaFuncionarioDao extends Dao<CrachaFuncionario>{
    public CrachaFuncionarioDao(){
        super(CrachaFuncionario.class);
    }

    //TEMPORARIAMENTE SENDO UTILIZADO PARA PEGAR UMA FOTO DO BANCO E SALVAR NA MINHA MÁQUINA
    public void testeGetFoto(Long id) throws IOException {
        CrachaFuncionario cracha = findById(id);
        File teste = new File("C:\\Users\\Administrador\\Desktop\\Teste.png");
        FileOutputStream out = new FileOutputStream(teste);
        out.write(cracha.getFoto());
        out.close();
    }
}
