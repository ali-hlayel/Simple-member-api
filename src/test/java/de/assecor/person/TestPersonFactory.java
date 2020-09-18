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

    public static PersonModel createPersonModel() {
        PersonModel person = new PersonModel();
        person.setName("Ali");
        person.setLastName("Hlayel");
        person.setCity("Jerusalem");
        person.setZipCode(1234L);
        person.setColor(ColorEntryEnum.blau.blau);
        return person;
    }
}
