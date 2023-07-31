package com.pavani.geradorcrachas.dao;

import com.pavani.geradorcrachas.model.entities.LayoutCracha;
import com.pavani.geradorcrachas.model.exceptions.NenhumLayoutException;
import jakarta.persistence.Query;

public class LayoutCrachaDao extends Dao<LayoutCracha> {

    public LayoutCrachaDao(){
        super(LayoutCracha.class);
    }

    public LayoutCracha getDefault(){
        Query query = em.createNativeQuery("select id from layoutcracha order by id LIMIT 1");
        LayoutCracha objeto = em.find(LayoutCracha.class, query.getSingleResult());
        if(objeto == null)
            throw new NenhumLayoutException("Um Layout para crahcás deve ser criado antes de prosseguir");
        return objeto;
    }
}
