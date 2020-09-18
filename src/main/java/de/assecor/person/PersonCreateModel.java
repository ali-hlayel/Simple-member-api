package de.assecor.person;

import de.assecor.constant.ColorEntryEnum;

public class PersonCreateModel {

    private String name;

    private String lastName;

    private Long zipCode;

    private String city;

    private ColorEntryEnum color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getZipCode() {
        return zipCode;
    }

    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ColorEntryEnum getColor() {
        return color;
    }

    public void setColor(ColorEntryEnum color) {
        this.color = color;
    }
}
