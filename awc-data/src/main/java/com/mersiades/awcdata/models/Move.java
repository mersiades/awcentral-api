package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.MoveKinds;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.enums.Stats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Move  {

    @Id
    private String id;

    private String name;

    // Long string
    private String description;

    // Enum type string
    private Stats stat;

    private StatModifier statModifier;

    private RollModifier rollModifier;

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
