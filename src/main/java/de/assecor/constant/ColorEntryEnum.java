package de.assecor.constant;

public enum ColorEntryEnum {
    blau(1),
    grün(2),
    violett(3),
    rot(4),
    gelb(5),
    türkis(6),
    weiß(7);

    private int number;

    ColorEntryEnum(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}