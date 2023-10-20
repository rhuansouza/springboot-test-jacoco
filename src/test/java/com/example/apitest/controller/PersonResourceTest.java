package com.example.apitest.controller;


import com.example.apitest.entity.Person;
import com.example.apitest.repository.PersonRepository;
import com.example.apitest.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = PersonResource.class)
@AutoConfigureMockMvc  //configura objeto para fazer as requisições da api
public class PersonResourceTest {

    static String API = "/person";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    PersonService personService;

    @Test
    @DisplayName("Deve Criar uma pessoa com sucesso")
    public void createPersonTest() throws Exception {
        Person person = Person.builder().age(33).name("Rhuan").build();

        //linha abaixo não foi necessária
        BDDMockito.given(personService.save(Mockito.any(Person.class))).willReturn(null);
        String json = new ObjectMapper().writeValueAsString(person);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                        .content(json);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("age").value("33"))
                .andExpect(jsonPath("name").value("Rhuan"));



    }
}
