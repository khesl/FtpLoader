package com.khesl.ftploader.FtpLoader.RestControllers;

import com.khesl.ftploader.FtpLoader.beans.Person;
import com.khesl.ftploader.FtpLoader.beans.PersonRepository;
import com.khesl.ftploader.FtpLoader.beans.Visit;
import com.khesl.ftploader.FtpLoader.beans.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/visit")
class VisitController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Person person) {
        System.out.println(person);
    }

    @RequestMapping("/")
    Collection<Visit> visits(){
        return this.visitRepository.findAll();
    }

    @Autowired
    VisitRepository visitRepository;
}