package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Roles;
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
    private Roles role;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    // TODO: delete relationship between Games and Users. Use GameRole instead
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "gameRole")
    private final Set<Npc> npcs = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "gameRole")
    private final Set<Threat> threats = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "gameRole")
    private Set<Character> characters = new HashSet<>();

    public GameRole() {
    }

    public GameRole(Roles role, Game game, User user) {
        this.role = role;
        this.game = game;
        this.user = user;
    }

    public GameRole(Roles role, User user) {
        this.role = role;
        this.user = user;
    }

    public GameRole(Long id, Roles role, Game game, User user) {
        super(id);
        this.role = role;
        this.game = game;
        this.user = user;
    }

    @Override
    public String toString() {
        return "GameRole [role= " + this.role + ", Game= " + this.getGame().getId() + ", User= " + this.getUser().getId() + "]";
    }
}
