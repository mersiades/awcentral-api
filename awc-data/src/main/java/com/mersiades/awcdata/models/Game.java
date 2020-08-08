package com.mersiades.awcdata.models;

public class Game {

    private Long id;
    private String name;

    public Game(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
