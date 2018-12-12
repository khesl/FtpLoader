package com.khesl.ftploader.FtpLoader.beans;

import com.khesl.ftploader.FtpLoader.utils.MyParametersController;
import com.khesl.ftploader.FtpLoader.utils.Utils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FtpLogic {
    private static FTPClient ftpClient;

    private String server; // dns-name "ftp.kz" or ip 0.0.0.0
    private int port = 21; // sftp port connection (default:'21')
    private String user; // ftp_user
    private String pass; // ftp_password

    public FtpLogic() throws IOException {
        ftpClient = new FTPClient();

        server = new MyParametersController().getProperties("ftp_server");
        port = Integer.parseInt(new MyParametersController().getProperties("ftp_port"));
        user = new MyParametersController().getProperties("ftp_user");
        pass = new MyParametersController().getProperties("ftp_password");
    }

    public static FTPClient getFtpClient() {
        return ftpClient;
    }

    public String getServer() { return server; }

    /**
     * метод для скачивания файла или InputStream с FTP по указанному пути
     * example of call: downloadFile("test_folder/file3.txt");
     *
     * @param remoteFilePath    - remoteFilePath to read, typeOf(String)
     * @return String - content of file (variations: File, String, bytes[], OutputStream)
     * */
    public String downloadFileString(String remoteFilePath) {
        //FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            System.out.println("Start uploading first file");
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath); //storeFile(firstRemoteFile, inputStream); - upload

            BufferedInputStream inbf = new BufferedInputStream(inputStream);

            int bytesRead;
            byte[] buffer = new byte[1024];
            String fileContent= null;
            while((bytesRead = inbf.read(buffer))!=-1) {
                fileContent = new String(buffer,0,bytesRead);
            }
            System.out.println("File: " + fileContent);
            inputStream.close();

            return fileContent;
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * метод для скачивания файла или InputStream с FTP по указанному пути
     * example of call: downloadFileInputStream("test_folder/file3.txt");
     *
     * @param remoteFilePath    - remoteFilePath to read, typeOf(String)
     * @return InputStream - content of file (variations: File, String, bytes[], InputStream)
     * */
    public InputStream downloadFileInputStream(String remoteFilePath) {
        //FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            System.out.println("Start uploading first file");
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath); //storeFile(firstRemoteFile, inputStream); - upload

            BufferedInputStream inbf = new BufferedInputStream(inputStream);

            return inbf;
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * метод для скачивания файла или InputStream с FTP по указанному пути
     * example of call: downloadFile("test_folder/file3.txt");
     * test http link 'http://localhost:8080/file/upload?path_to_download=test_folder_1/test_file_2.txt&path_to_save=src/main/resources/test_file_2.txt'
     *
     * @param localFilePath     - localFilePath to write file, typeOf(String)
     * @param remoteFilePath    - remoteFilePath to read, typeOf(String)
     * @return InputStream - content of file (variations: {@link java.io.File}, String, bytes[], InputStream)
     * */
    public boolean downloadFile(String localFilePath, String remoteFilePath) throws IllegalAccessException {
        //FTPClient ftpClient = new FTPClient();
        boolean success = false;
        try {
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            System.out.println("Start uploading first file");
            System.out.println("localFilePath: '" + localFilePath + "'");
            System.out.println("remoteFilePath: '" + remoteFilePath + "'");

            ftpClient.enterLocalPassiveMode();
            ftpClient.setAutodetectUTF8(true);

            if (new File(localFilePath).isDirectory()) {
                //localFilePath += remoteFilePath.split("/")[remoteFilePath.split("/").length-1];
                throw new IllegalAccessException("wrong filePath to save");
            }
            System.out.println("new localFilePath: '" + localFilePath + "'");

            OutputStream outputStream = new FileOutputStream(new File(localFilePath));
            success = ftpClient.retrieveFile(remoteFilePath, outputStream);
            outputStream.close();
            if (success) System.out.println("File #2 has been downloaded successfully.");

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return success;
    }

    /**
     * метод для загрузки локального файла или InputStream на FTP по указанному пути
     * example of call: uploadFile("file3.txt", "test_folder/file3.txt");
     *
     * @param localFilePath     - localFilePath to read, typeOf(String)
     * @param remoteFilePath    - remoteFilePath to write, typeOf(String)
     * */
    public void uploadFile(String localFilePath, String remoteFilePath) {
        //FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // using create a new file from str
            String strContent = "This example shows how to write string content to a file"; // даже перезаписываем файл
            File firstLocalFile = new File(localFilePath); // путь файла который записываем
            // check if file exist, otherwise create the file before writing
            if (!firstLocalFile.exists()) firstLocalFile.createNewFile();

            Writer writer = new FileWriter(firstLocalFile);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(strContent);
            bufferedWriter.close();

            InputStream inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(remoteFilePath, inputStream); // путь на FTP куда писать, поток который нужно записать
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * метод для загрузки локального файла или InputStream на FTP по указанному пути
     * example of call: uploadFile("file3.txt", "test_folder/file3.txt");
     *
     * @param localFile     - localFile to read, typeOf({@link java.io.File})
     * @param remoteFilePath    - remoteFilePath to write, typeOf(String)
     * */
    public void uploadFile(File localFile, String remoteFilePath) {
        //FTPClient ftpClient = new FTPClient();
        try {
            if (!localFile.exists()) throw new NullPointerException("Not found file!");

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            InputStream inputStream = new FileInputStream(localFile);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(remoteFilePath, inputStream); // путь на FTP куда писать, поток который нужно записать
            inputStream.close();
            if (done) System.out.println("The first file is uploaded successfully.");
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            //ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * метод для загрузки локального файла или InputStream на FTP по указанному пути
     * example of call: uploadFile("file3.txt", "test_folder/file3.txt");
     *
     * @param localInputStream     - localFile to read, typeOf({@link java.io.InputStream})
     * @param remoteFilePath    - remoteFilePath to write, typeOf(String)
     * */
    public void uploadFile(InputStream localInputStream, String remoteFilePath) {
        //FTPClient ftpClient = new FTPClient();
        try {
            if (localInputStream == null) throw new NullPointerException("Not found file!");

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(remoteFilePath, localInputStream); // путь на FTP куда писать, поток который нужно записать
            localInputStream.close();
            if (done) System.out.println("The first file is uploaded successfully.");
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            //ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public FTPFile[] getFiles(){
        FTPFile[] files = null;
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            files = ftpClient.listFiles();


        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return files;
    }
}
