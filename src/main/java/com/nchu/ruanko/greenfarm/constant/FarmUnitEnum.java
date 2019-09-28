package com.nchu.ruanko.greenfarm.constant;

public enum FarmUnitEnum {

    UNIT_1(1, "元/亩/年"), UNIT_2(2, "元/亩"), UNIT_3(3, "元/年"), UNIT_4(4, "元");

    private int key;
    private String value;

    FarmUnitEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValue(int key) {
        for (FarmUnitEnum f : FarmUnitEnum.values()) {
            if (f.key == key) {
                return f.value;
            }
        }
        return null;
    }

}