package com.mersiades.awcdata.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
public class Npc {

    @Id
    private String id;

    private String name;

    private String description;

    // many to one
    private GameRole gameRole;

    public Npc() {
    }

    public Npc(GameRole gameRole, String name) {
        this.name = name;
    }

    public Npc(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Npc(GameRole gameRole, String name, String description) {
        this.name = name;
        this.description = description;
    }

}
