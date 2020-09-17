package de.assecor.person.person;

public enum ColourEntryEnum {
    blau(1),
    grün(2),
    violett(3),
    rot(4),
    gelb(5),
    türkis(6),
    weiß(7);

    private int number;

    ColourEntryEnum(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
