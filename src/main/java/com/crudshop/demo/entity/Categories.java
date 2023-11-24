package com.crudshop.demo.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Categories {
    FRUIT("Фрукт"),
    VEGETABLES("Овощь"),
    MEAT("Мясо"),
    FISH("Рыба");

    private final String name;

    Categories(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
