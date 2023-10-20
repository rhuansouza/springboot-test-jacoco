package com.example.apitest.controller;

import com.example.apitest.entity.Person;
import com.example.apitest.repository.PersonRepository;
import com.example.apitest.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonResource {

    private PersonRepository personRepository;
    private PersonService personService;

    public PersonResource(PersonRepository personRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person){
        //this.personRepository.save(person);
        this.personService.save(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAll(){
        List<Person> persons = new ArrayList<>();
        persons = personRepository.findAll();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

   @GetMapping("/{id}")
    public ResponseEntity<Optional<Person>> getById(@PathVariable Integer id){
        Optional<Person> person;
        try{
            person = personRepository.findById(id);
            return new ResponseEntity<Optional<Person>>(person, HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<Optional<Person>>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Person>> deleteById(@PathVariable Integer id){
        Optional<Person> person;
        try{
            personRepository.deleteById(id);
            return new ResponseEntity<Optional<Person>>(HttpStatus.OK);
        }catch(NoSuchElementException e){
            return new ResponseEntity<Optional<Person>>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping ("/{id}")
    public Optional<ResponseEntity<Person>> update(@PathVariable Integer id, @RequestBody Person updatePerson){
        return Optional.of(personRepository.findById(id)
                .map(person -> {
                    person.setName(updatePerson.getName());
                    Person personUpdated = personRepository.save(person);
                    return ResponseEntity.ok().body(personUpdated);
                }).orElse(ResponseEntity.notFound().build()));
    }


}
