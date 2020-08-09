package com.mersiades.awcdata.models;

import javax.persistence.*;

@Entity
@Table(name = "npcs")
public class Npc extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "game_role_id")
    private GameRole gameRole;

    public Npc() {
    }

    public Npc(GameRole gameRole, String name) {
        this.name = name;
    }

    public Npc(GameRole gameRole, String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GameRole getGameRole() {
        return gameRole;
    }

    public void setGameRole(GameRole gameRole) {
        this.gameRole = gameRole;
    }
}
