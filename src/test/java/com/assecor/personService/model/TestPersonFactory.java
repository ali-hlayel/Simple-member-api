package com.assecor.personService.model;

import com.assecor.personService.constant.ColorEntryEnum;
import com.assecor.personService.entity.Person;

public class TestPersonFactory {

    public static Person createPerson() {
        Person person = new Person();
        person.setFirstName("Ali");
        person.setLastName("Hlayel");
        person.setCity("Jerusalem");
        person.setZipCode(1234L);
        person.setColor(ColorEntryEnum.blau);
        return person;
    }

    public static PersonCreateModel createPersonModel() {
        PersonCreateModel person = new PersonCreateModel();
        person.setFirstName("Ali");
        person.setLastName("Hlayel");
        person.setCity("Jerusalem");
        person.setZipCode(1234L);
        person.setColor(ColorEntryEnum.blau);
        return person;
    }

    public static PersonImportRowModel createPersonImportRowModel() {
        PersonImportRowModel personImportRowModel = new PersonImportRowModel();
        personImportRowModel.setFirstName("Ali");
        personImportRowModel.setLastName("Hlayel");
        personImportRowModel.setColor(ColorEntryEnum.blau);
        personImportRowModel.setCity("Jerusalem");
        personImportRowModel.setZipCode(1234L);
        return personImportRowModel;
    }
}
