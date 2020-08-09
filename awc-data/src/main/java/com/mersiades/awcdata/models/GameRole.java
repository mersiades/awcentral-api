package com.mersiades.awcdata.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"game", "user"}, callSuper = false)
@Entity
@Table(name = "game_roles")
public class GameRole extends BaseEntity {

    @Column(name = "role")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameRole")
    private final Set<Npc> npcs = new HashSet<>();

    public GameRole(Role role, Game game, User user) {
        this.role = role;
        this.game = game;
        this.user = user;
    }

    public enum Role {
        MC,
        PLAYER
    }

}
