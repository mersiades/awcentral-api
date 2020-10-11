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
        StringBuilder sb = new StringBuilder("GameRole [");
        if (this.getId() != null) {
            sb.append("id= ").append(this.getId());
        }

        if (this.role != null) {
            sb.append(", role= ").append(this.role);
        }

        if (this.getGame() != null) {
            System.out.println(this.getGame().getId());
            sb.append(", Game= ").append(this.getGame().getId());
        }

        if (this.getUser().getId() != null) {
            sb.append(", User= ").append(this.getUser().getId());
        }

        sb.append("]");

        return sb.toString();
    }
}
