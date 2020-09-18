package de.assecor.services;

import de.assecor.config.exception.CreateErrorException;
import de.assecor.constant.ColorEntryEnum;
import de.assecor.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.util.List;

public interface PersonService {

    Person createPerson(Person person) throws CreateErrorException;

    Person getById(long id) throws NoResultException;

    List<Person> getByColor(ColorEntryEnum color);

    List<Person> getPersons(int page, int limit);;

    void Upload(MultipartFile file);
}
