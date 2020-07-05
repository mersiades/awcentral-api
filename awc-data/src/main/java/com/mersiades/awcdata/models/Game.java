package com.mersiades.awcdata.models;

public class Game extends BaseEntity {

    private String name;

    public Game(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
