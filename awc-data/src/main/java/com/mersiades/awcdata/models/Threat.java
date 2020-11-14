package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Threats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Threat {

    @Id
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

    public Threat(String name, Threats threatKind, String impulse) {
        this.name = name;
        this.threatKind = threatKind;
        this.impulse = impulse;
    }
}
