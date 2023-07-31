package com.pavani.geradorcrachas.dao;

import com.pavani.geradorcrachas.model.entities.LayoutCracha;
import jakarta.persistence.Query;

public class LayoutCrachaDao extends Dao<LayoutCracha> {

    public LayoutCrachaDao(){
        super(LayoutCracha.class);
    }

    public LayoutCracha getDefault(){
        Query query = em.createNativeQuery("select id from layoutcracha order by id LIMIT 1");
        return em.find(LayoutCracha.class, query.getSingleResult());
    }
}
