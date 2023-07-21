package com.pavani.geradorcrachas.servlet;

import com.pavani.geradorcrachas.service.ImagemCrachaService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            String requestedImage = request.getPathInfo().substring(1);

            if(requestedImage == null){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            response.setContentType("image/png");

            byte[] bytes = ImagemCrachaService.getById(Long.parseLong(requestedImage));
            OutputStream out = response.getOutputStream();
            out.write(bytes);
            out.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
