package de.assecor.person.service;

import org.springframework.stereotype.Service;
import de.assecor.person.person.Person;

import java.util.List;

@Service("personService")
public class PersonServiceImpl implements PersonService {
/*
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person getById(Long id) {
        Person result = personRepository.findById(id).orElseThrow(
                () -> new NoResultException("N̨o person found with id: " + id)
        );
        return result;
    }
//here i need to rebuild better
    @Override
    public List<Person> getPersons(int page, int limit) {
        return personRepository.findAll();
    }
*/
}
