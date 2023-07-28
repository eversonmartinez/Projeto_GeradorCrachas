package com.pavani.geradorcrachas.servlet;

import com.pavani.geradorcrachas.dao.CrachaDao;
import com.pavani.geradorcrachas.model.entities.Cracha;
import jakarta.persistence.NoResultException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@WebServlet("/download/images/crachas/*")
public class DownloadServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){

        try {
            String requestedImage = request.getPathInfo().substring(1);

            if (requestedImage == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                response.setStatus(404);
                return;
            }

            try {

                Cracha cracha = new CrachaDao().findById(Long.parseLong(requestedImage));
                if (cracha == null || cracha.getCrachaFinalizado() == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    response.setStatus(404);
                    return;
                }

                response.setContentType("image/png");
                String filename = "cracha-" + cracha.getCrachaFuncionario().getNomeVisivel();
                filename = filename.replaceAll( " ", "");

                response.setHeader("Content-disposition", "attachment; filename=" + filename + ".png");

                byte[] bytes;
                bytes = cracha.getCrachaFinalizado();
                OutputStream out = response.getOutputStream();
                out.write(bytes);
                out.close();

            } catch (NoResultException nre) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                response.setStatus(404);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
