package de.assecor.person.controller;

import de.assecor.person.person.Person;
import de.assecor.person.person.PersonDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personService")
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonDbo create(PersonDbo person) {
        PersonDbo result = personRepository.save(person);
        return result;    }
}
