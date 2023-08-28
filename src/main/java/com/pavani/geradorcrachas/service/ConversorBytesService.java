package com.pavani.geradorcrachas.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConversorBytesService {

    public static byte[] toByteArrayUsingIS(InputStream inputS) throws IOException {
        ByteArrayOutputStream byteArrayOtpS = new ByteArrayOutputStream();
        int readByte = inputS.read();
        while (readByte != -1){
            byteArrayOtpS.write(readByte);
            readByte = inputS.read();
        }
        return byteArrayOtpS.toByteArray();
    }
}
