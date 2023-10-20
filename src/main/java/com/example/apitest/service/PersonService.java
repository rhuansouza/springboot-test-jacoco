package com.example.apitest.service;

import com.example.apitest.entity.Person;
import com.example.apitest.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;



    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public Person save(Person person){
        return this.personRepository.save(person);
    }
}
