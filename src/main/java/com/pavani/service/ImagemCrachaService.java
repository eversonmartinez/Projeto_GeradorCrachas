package com.pavani.service;

import com.pavani.dao.CrachaFuncionarioDao;
import com.pavani.model.entities.CrachaFuncionario;

public class ImagemCrachaService {

    static CrachaFuncionarioDao dao = new CrachaFuncionarioDao();

    public static byte[] getById(Long id){
        CrachaFuncionario cracha = dao.findById(id);
        return cracha.getFoto();
    }
}
