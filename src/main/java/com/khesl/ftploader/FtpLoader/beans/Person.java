package com.khesl.ftploader.FtpLoader.beans;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class Person {

    private static final AtomicInteger AUTO_ID = new AtomicInteger(0);
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String personName;

    public Person(){
    }

    public Person(String personName){
        super();
        this.personName = personName;
    }

    public String getPersonName() {
        return personName;
    }

    public long getId() {
        return id;
    }

    public String toString(){
        return "Id:'" + id + "', personName:'" + personName + "'";
    }
}