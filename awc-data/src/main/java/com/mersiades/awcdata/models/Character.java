package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(exclude = "gameRole", callSuper = false)
@Data
@Entity
@Table(name = "characters")
public class Character extends BaseEntity {

    @Column(name = "name")
    private String name;

    // TODO: add stats

    // TODO: add harm

    @Column(name = "playbook")
    private Playbooks playbook;

    @Lob
    @Column(name = "gear")
    private String gear;

    @ManyToOne
    @JoinColumn(name = "game_role_id")
    private GameRole gameRole;

    public Character() {
    }

    public Character(String name, GameRole gameRole, Playbooks playbook, String gear) {
        this.name = name;
        this.gameRole = gameRole;
        this.playbook = playbook;
        this.gear = gear;
    }

    public Character(Long id, String name, GameRole gameRole, Playbooks playbook, String gear) {
        super(id);
        this.name = name;
        this.gameRole = gameRole;
        this.playbook = playbook;
        this.gear = gear;
    }




}
