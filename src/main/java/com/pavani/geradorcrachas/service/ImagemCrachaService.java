package com.pavani.geradorcrachas.service;

import com.pavani.geradorcrachas.dao.CrachaFuncionarioDao;
import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;

import java.io.IOException;

public class ImagemCrachaService {

    static CrachaFuncionarioDao dao = new CrachaFuncionarioDao();

    public static byte[] getById(Long id) throws IOException {
        CrachaFuncionario cracha = dao.findById(id);
        GeradorCrachaService service = new GeradorCrachaService();

        return service.gerarCracha(cracha);
    }
}
