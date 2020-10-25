package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Roles;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class GameRole {

    private String id;

    private Roles role;

    private Game game;

    private User user;

    private final Set<Npc> npcs = new HashSet<>();

    private final Set<Threat> threats = new HashSet<>();

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

    public GameRole(String id, Roles role, Game game, User user) {
        this.id = id;
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
