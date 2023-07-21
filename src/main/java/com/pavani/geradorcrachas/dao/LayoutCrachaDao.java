package com.pavani.geradorcrachas.dao;

import com.pavani.geradorcrachas.model.entities.LayoutCracha;

public class LayoutCrachaDao extends Dao<LayoutCracha> {

    public LayoutCrachaDao(){
        super(LayoutCracha.class);
    }

    //TODO: query did not return a unique result
    public LayoutCracha getDefault(){
        return em.createQuery("from LayoutCracha order by id", LayoutCracha.class).getSingleResult();
    }
}
