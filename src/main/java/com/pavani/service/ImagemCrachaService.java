package com.pavani.service;

import com.pavani.dao.CrachaFuncionarioDao;
import com.pavani.model.entities.CrachaFuncionario;

import java.io.IOException;

public class ImagemCrachaService {

    static CrachaFuncionarioDao dao = new CrachaFuncionarioDao();

    public static byte[] getById(Long id) throws IOException {
        CrachaFuncionario cracha = dao.findById(id);
        GeradorCrachaService service = new GeradorCrachaService();

        return service.gerarCracha(cracha);
    }
}
