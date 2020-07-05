package com.mersiades.awcdata.models;

public class GameRole extends BaseEntity {

    private Long player;
    private Long game;
    private Role role;

    public GameRole(Long player, Long game, Role role) {
        this.player = player;
        this.game = game;
        this.role = role;
    }

    public enum Role {
        MC,
        PLAYER
    }

    public Long getPlayer() {
        return player;
    }

    public Long getGame() {
        return game;
    }

    public Role getRole() {
        return role;
    }
}
