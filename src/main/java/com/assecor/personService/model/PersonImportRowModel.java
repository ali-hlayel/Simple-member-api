package com.assecor.personService.model;

import com.assecor.personService.constant.ColorEntryEnum;

public class PersonImportRowModel {

    private String firstName;

    private String lastName;

    private Long postCode;

    private String city;

    private ColorEntryEnum color;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPostCode() {
        return postCode;
    }

    public void setPostCode(Long postCode) {
        this.postCode = postCode;
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
