package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.MoveKinds;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.enums.Stats;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Move  {

    @Id
    private String id;

    private String name;

    // Long string
    private String description;

    // Enum type string
    private Stats stat;

    // Enum type string
    private MoveKinds kind;

    // enum type string
    private Playbooks playbook;

    public Move(String name, String description, Stats stat, MoveKinds kind, Playbooks playbook) {
        this.name = name;
        this.description = description;
        this.stat = stat;
        this.kind = kind;
        this.playbook = playbook;
    }
}
