package com.pavani.geradorcrachas.dao;

import com.pavani.geradorcrachas.model.entities.Cracha;

public class CrachaDao extends Dao<Cracha> {
    public CrachaDao(){
        super(Cracha.class);
    }

    @Override
    public Cracha findById(Long id){
        rollback();
        Cracha obj = em.createQuery("select c from Cracha c where c.id.crachaFuncionario.id = " + id, Cracha.class).getSingleResult();
        return obj;
    }
}
