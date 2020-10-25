package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Threats;
import lombok.Data;

@Data
public class Threat {

    private String id;

    /* The GameRole that created/owns the Threat */
    // many to one
    private GameRole gameRole;

    private String name;

    // enum type string
    private Threats threatKind;

    private String impulse;

    private String description;

    private String stakes;

    public Threat(String id) {
        this.id = id;
    }

    public Threat(GameRole gameRole, String name, Threats threatKind, String impulse) {
        this.gameRole = gameRole;
        this.name = name;
        this.threatKind = threatKind;
        this.impulse = impulse;
    }
}
