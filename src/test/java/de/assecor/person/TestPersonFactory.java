package de.assecor.person;

import de.assecor.constant.ColorEntryEnum;
import de.assecor.entity.PersonEntity;

public class TestPersonFactory {

    public static PersonEntity createPerson() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName("Ali");
        personEntity.setLastName("Hlayel");
        personEntity.setCity("Jerusalem");
        personEntity.setZipCode(1234L);
        personEntity.setColor(ColorEntryEnum.blau.blau);
        return personEntity;
    }
}
