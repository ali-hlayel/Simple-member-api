package com.assecor.personService.services;

import com.assecor.personService.utils.exception.EntityAlreadyExistsException;
import com.assecor.personService.constant.ColorEntryEnum;
import com.assecor.personService.entity.Person;
import com.assecor.personService.model.TestPersonFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
    void testCreate() throws EntityAlreadyExistsException {
        Person person = TestPersonFactory.createPerson();
        when(personRepository.save(any(Person.class))).thenReturn(person);
        Person result = personService.createPerson(person);
        assertEquals("Ali", result.getFirstName());
        assertEquals("Hlayel", result.getLastName());
        verify(personRepository).save(person);
    }

    @Test
    void testCreateThrowPersonAlreadyExists() {
        Person person = TestPersonFactory.createPerson();
        person.setId(1L);
        when(personRepository.existsByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(true);
        assertThrows(EntityAlreadyExistsException.class, () -> personService.createPerson(person));    }

    @Test
    void testGetById() {
        Person person = TestPersonFactory.createPerson();
        person.setId(1L);
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.of(person));
        Person result = personService.getById(1L);
        assertEquals(person.getFirstName(), result.getFirstName());
        Assertions.assertEquals(person.getColor(), result.getColor());
        verify(personRepository).findById(1L);
    }

    @Test
    void testGetByColor() {
        Person person = TestPersonFactory.createPerson();
        person.setId(1L);
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        when(personRepository.findByColor(any(ColorEntryEnum.class))).thenReturn(personList);
        List<Person> result = personService.getByColor(person.getColor());
        assertEquals(1, result.size());
    }

    @Test
    void getByIdReturnsNoResultException() {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(NoResultException.class, () -> personService.getById(1L));
        verify(personRepository).findById(1L);
    }

    @Test
    void testGet() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Person firstPerson = TestPersonFactory.createPerson();
        firstPerson.setId(1L);
        Person secondPerson = TestPersonFactory.createPerson();
        secondPerson.setId(2L);
        List<Person> personList = new ArrayList<>();
        personList.add(firstPerson);
        personList.add(secondPerson);
        Page<Person> personPage = new PageImpl<>(personList, pageRequest, personList.size());
        when(personRepository.findAll(pageRequest)).thenReturn(personPage);
        List<Person> results = personService.getPersons(0, 2);
        assertEquals(2, results.size());
        assertEquals(firstPerson.getFirstName(), results.get(0).getFirstName());
        assertEquals(secondPerson.getLastName(), results.get(1).getLastName());
        verify(personRepository).findAll(pageRequest);
    }
}