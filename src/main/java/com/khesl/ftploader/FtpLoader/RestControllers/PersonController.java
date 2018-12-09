package com.khesl.ftploader.FtpLoader.RestControllers;

import com.khesl.ftploader.FtpLoader.beans.Person;
import com.khesl.ftploader.FtpLoader.beans.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/persons")
class PersonController {

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable long id) {
        return new Person(("Name" + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Person person) {
        System.out.println(person);
    }

    @RequestMapping("/")
    Collection<Person> persons(){
        return this.personRepository.findAll();
    }
    @Autowired
    PersonRepository personRepository;
}