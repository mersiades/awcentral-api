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

    // Some of the basic moves have extra instruction for when the character rolls over 12
    private String twelvePlusInstructions;

    private StatType stat;

    private StatModifier statModifier;

    private RollModifier rollModifier;

    private MoveType kind;

    private PlaybookType playbook;

    private MoveAction moveAction;
}
