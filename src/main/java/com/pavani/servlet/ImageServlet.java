package com.pavani.servlet;

import com.pavani.service.ImagemCrachaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            String s = request.getPathInfo().substring(1);
            response.setContentType("image/png");

            byte[] bytes = ImagemCrachaService.getById(Long.parseLong(s));
            OutputStream out = response.getOutputStream();
            out.write(bytes);
            out.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
