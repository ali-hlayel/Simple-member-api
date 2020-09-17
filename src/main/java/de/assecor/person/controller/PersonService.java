package de.assecor.person.controller;

import de.assecor.person.person.PersonEntity;

public interface PersonService {

    PersonEntity create(PersonEntity person);

    PersonEntity getOne(long id);

}
