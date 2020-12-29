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

    private String description;

    private Stats stat;

    private StatModifier statModifier;

    private RollModifier rollModifier;

    private MoveKinds kind;

    private Playbooks playbook;

    public Move(String name, String description, Stats stat, MoveKinds kind, Playbooks playbook) {
        this.name = name;
        this.description = description;
        this.stat = stat;
        this.kind = kind;
        this.playbook = playbook;
    }
}
