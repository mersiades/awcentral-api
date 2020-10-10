package com.mersiades.awcdata.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "discord_id")
    private String discordId;

    // TODO: delete relationship between Games and Users. Use GameRole instead
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name = "user_games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private final Set<Game> games = new HashSet<>();

    @OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "user")
    private final Set<GameRole> gameRoles = new HashSet<>();

    public User() {
    }

    public User(Long id) {
        super(id);
    }

    public User(String discordId) {
        this.discordId = discordId;
    }

}
