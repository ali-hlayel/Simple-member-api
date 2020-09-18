package de.assecor.services;

import com.opencsv.exceptions.CsvException;
import de.assecor.config.exception.CreateErrorException;
import de.assecor.config.helper.CsvReader;
import de.assecor.constant.ColorEntryEnum;
import de.assecor.entity.Person;
import de.assecor.person.PersonImportRowModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

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
            List<PersonImportRowModel> personEntities = CsvReader.csvToEntity((Reader) file);
            //personRepository.saveAll(personEntities);
        } catch (IOException | CsvException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    @Override
    public List<Person> getPersons(int page, int limit) {
        if(page>0) page = page-1;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<Person> personsPage = personRepository.findAll(pageableRequest);
        List<Person> personsList = personsPage.getContent();
        return personsList;
    }

}
