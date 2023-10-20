package com.example.apitest.service;

import com.example.apitest.entity.Person;
import com.example.apitest.repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PersonServiceTest {


    PersonService personService;

    @MockBean//Spring ja tem comportamento para os repositorys
    PersonRepository personRepository;


    @BeforeEach
    public void setUp(){
        this.personService = new PersonService(personRepository);
    }

    @Test
    @DisplayName("Deve Criar uma pessoa com sucesso")
    public void createPersonTest() throws Exception {
        //cenario
        Person person = Person.builder().age(33).name("Rhuan").build();
        Mockito.when(personRepository.save(person)).thenReturn(Person.builder().id(1).age(33).name("Rhuan").build());
        //execução
        Person savedPerson = personService.save(person);
        //verificação
        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getAge()).isEqualTo(person.getAge());
        assertThat(savedPerson.getName()).isEqualTo(person.getName());
        Mockito.verify(personRepository, Mockito.times(1)).save(person);
    }
}
