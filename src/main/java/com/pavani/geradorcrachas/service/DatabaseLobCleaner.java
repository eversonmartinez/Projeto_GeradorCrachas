package com.pavani.geradorcrachas.service;

import com.pavani.geradorcrachas.dao.CrachaDao;
import com.pavani.geradorcrachas.model.entities.Cracha;

import java.util.List;

public class DatabaseLobCleaner {

    public static void verifyDBWeight(){
        CrachaDao dao = new CrachaDao();
        List<Cracha> listaTodos = dao.getListaTodos();
        if(listaTodos.size() <= 10)
            return;

        int contagemFotos = 0;
        for(Cracha c : listaTodos){
            if(c.getCrachaFinalizado() != null){
                contagemFotos++;
            }
        }

        if(contagemFotos <= 10)
            return;

        int i= contagemFotos - 10;
        for(Cracha c : listaTodos){
            if(c.getCrachaFinalizado() != null){
                i--;
                c.setCrachaFinalizado(null);
                dao.merge(c);
            }
            if(i==0)
                break;
        }
    }

    public static void verifyDBWeightExcept(Long id){
        CrachaDao dao = new CrachaDao();
        List<Cracha> listaTodos = dao.getListaTodos();
        if(listaTodos.size() <= 10)
            return;

        int contagemFotos = 0;
        for(Cracha c : listaTodos){
            if(c.getCrachaFinalizado() != null){
                contagemFotos++;
            }
        }
        if(contagemFotos <= 10)
            return;

        int i= contagemFotos - 10;
        for(Cracha c : listaTodos){
            if(c.getId().getCrachaFuncionario().getId()!=id && c.getCrachaFinalizado() != null){
                i--;
                c.setCrachaFinalizado(null);
                dao.merge(c);
            }
            if(i==0)
                break;
        }
    }
}
