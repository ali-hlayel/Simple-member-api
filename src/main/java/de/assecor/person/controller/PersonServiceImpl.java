package de.assecor.person.controller;

import de.assecor.person.person.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service("personService")
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonEntity create(PersonEntity person) {

        PersonEntity result = personRepository.save(person);
        return result;    }

    @Override
    public PersonEntity getOne(long id) throws NoResultException {
        PersonEntity result = personRepository.findById(id).orElseThrow(
                () -> new NoResultException("there is no member" + id)
        );
        return result;
    }
}
