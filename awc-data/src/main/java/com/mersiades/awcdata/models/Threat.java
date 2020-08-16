package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Threats;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = "gameRole", callSuper = false)
@Entity
@Table(name = "threats")
public class Threat extends BaseEntity {

    /* The GameRole that created/owns the Threat */
    @ManyToOne
    @JoinColumn(name = "game_role_id")
    private GameRole gameRole;

    @Column(name = "name")
    private String name;

    @Column(name = "threat_kind")
    private Threats threatKind;

    @Column(name = "impulse")
    private String impulse;

    @Column(name = "description")
    private String description;

    @Column(name = "stakes")
    private String stakes;

    public Threat() {
    }

    public Threat(Long id) {
        super(id);
    }

    public Threat(GameRole gameRole, String name, Threats threatKind, String impulse) {
        this.gameRole = gameRole;
        this.name = name;
        this.threatKind = threatKind;
        this.impulse = impulse;
    }
}
