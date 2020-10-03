package com.mersiades.awcdata.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "discourse_id")
    private String discourseID;

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

    public User(Long id) {
        super(id);
    }

    public User(String discourseID, String username) {
        this.discourseID = discourseID;
        this.username = username;
    }

}
