package com.assecor.personservice.services;

import com.assecor.personservice.config.exception.EntityAlreadyExistsException;
import com.assecor.personservice.constant.ColorEntryEnum;
import com.assecor.personservice.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person createPerson(Person person) throws EntityAlreadyExistsException {
        Person result;
        if (!personRepository.existsByNameAndLastName(person.getName(), person.getLastName())) {
            result = personRepository.save(person);
        } else
            throw new EntityAlreadyExistsException("Person with name  " + person.getName() + " " + person.getLastName() + " is already exists.");
        return result;
    }

    @Override
    public Person getById(long id) throws NoResultException {
        Person result = personRepository.findById(id).orElseThrow(
                () -> new NoResultException("There is no person " + id)
        );
        return result;
    }

    @Override
    public List<Person> getByColor(ColorEntryEnum color) {
        List<Person> result = personRepository.findByColor(color);
        return result;
    }

    @Override
    public List<Person> getPersons(int page, int limit) {
        if (page > 0) page = page - 1;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<Person> personsPage = personRepository.findAll(pageableRequest);
        List<Person> personsList = personsPage.getContent();
        return personsList;
    }

}
