package de.assecor.services;

import de.assecor.config.exception.CreateErrorException;
import de.assecor.constant.ColorEntryEnum;
import de.assecor.entity.PersonEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PersonService {

    PersonEntity createPerson(PersonEntity person) throws CreateErrorException;

    PersonEntity getById(long id);

    List<PersonEntity> getByColor(ColorEntryEnum color);

    List<PersonEntity> get();

    void Upload(MultipartFile file);
}
