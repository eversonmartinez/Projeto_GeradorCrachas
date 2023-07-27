package com.pavani.geradorcrachas.dao;

import com.pavani.geradorcrachas.model.entities.Cracha;
import com.pavani.geradorcrachas.model.entities.CrachaFuncionario;
import com.pavani.geradorcrachas.util.EntityManagerUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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

    @Override
    public List<CrachaFuncionario> getListaTodos(){
       em.getTransaction().begin();
        String jpql = "from CrachaFuncionario";
        List<CrachaFuncionario> listaRetorno = em.createQuery(jpql).getResultList();
        em.getTransaction().commit();
        return listaRetorno;
    }
}
