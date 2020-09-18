package de.assecor.person;

import de.assecor.constant.ColorEntryEnum;
import de.assecor.entity.Person;

public class TestPersonFactory {

    public static Person createPerson() {
        Person person = new Person();
        person.setName("Ali");
        person.setLastName("Hlayel");
        person.setCity("Jerusalem");
        person.setZipCode(1234L);
        person.setColor(ColorEntryEnum.blau.blau);
        return person;
    }

    public static PersonCreateModel createPersonModel() {
        PersonCreateModel person = new PersonCreateModel();
        person.setName("Ali");
        person.setLastName("Hlayel");
        person.setCity("Jerusalem");
        person.setZipCode(1234L);
        person.setColor(ColorEntryEnum.blau.blau);
        return person;
    }
}
