package de.assecor.services;

import de.assecor.config.exception.CreateErrorException;
import de.assecor.entity.Person;
import de.assecor.person.TestPersonFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void testCreate() throws CreateErrorException {
        Person person = TestPersonFactory.createPerson();
        when(personRepository.save(any(Person.class))).thenReturn(person);
        Person result = personService.createPerson(person);
        assertEquals("Ali", result.getName());
        assertEquals("Hlayel", result.getLastName());
        verify(personRepository).save(person);
    }

    @Test
    void testGetById() {
        Person person = TestPersonFactory.createPerson();
        person.setId(1L);
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.of(person));
        Person result = personService.getById(1L);
        assertEquals(person.getName(), result.getName());
        assertEquals(person.getColor(), result.getColor());
        verify(personRepository).findById(1L);
    }

    @Test
    void getByIdReturnsNoResultException() {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(NoResultException.class, () -> personService.getById(1L));
        verify(personRepository).findById(1L);
    }

    @Test
    void testGetByColor() {
    }

    @Test
    void testSave() {
    }

    @Test
    void testGet() {
        Person firstPerson = TestPersonFactory.createPerson();
        firstPerson.setId(1L);
        Person secondPerson = TestPersonFactory.createPerson();
        secondPerson.setId(2L);
        List<Person> personList = new ArrayList<>();
        personList.add(firstPerson);
        personList.add(secondPerson);
        when(personRepository.findAll()).thenReturn(personList);

        List<Person> results = personService.get();
        assertEquals(2, results.size());
        assertEquals(firstPerson.getName(), results.get(0).getName());
        assertEquals(secondPerson.getLastName(), results.get(1).getLastName());
        verify(personRepository).findAll();
    }
}