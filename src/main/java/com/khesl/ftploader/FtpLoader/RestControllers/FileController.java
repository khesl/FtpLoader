package com.khesl.ftploader.FtpLoader.RestControllers;

import com.khesl.ftploader.FtpLoader.beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.sql.Clob;
import java.util.Calendar;
import java.util.Collection;


@RestController
@RequestMapping("/file")
public class FileController {

    @GetMapping("/check/{path}")
    public File[] checkByPath(@PathVariable String path) {
        System.out.println("path: " + path);
        return new File(path).listFiles();
    }

    @Autowired
    VisitJdbcRepository visitJdbcRepository;

    @Autowired
    UploadedFilesJdbcRepository uploadedFilesJdbcRepository;

    @GetMapping("/check")
    public File[] checkFile(Authentication authentication, Principal principal, @RequestParam("path") String path) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Auth: " + path);

        System.out.println(authentication.getName());
        System.out.println("-----------------");
        System.out.println(principal.getName());
        System.out.println("-----------------");
        System.out.println(user.getUsername());

        Calendar cal = Calendar.getInstance();
        visitJdbcRepository.insert(new Visit(user.getUsername(), cal.getTime(), "/check", path));

        System.out.println("path: " + path);
        return new File(path).listFiles();
    }

    @GetMapping("/upload")
    public File[] uploadFile(Authentication authentication, Principal principal, @RequestParam("path") String path) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Auth: " + path);

        System.out.println(authentication.getName());

        Calendar cal = Calendar.getInstance();
        visitJdbcRepository.insert(new Visit(user.getUsername(), cal.getTime(), "/upload", path));

        for (File file : new File(path).listFiles())
            if (!file.isDirectory()){
                try {
                    uploadedFilesJdbcRepository.insert(new UploadedFiles(file.getName(), cal.getTime(), null),
                            Files.readAllBytes(file.toPath()));
                } catch (IOException e) {
                    System.out.println("--> IOException e");
                    //e.printStackTrace();
                }
            }


        System.out.println("path: " + path);
        return new File(path).listFiles();
    }

    @Autowired
    UploadedFilesRepository uploadedFilesRepository;

    @GetMapping("/view")
    @ResponseBody
    public String viewFile(Authentication authentication, Principal principal) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println(authentication.getName());
        System.out.println("-----------------");

        Calendar cal = Calendar.getInstance();
        visitJdbcRepository.insert(new Visit(user.getUsername(), cal.getTime(), "/view", null));

        String response = "[";
        for (UploadedFiles obj : this.uploadedFilesRepository.findAll())
            response += "{" + obj.toString() + "},\n";
        response = response.substring(0, response.length()-2);
        response+="]";

        return response;
    }
    /*@GetMapping("/view")
    *public Collection<UploadedFiles> visits(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Calendar cal = Calendar.getInstance();
        visitJdbcRepository.insert(new Visit(user.getUsername(), cal.getTime(), "/view", null));

        return this.uploadedFilesRepository.findAll();
    }*/


}
