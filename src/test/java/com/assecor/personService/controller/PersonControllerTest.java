package com.assecor.personService.controller;

import com.assecor.personService.constant.ColorEntryEnum;
import com.assecor.personService.entity.Person;
import com.assecor.personService.model.PersonCreateModel;
import com.assecor.personService.model.PersonImportRowModel;
import com.assecor.personService.model.TestPersonFactory;
import com.assecor.personService.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class PersonControllerTest {

    @Mock
    private PersonService personService;

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private final String LINK = "/person-service";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new PersonController(personService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        this.mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    }

    @Test
    void testImportCsvFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "name,lastName,address,color".getBytes());
        PersonImportRowModel personImportRowModel = TestPersonFactory.createPersonImportRowModel();

        this.mockMvc.perform(multipart(LINK + "/import")
                .file(file))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPersons() throws Exception {
        Person firstPerson = TestPersonFactory.createPerson();
        Person secondPerson = TestPersonFactory.createPerson();
        List<Person> addresses = Arrays.asList(firstPerson, secondPerson);
        when(personService.getPersons(0, 2)).thenReturn(addresses);
        this.mockMvc.perform(get(LINK + "/persons"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPerson() throws Exception {
        Person person = TestPersonFactory.createPerson();
        person.setId(1L);
        when(personService.getById(any(Long.class))).thenReturn(person);
        this.mockMvc.perform(get(LINK + "/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ali"));
    }

    @Test
    void testGetPersonWithIdThatIsNotFound() throws Exception {
        when(personService.getById(10L)).thenThrow(NoResultException.class);
        this.mockMvc.perform(get(LINK + "/person/10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreatePerson() throws Exception {
        PersonCreateModel personRequestCreateModel = TestPersonFactory.createPersonModel();
        when(personService.createPerson(any(Person.class))).thenReturn(TestPersonFactory.createPerson());

        this.mockMvc.perform(post(LINK + "/person")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json(personRequestCreateModel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ali"));
    }

    @Test
    void testGetPersonByColor() throws Exception {
        Person firstPerson = TestPersonFactory.createPerson();
        Person secondPerson = TestPersonFactory.createPerson();
        List<Person> personsList = Arrays.asList(firstPerson, secondPerson);
        when(personService.getByColor(any(ColorEntryEnum.class))).thenReturn(personsList);
        this.mockMvc.perform(get(LINK + "/person/color/rot"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPersonsByColorsWithIdNoResults() throws Exception {
        when(personService.getByColor(any(ColorEntryEnum.class))).thenThrow(NoResultException.class);
        this.mockMvc.perform(get(LINK + "/person/color/rot"))
                .andExpect(status().isNoContent());
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}

