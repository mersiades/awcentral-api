package com.mersiades.awcdata.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterMove extends Move {

    private Boolean isSelected;

    public static CharacterMove createFromMove(Move move, Boolean isSelected) {
        CharacterMove characterMove = new CharacterMove();

        characterMove.setId(UUID.randomUUID().toString());
        characterMove.setIsSelected(isSelected);

        characterMove.setName(move.getName());
        characterMove.setDescription(move.getDescription());
        characterMove.setStat(move.getStat());
        characterMove.setStatModifier(move.getStatModifier());
        characterMove.setRollModifier(move.getRollModifier());
        characterMove.setKind(move.getKind());
        characterMove.setPlaybook(move.getPlaybook());
        return characterMove;
    }
}
