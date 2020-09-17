package de.assecor.person.controller;

import de.assecor.person.person.PersonEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PersonService {

    PersonEntity create(PersonEntity person);

    PersonEntity getOne(long id);

    List<PersonEntity> getByColor(int colorValue);

    List<PersonEntity> get();

    void save(MultipartFile file);
}
