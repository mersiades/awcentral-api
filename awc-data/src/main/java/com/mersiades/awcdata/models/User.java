package com.mersiades.awcdata.models;

public class User extends BaseEntity {

    private String email;
    private String password;
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
