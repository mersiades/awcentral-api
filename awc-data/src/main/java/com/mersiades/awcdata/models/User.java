package com.mersiades.awcdata.models;

import java.util.HashSet;
import java.util.Set;

public class User {
    private Long id;
    private String userName;
    private Set<Game> games = new HashSet<>();
    private Set<GameRole> gameRoles = new HashSet<>();

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Game> getGames() {
        return games;
    }

    public Set<GameRole> getGameRoles() {
        return gameRoles;
    }
}
