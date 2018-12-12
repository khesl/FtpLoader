package com.khesl.ftploader.FtpLoader.utils;

import java.io.*;

public class Utils {

    public static void inputStreamToFile(String filePath, InputStream inputStream) throws IOException {
        // write the inputStream to a FileOutputStream
        OutputStream outputStream = new FileOutputStream(new File(filePath));

        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
    }
}
