package com.pavani.dao;

import com.pavani.model.entities.LayoutCracha;
import jakarta.persistence.Query;

public class LayoutCrachaDao extends Dao<LayoutCracha> {

    public LayoutCrachaDao(){
        super(LayoutCracha.class);
    }

    public LayoutCracha getDefault(){
        return em.createQuery("from LayoutCracha order by id", LayoutCracha.class).getSingleResult();
    }
}
