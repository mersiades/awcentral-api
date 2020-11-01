package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Roles;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
public class GameRole {

    @Id
    private String id;

    private Roles role;

    @DBRef
    private Game game;

    @DBRef
    private User user;

    private Set<Npc> npcs = new HashSet<>();

    private Set<Threat> threats = new HashSet<>();

    private Set<Character> characters = new HashSet<>();

    public GameRole() {
    }
    public GameRole(Roles role) {
        this.role = role;
    }

    public GameRole(String id, Roles role) {
        this.id = id;
        this.role = role;
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
            sb.append(", Game= ").append(this.getGame().getId());
        }

        if (this.getUser() == null) {
            sb.append(", User= null");
        } else if (this.getUser().getId() != null) {
            sb.append(", User= ").append(this.getUser().getId());
        }

        sb.append("]");

        return sb.toString();
    }
}
