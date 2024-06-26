package com.example.demo.finance.controller;

import com.example.demo.finance.service.impl.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    @Autowired
    private PersonServiceImpl personService;




//    @GetMapping("/")
//    public List<Person> getAllPersons() {
//        return personService.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public Person getPerson(@PathVariable Long id) {
//        return personService.findById(id);
//    }
//
//    @PostMapping("/")
//    public void addPerson(@RequestBody Person person) {
//        personService.save(person);
//    }
//
//    @PutMapping("/")
//    public void updatePerson(@RequestBody Person person) {
//        personService.save(person);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deletePerson(@PathVariable Long id) {
//        personService.deleteById(id);
//    }
}
