package com.zephir.fridgeapp.enums;

public enum KcalUnit {
    GRAMS_100("100g"),
    KG("1kg"),
    ITEM("item");

    private final String unit;

    KcalUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return this.unit;
    }
}