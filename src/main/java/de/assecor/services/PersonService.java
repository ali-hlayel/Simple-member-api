package de.assecor.services;

import de.assecor.config.exception.CreateErrorException;
import de.assecor.constant.ColorEntryEnum;
import de.assecor.entity.Person;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PersonService {

    Person createPerson(Person person) throws CreateErrorException;

    Person getById(long id);

    List<Person> getByColor(ColorEntryEnum color);

    List<Person> get();

    void Upload(MultipartFile file);
}