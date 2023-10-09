package com.pavani.geradorcrachas.util;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontUtil {

    public static Font customFont(int size){
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream fontFile = classLoader.getResourceAsStream("Fonts\\CocoGothic.ttf");
            Font cocoGothic = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont((float) size);
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(cocoGothic);
            return cocoGothic;
        }catch (IOException ioe){
            ioe.printStackTrace();
            return new Font("Akhbar", Font.TRUETYPE_FONT, size);
        }
        catch (FontFormatException ffe){
            ffe.printStackTrace();
            return new Font("Akhbar", Font.TRUETYPE_FONT, size);
        }
    }

    public static Font customFontBold(int size){
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream fontFile = classLoader.getResourceAsStream("Fonts\\CocoGothic-Bold.ttf");
            Font cocoGothic = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont((float) size);
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(cocoGothic);
            return cocoGothic;
        }catch (IOException ioe){
            ioe.printStackTrace();
            return new Font("Akhbar", Font.BOLD, size);
        }
        catch (FontFormatException ffe){
            ffe.printStackTrace();
            return new Font("Akhbar", Font.BOLD, size);
        }
    }
    public static Font fontSofiaPro(int size){
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream fontFile = classLoader.getResourceAsStream("Fonts\\sofiapro-light.otf");
            Font sofiaPro = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont((float) size);
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(cocoGothic);
            return sofiaPro;
        }catch (IOException ioe){
            ioe.printStackTrace();
            return new Font("Akhbar", Font.BOLD, size);
        }
        catch (FontFormatException ffe){
            ffe.printStackTrace();
            return new Font("Akhbar", Font.BOLD, size);
        }
    }

}
