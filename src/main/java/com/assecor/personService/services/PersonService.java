package com.assecor.personService.services;

import com.assecor.personService.utils.exception.EntityAlreadyExistsException;
import com.assecor.personService.constant.ColorEntryEnum;
import com.assecor.personService.entity.Person;

import javax.persistence.NoResultException;
import java.util.List;

public interface PersonService {

    Person createPerson(Person person) throws EntityAlreadyExistsException;

    Person getById(long id) throws NoResultException;

    List<Person> getByColor(ColorEntryEnum color);

    List<Person> getPersons(int page, int limit);

}
