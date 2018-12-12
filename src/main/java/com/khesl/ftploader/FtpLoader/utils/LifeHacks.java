package com.khesl.ftploader.FtpLoader.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.io.*;
import java.security.Principal;

public class LifeHacks {

    /**способы получить инфу о текущем пользователе.*/
    public void checkFile(Authentication authentication, Principal principal) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println(authentication.getName());
        System.out.println("-----------------");
        System.out.println(principal.getName());
        System.out.println("-----------------");
        System.out.println(user.getUsername());
    }

    /** 4 способа выкачать файл с тфп*/
    public void inputStreamToFile(FTPClient ftpClient, String localFilePath, String remoteFilePath) throws IOException {
        boolean success = false;
        //--------------------
            /*//Create an InputStream to the File Data and use FileOutputStream to write it
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(localFilePath);
            //Using org.apache.commons.io.IOUtils
            IOUtils.copy(inputStream, fileOutputStream);
            fileOutputStream.flush();
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(inputStream);
            success = ftpClient.completePendingCommand();
            if (success) System.out.println("File has been downloaded successfully.");*/
        //---------------------
            /*// APPROACH #1: using retrieveFile(String, OutputStream) Работает!
            File downloadFile1 = new File(localFilePath);
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            success = ftpClient.retrieveFile(remoteFilePath, outputStream1);
            outputStream1.close();

            if (success) System.out.println("File has been downloaded successfully.");*/
        //---------------------
            /*// APPROACH #2: using InputStream retrieveFileStream(String)
            File downloadFile2 = new File(localFilePath);
            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath);
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                outputStream2.write(bytesArray, 0, bytesRead);
            }
            success = ftpClient.completePendingCommand();
            if (success) System.out.println("File #2 has been downloaded successfully.");
            outputStream2.close();
            inputStream.close();*/
        //---------------------
        OutputStream outputStream = new FileOutputStream(new File(localFilePath));
        success = ftpClient.retrieveFile(remoteFilePath, outputStream);
        outputStream.close();
        if (success) System.out.println("File #2 has been downloaded successfully.");
    }
}
