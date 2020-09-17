package de.assecor.services;

import com.opencsv.exceptions.CsvException;
import de.assecor.config.exception.CreateErrorException;
import de.assecor.config.helper.CsvReader;
import de.assecor.constant.ColorEntryEnum;
import de.assecor.entity.PersonEntity;
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
    public PersonEntity createPerson(PersonEntity person) throws CreateErrorException {

        if (person == null) {
            throw new IllegalArgumentException("Booking may not be null");
        }

        if (person.getId() != null) {
            Optional<PersonEntity> optionalExistingPerson = personRepository.findById(person.getId());
            if (optionalExistingPerson.isPresent()) {
                throw new CreateErrorException("Can't create person id: " + person.getId()
                        + ", person already exist");
            } else {
                throw new IllegalArgumentException("Create person can't have id !!");
            }
        }
        PersonEntity result = personRepository.save(person);
        return result;
    }

    @Override
    public PersonEntity getById(long id) throws NoResultException {
        PersonEntity result = personRepository.findById(id).orElseThrow(
                () -> new NoResultException("there is no member " + id)
        );
        return result;
    }

    @Override
    public List<PersonEntity> getByColor(ColorEntryEnum color) {
        List<PersonEntity> result = personRepository.findByColor(color);
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
    public List<PersonEntity> get() {
        return personRepository.findAll();
    }
}
