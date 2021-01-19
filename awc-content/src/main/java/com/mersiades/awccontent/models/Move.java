package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.enums.StatType;
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

    private StatType stat;

    private StatModifier statModifier;

    private RollModifier rollModifier;

    private MoveType kind;

    private PlaybookType playbook;

    public Move(String name, String description, StatType stat, MoveType kind, PlaybookType playbook) {
        this.name = name;
        this.description = description;
        this.stat = stat;
        this.kind = kind;
        this.playbook = playbook;
    }
}
