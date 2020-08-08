package com.mersiades.awcdata.models;

public class GameRole extends BaseEntity {

    private Long playerId;
    private Long gameId;
    private Role role;

    public GameRole(Long playerId, Long gameId, Role role) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.role = role;
    }

    public enum Role {
        MC,
        PLAYER
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getGameId() {
        return gameId;
    }

    public Role getRole() {
        return role;
    }
}
