package de.assecor.services;

import com.opencsv.exceptions.CsvException;
import de.assecor.config.exception.CreateErrorException;
import de.assecor.config.helper.CsvReader;
import de.assecor.constant.ColorEntryEnum;
import de.assecor.entity.Person;
import de.assecor.person.PersonImportRowModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person createPerson(Person person) throws CreateErrorException {

        Person result = personRepository.save(person);
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
    public void Upload(MultipartFile file) {
        try {
            List<PersonImportRowModel> personEntities = CsvReader.csvToTutorials((Reader) file);
            //personRepository.saveAll(personEntities);
        } catch (IOException | CsvException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    @Override
    public List<Person> get() {
        return personRepository.findAll();
    }
}
