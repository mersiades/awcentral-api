package com.mersiades.awcdata.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "discourse_id")
    private Long discourseID;

    @Column(name = "username")
    private String username;

    @ManyToMany
    @JoinTable(name = "user_games", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private final Set<Game> games = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private final Set<GameRole> gameRoles = new HashSet<>();

    public User() {
    }

    public User(Long discourseID, String username) {
        this.discourseID = discourseID;
        this.username = username;
    }

    public Long getDiscourseID() {
        return discourseID;
    }

    public void setDiscourseID(Long discourseID) {
        this.discourseID = discourseID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Game> getGames() {
        return games;
    }

    public Set<GameRole> getGameRoles() {
        return gameRoles;
    }
}
