package com.khesl.ftploader.FtpLoader.beans;

import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class Visit {

    private static final AtomicInteger AUTO_ID = new AtomicInteger(0);
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String userName;
    private Date date;
    private String path;
    private String parameters;

    public Visit(){
        super();
        id = getNewId();
    }

    public Visit(String userName, Date date, String path, String parameters){
        this();
        this.userName = userName;
        this.date = date;
        this.path = path;
        this.parameters = parameters;
    }

    public int getNewId(){
        return AUTO_ID.getAndIncrement();
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) { this.userName = userName; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getParameters() { return parameters; }
    public void setParameters(String parameters) { this.parameters = parameters; }

    public long getId() {
        return id;
    }

    public String toString(){
        return "Id:'" + id + "', userName:'" + userName + "', date:'" + date + "'";
    }
}