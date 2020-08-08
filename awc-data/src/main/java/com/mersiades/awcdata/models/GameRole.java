package com.mersiades.awcdata.models;

import java.util.HashSet;
import java.util.Set;

public class GameRole extends BaseEntity {

    private Role role;
    private Game game;
    private User user;
    private Set<Npc> npcs = new HashSet<>();

    public GameRole(Role role, Game game, User user) {
        this.role = role;
        this.game = game;
        this.user = user;
    }

    public enum Role {
        MC,
        PLAYER
    }

    public Role getRole() {
        return role;
    }

    public Game getGame() {
        return game;
    }

    public User getUser() {
        return user;
    }

    public Set<Npc> getNpcs() {
        return npcs;
    }
}
