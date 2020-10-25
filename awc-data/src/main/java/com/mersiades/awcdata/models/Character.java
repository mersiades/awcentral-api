package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Character {

    @Id
    private String id;

    private String name;

    // TODO: add stats

    // TODO: add harm

    private Playbooks playbook;

    private String gear;

    private GameRole gameRole;

    public Character() {
    }

    public Character(String name, GameRole gameRole, Playbooks playbook, String gear) {
        this.name = name;
        this.gameRole = gameRole;
        this.playbook = playbook;
        this.gear = gear;
    }

    public Character(String id, String name, GameRole gameRole, Playbooks playbook, String gear) {
        this.id = id;
        this.name = name;
        this.gameRole = gameRole;
        this.playbook = playbook;
        this.gear = gear;
    }
}
