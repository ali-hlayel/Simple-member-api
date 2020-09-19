package de.assecor.person;

public class PersonImportRowModel {

    private String name;

    private String lastName;

    private Long postZeil;

    private String city;

    private int color;

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

    public Long getPostZeil() {
        return postZeil;
    }

    public void setPostZeil(Long postZeil) {
        this.postZeil = postZeil;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "PersonImportRowModel{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", postZeil='" + postZeil + '\'' +
                ", address='" + city + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
