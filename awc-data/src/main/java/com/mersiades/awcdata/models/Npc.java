package com.mersiades.awcdata.models;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
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

    public Npc(Long id, String name) {
        super(id);
        this.name = name;
    }

    public Npc(GameRole gameRole, String name, String description) {
        this.name = name;
        this.description = description;
    }

}
