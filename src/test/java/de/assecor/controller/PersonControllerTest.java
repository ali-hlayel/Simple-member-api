package de.assecor.controller;

import de.assecor.entity.Person;
import de.assecor.person.PersonModel;
import de.assecor.person.TestPersonFactory;
import de.assecor.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class PersonControllerTest {

    @Mock
    private PersonService personService;

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private final String LINK = "/persons";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new PersonController(personService))
                .build();
        this.mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    }
    @Test
    void importCsvFile() {
    }

    @Test
    void getPersons() throws Exception {
        Person firstPerson = TestPersonFactory.createPerson();
        Person secondPerson = TestPersonFactory.createPerson();
        List<Person> addresses = Arrays.asList(firstPerson, secondPerson);
        when(personService.get()).thenReturn(addresses);
        this.mockMvc.perform(get(LINK))
                .andExpect(status().isOk());
    }
    @Test
    void getPerson() throws Exception {
        Person person = TestPersonFactory.createPerson();
        person.setId(1L);
        when(personService.getById(any(Long.class))).thenReturn(person);
        this.mockMvc.perform(get(LINK + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ali"));
    }

    @Test
    void getPersonWithIdThatIsNotFound() throws Exception {
        when(personService.getById(10L)).thenThrow(NoResultException.class);
    }

    @Test
    void createPerson() throws Exception {
        PersonModel personRequestCreateModel = TestPersonFactory.createPersonModel();
        when(personService.createPerson(any(Person.class))).thenReturn(TestPersonFactory.createPerson());

        this.mockMvc.perform(post(LINK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json(personRequestCreateModel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ali"));
    }
    @Test
    void testGetPersonByColor() {
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}

