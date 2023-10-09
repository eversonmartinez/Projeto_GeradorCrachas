package com.pavani.geradorcrachas.dao;

import com.pavani.geradorcrachas.model.entities.Cracha;
import com.pavani.geradorcrachas.service.DatabaseLobCleaner;
import com.pavani.geradorcrachas.util.MessageUtil;

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

    @Override
    public boolean persist(Cracha objeto){
        try{
            em.getTransaction().begin();
            em.persist(objeto);
            em.getTransaction().commit();
            mensagem = "Objeto persistido com sucesso!";
            DatabaseLobCleaner.verifyDBWeight();
            return true;
        }
        catch(Exception ex){
            rollback();
            mensagem = "Erro ao persistir: " + MessageUtil.getExceptionMessage(ex);
            return false;
        }
    }

    @Override
    public boolean merge(Cracha objeto){
        try{
            em.getTransaction().begin();
            em.merge(objeto);
            em.getTransaction().commit();
            mensagem = "Objeto persistido com sucesso!";
            DatabaseLobCleaner.verifyDBWeight();
            return true;
        }
        catch(Exception ex){
            rollback();
            mensagem = "Erro ao persistir: " + MessageUtil.getExceptionMessage(ex);
            return false;
        }
    }
}
