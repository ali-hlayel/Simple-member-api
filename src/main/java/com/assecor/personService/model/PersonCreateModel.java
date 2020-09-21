package com.assecor.personService.model;

import com.assecor.personService.constant.ColorEntryEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PersonCreateModel {

    @ApiModelProperty(required = true, example = "Ali")
    @NotBlank
    private String firstName;

    @ApiModelProperty(required = true, example = "Hlayel")
    @NotBlank
    private String lastName;

    @ApiModelProperty(required = true, example = "12345", notes = "postCode should be at least five numbers")
    @NotNull
    @Min(5)
    private Long postCode;

    @ApiModelProperty(required = true, example = "Jersualem")
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$")
    private String city;

    @ApiModelProperty(required = true, example = "rot")
    @NotNull
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
