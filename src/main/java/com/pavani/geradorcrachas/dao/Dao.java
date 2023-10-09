package com.pavani.geradorcrachas.dao;

import com.pavani.geradorcrachas.util.EntityManagerUtil;
import com.pavani.geradorcrachas.util.MessageUtil;
import jakarta.persistence.EntityManager;

import java.io.Serializable;
import java.util.List;

public abstract class Dao<T> implements Serializable {
    protected String mensagem = "";
    protected EntityManager em;
    protected Class classePersistente;
    protected String filtro="";
    protected String ordem="id";
    protected Integer maximoObjetos=8;
    protected Integer posicaoAtual=0;
    protected Integer totalObjetos=0;
    private List<T> listaObjetos;
    private List<T> listaTodos;

    public Dao(){
        em = EntityManagerUtil.getEntityManager();
    }

    public Dao(Class classePersistente){
        em = EntityManagerUtil.getEntityManager();
        this.classePersistente = classePersistente;
    }

    public List<T> getListaTodos(){
        String jpql = "from " + classePersistente.getSimpleName();
        return em.createQuery(jpql).getResultList();
    }

    public List<T> getListaObjetos(){
        String jpql = "from " + classePersistente.getSimpleName();
        String where = "";

        filtro = filtro.replaceAll("[';-]", "");    //método de evitar sql injections

        if(filtro.length()>0){
            if(ordem.equals("id")){
                try {
                    Long.parseLong(filtro);
                    where += " where " + ordem + " = '" + filtro + "' ";
                }catch(Exception e){}
            }
            else if(ordem.startsWith("codigo")){
                try {
                    Long.parseLong(filtro);
                    where += " where " + ordem + " = '" + filtro + "' ";
                }catch(Exception e){}
            }
            else{
                where += " where upper(" + ordem + ") like '%" + filtro.toUpperCase() + "%'";
            }

            jpql+=where;
            jpql+=" order by " + ordem;
        }

        totalObjetos = em.createQuery(jpql).getResultList().size();

        return em.createQuery(jpql).setFirstResult(posicaoAtual).setMaxResults(maximoObjetos).getResultList();
    }
    public void primeiraPagina(){
        posicaoAtual = 0;
    }
    public void anteriorPagina(){
        posicaoAtual -= maximoObjetos;
        if(posicaoAtual<0){
            posicaoAtual = 0;
        }
    }

    public void proximaPagina(){
        if(posicaoAtual + maximoObjetos < totalObjetos){
            posicaoAtual += maximoObjetos;
        }
    }

    public void ultimaPagina(){
        int resto = totalObjetos%maximoObjetos;
        if(resto > 0){
            posicaoAtual = totalObjetos - resto;
        }
        else{
            posicaoAtual = totalObjetos - maximoObjetos;
        }
    }

    public String getMensagemNavegaco(){
        int ate = posicaoAtual + maximoObjetos;
        if(ate > totalObjetos){
            ate = totalObjetos;
        }

        return("Listando de " + (posicaoAtual+1) + " até " + ate + " de " + totalObjetos + " registros");
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

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public Integer getMaximoObjetos() {
        return maximoObjetos;
    }

    public void setMaximoObjetos(Integer maximoObjetos) {
        this.maximoObjetos = maximoObjetos;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Integer getTotalObjetos() {
        return totalObjetos;
    }

    public void setTotalObjetos(Integer totalObjetos) {
        this.totalObjetos = totalObjetos;
    }
}
