package de.assecor.person.person;

import com.opencsv.bean.CsvBindByPosition;

public class Person {

    private Long id;

    private String name;

    private String lastName;

    private Long zipCode;

    private String city;

    private ColourEntryEnum color;

    public Person(long id, String name, String lastName, boolean zicode) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public ColourEntryEnum getColor() {
        return color;
    }

    public void setColor(ColourEntryEnum color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "person.Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", zipCode=" + zipCode +
                ", city='" + city + '\'' +
                ", color=" + color +
                '}';
    }
}
