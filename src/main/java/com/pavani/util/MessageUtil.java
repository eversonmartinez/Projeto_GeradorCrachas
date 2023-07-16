package com.pavani.util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class MessageUtil {
    public static String getExceptionMessage(Exception ex){
        while(ex.getCause() != null){
            ex = (Exception) ex.getCause();
        }
        String retorno = ex.getMessage();
        if(retorno.contains("foreign key")){
            retorno = "Registro não pode ser excluído por possuir referência no sistema";
        }

        return retorno;
    }

    public static void infoMessage(String title){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, "");
        facesContext.addMessage(null, message);
    }

    public static void infoMessage(String title, String detail){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, detail);
        facesContext.addMessage(null, message);
    }

    public static void errorMessage(String title){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, "");
        facesContext.addMessage(null, message);
    }

    public static void errorMessage(String title, String detail){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, detail);
        facesContext.addMessage(null, message);
    }
}
