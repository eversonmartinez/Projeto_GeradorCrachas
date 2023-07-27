package com.pavani.geradorcrachas.servlet;

import com.pavani.geradorcrachas.dao.CrachaDao;
import com.pavani.geradorcrachas.model.entities.Cracha;
import com.pavani.geradorcrachas.service.GeradorCrachaService;
import jakarta.persistence.NoResultException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;

@WebServlet("/images/crachas/*")
public class ImageCrachaServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            String requestedImage = request.getPathInfo().substring(1);

            if(requestedImage == null){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                response.setStatus(404);
                return;
            }

            byte[] bytes;
            response.setContentType("image/png");

            if(requestedImage.equals("none"))
                bytes = GeradorCrachaService.crachaVazioSemLayout();

            else {
                try {
                    Cracha cracha = new CrachaDao().findById(Long.parseLong(requestedImage));
                    if (cracha == null || cracha.getCrachaFinalizado() == null)
                        bytes = GeradorCrachaService.crachaVazioSemLayout();

                    else
                        bytes = cracha.getCrachaFinalizado();

                }catch(NoResultException nre){
                    bytes = GeradorCrachaService.crachaVazioSemLayout();
                }
            }

            OutputStream out = response.getOutputStream();
            out.write(bytes);
            out.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
