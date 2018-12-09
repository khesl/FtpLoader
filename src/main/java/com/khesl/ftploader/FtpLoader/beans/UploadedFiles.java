package com.khesl.ftploader.FtpLoader.beans;

import javax.persistence.*;
import java.sql.Clob;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class UploadedFiles {

    private static final AtomicInteger AUTO_ID = new AtomicInteger(0);
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String fileName;
    private Date uploadDate;
    private Clob data;

    public UploadedFiles(){
        super();
        id = getNewId();
    }

    public UploadedFiles(String fileName, Date uploadDate, Clob data){
        this();
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.data = data;
    }

    public int getNewId(){
        return AUTO_ID.getAndIncrement();
    }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public Date getUploadDate() { return uploadDate; }
    public void setUploadDate(Date uploadDate) { this.uploadDate = uploadDate; }

    public Clob getData() { return data; }
    public void setData(Clob data) { this.data = data; }

    public long getId() {
        return id;
    }

    public String toString(){
        return "\"id\":\"" + id +
                "\", \"fileName\":\"" + fileName +
                "\", \"uploadDate\":\"" + uploadDate +
                "\", \"data_null\":\"" + (data == null) + "\"";
    }
}