package com.khesl.ftploader.FtpLoader.RestControllers;

import com.khesl.ftploader.FtpLoader.beans.*;
import com.khesl.ftploader.FtpLoader.utils.MyParametersController;
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
    @Autowired
    VisitJdbcRepository visitJdbcRepository;
    @Autowired
    UploadedFilesJdbcRepository uploadedFilesJdbcRepository;
    @Autowired
    FtpLogic ftpLogic;

    /**
     *  Method for inspect tree of files in 'path'
     * for call use {@link "http:/localhost:8080/file/check?path=*path*" }
     *
     * @param path - REQUIRED!
     * @return File[] - array of files in directory
     * */
    @GetMapping("/check")
    public File[] checkFile(@RequestParam("path") String path) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Call: /file/check?path" + path);

        visitJdbcRepository.insert(new Visit(user.getUsername(),"/check", path));

        System.out.println("path: " + path);
        return new File(path).listFiles();
    }

    /**
     *  Method for upload tree of files in 'path'
     * for call use {@link "http:/localhost:8080/file/upload?path_to_download=*path*,path_to_save=*path*" }
     *
     * @param pathToDownload  - REQUIRED!
     * @param pathToSave      - optional!
     * @return File[] - array of files in directory
     * */
    @GetMapping("/upload")
    public File[] uploadFile(@RequestParam("path_to_download") String pathToDownload, @RequestParam(value = "path_to_save", required = false) String pathToSave) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Call: /file/upload?path" + pathToDownload);
        visitJdbcRepository.insert(new Visit(user.getUsername(),"/upload", pathToDownload));

        boolean use_ftp;
        try {
            use_ftp = Boolean.parseBoolean(new MyParametersController().getProperties("ftp_use_loader"));
        } catch (IOException e) {
            use_ftp = false;
        }
        File[] listFiles;
        if (use_ftp) {
            File tempFile = ftpLogic.downloadFile(pathToSave, pathToDownload);
            if (tempFile.isDirectory()) {
                listFiles = tempFile.listFiles();
            } else {
                listFiles = new File[1];
                listFiles[0] = tempFile;
            }
        } else {
            listFiles = new File(pathToDownload).listFiles();
        }

        for (File file : listFiles)
            if (!file.isDirectory()){
                try {
                    uploadedFilesJdbcRepository.insert(new UploadedFiles(file.getName(), Calendar.getInstance().getTime(), null),
                            Files.readAllBytes(file.toPath()));
                } catch (IOException e) {
                    System.out.println("--> IOException e");
                }
            }
        System.out.println("pathToDownload: " + pathToDownload);
        return new File(pathToDownload).listFiles();
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

    @GetMapping("/test_ftp")
    @ResponseBody
    public String testFTP(){
        return "{ftpServer: '" + ftpLogic.getServer() + "'}";
    }


}
