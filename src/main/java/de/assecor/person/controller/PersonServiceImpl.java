package de.assecor.person.controller;

import de.assecor.person.config.helper.CsvReader;
import de.assecor.person.person.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.List;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public PersonEntity create(PersonEntity person) {
        PersonEntity result = personRepository.save(person);
        return result;
    }

    @Override
    public PersonEntity getOne(long id) throws NoResultException {
        PersonEntity result = personRepository.findById(id).orElseThrow(
                () -> new NoResultException("there is no member " + id)
        );
        return result;
    }

    @Override
    public List<PersonEntity> getByColor(int colorValue) {
        List<PersonEntity> result = personRepository.findByColor(colorValue);
        return result;
    }

    @Override
    public void save(MultipartFile file) {
        try {
            List<PersonEntity> personEntities = CsvReader.csvToTutorials(file.getInputStream());
            personRepository.saveAll(personEntities);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    @Override
    public List<PersonEntity> get() {
        return personRepository.findAll();
    }
}
