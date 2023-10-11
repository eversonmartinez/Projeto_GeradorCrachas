package com.pavani.geradorcrachas.dao;

import com.pavani.geradorcrachas.masks.FuncionarioMask;
import com.pavani.geradorcrachas.model.entities.Funcionario;

import java.util.List;
import java.util.stream.Collectors;

public class FuncionarioDao extends Dao<Funcionario> {

    public FuncionarioDao(){
        super(Funcionario.class);
    }

    private List<FuncionarioMask> listaObjetosMasked;

    public List<FuncionarioMask> getListaObjetosMasked(){
        return this.getListaObjetos().stream().map(funcionario -> new FuncionarioMask(funcionario)).collect(Collectors.toList());
    }
}
