package com.assecor.personService.constant;

import java.util.HashMap;
import java.util.Map;

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

    static Map<Integer, ColorEntryEnum> map = new HashMap<>();

    static {
        for (ColorEntryEnum color : ColorEntryEnum.values()) {
            map.put(color.number, color);
        }
    }

    public static ColorEntryEnum getByNumber(int number) {
        return map.get(number);
    }
}