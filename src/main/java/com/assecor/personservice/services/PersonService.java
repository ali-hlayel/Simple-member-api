package com.assecor.personservice.services;

import com.assecor.personservice.config.exception.EntityAlreadyExistsException;
import com.assecor.personservice.constant.ColorEntryEnum;
import com.assecor.personservice.entity.Person;

import javax.persistence.NoResultException;
import java.util.List;

public interface PersonService {

    Person createPerson(Person person) throws EntityAlreadyExistsException;

    Person getById(long id) throws NoResultException;

    List<Person> getByColor(ColorEntryEnum color);

    List<Person> getPersons(int page, int limit);

}
