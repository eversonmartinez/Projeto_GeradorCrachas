package com.pavani.geradorcrachas.servlet;

import com.pavani.geradorcrachas.dao.CrachaDao;
import com.pavani.geradorcrachas.dao.LayoutCrachaDao;
import com.pavani.geradorcrachas.model.entities.Cracha;
import com.pavani.geradorcrachas.model.entities.LayoutCracha;
import com.pavani.geradorcrachas.service.GeradorCrachaService;
import jakarta.persistence.NoResultException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;

@WebServlet("/images/layouts/*")
public class ImageLayoutServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            String requestedImage = request.getPathInfo().substring(1);

            if(requestedImage == null){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                response.setStatus(404);
                return;
            }

            byte[] bytes = null;
            response.setContentType("image/png");

            //if(requestedImage.equals("none"))
            //    bytes = GeradorCrachaService.crachaVazioSemLayout();

            //else {
                try {
                    LayoutCracha layout = new LayoutCrachaDao().findById(Long.parseLong(requestedImage));
                    if (layout != null && layout.getImagem() != null)
                        bytes = layout.getImagem();

                }catch(NoResultException nre){
                    nre.printStackTrace();
                }
            //}

            if(bytes != null) {
                OutputStream out = response.getOutputStream();
                out.write(bytes);
                out.close();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
