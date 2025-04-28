package com.example.pidev.entity.activities;


public class Replacement {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Replacement{" +
                "value='" + value + '\'' +
                '}';
    }
}