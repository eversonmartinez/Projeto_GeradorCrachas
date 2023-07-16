package com.pavani.dao;

import com.pavani.util.EntityManagerUtil;
import com.pavani.util.MessageUtil;
import jakarta.persistence.EntityManager;

import java.io.Serializable;
import java.util.List;

public abstract class Dao<T> implements Serializable {
    private String mensagem = "";
    private EntityManager em;
    private Class classePersistente;

    public Dao(){
        em = EntityManagerUtil.getEntityManager();
    }

    public Dao(Class classePersistente){
        em = EntityManagerUtil.getEntityManager();
        this.classePersistente = classePersistente;
    }

    public List<T> getListaTodos(){
        String jpql = "from " + classePersistente.getSimpleName() + " order by id";
        return em.createQuery(jpql).getResultList();
    }

    public void rollback(){
        if(em.getTransaction().isActive() == false){
            em.getTransaction().begin();
        }

        em.getTransaction().rollback();
    }

    public boolean persist(T objeto){
        try{
           em.getTransaction().begin();
           em.persist(objeto);
           em.getTransaction().commit();
           mensagem = "Objeto persistido com sucesso!";
           return true;
        }
        catch(Exception ex){
            rollback();
            mensagem = "Erro ao persistir: " + MessageUtil.getExceptionMessage(ex);
            return false;
        }
    }

    public boolean merge(T objeto){
        try{
            em.getTransaction().begin();
            em.merge(objeto);
            em.getTransaction().commit();
            mensagem = "Objeto persistido com sucesso!";
            return true;
        }
        catch(Exception ex){
            rollback();
            mensagem = "Erro ao persistir: " + MessageUtil.getExceptionMessage(ex);
            return false;
        }
    }

    public boolean remove(T objeto) {
        try {
            em.getTransaction().begin();
            em.remove(objeto);
            em.getTransaction().commit();
            mensagem = "Objeto removido com sucesso";
            return true;
        } catch (Exception ex) {
            rollback();
            mensagem = "Erro ao remover: " + MessageUtil.getExceptionMessage(ex);
            return false;
        }
    }

    public T findById(Long id){
        rollback();
        T obj = (T) em.find(classePersistente, id);
        return obj;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Class getClassePersistente() {
        return classePersistente;
    }
}
