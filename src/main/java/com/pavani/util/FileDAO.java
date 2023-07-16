package com.pavani.util;

import jakarta.inject.Named;
import org.primefaces.shaded.commons.io.IOUtils;

import java.io.*;

@Named
public class FileDAO {

    public static void save(InputStream inputStream, File file) throws IOException {
        OutputStream output = new FileOutputStream(file);

        IOUtils.copy(inputStream, output);
    }
}
