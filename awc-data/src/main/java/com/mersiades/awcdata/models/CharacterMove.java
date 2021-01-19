package com.mersiades.awcdata.models;

import com.mersiades.awccontent.enums.MoveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.mersiades.awccontent.models.Move;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterMove extends Move {

    private Boolean isSelected;

    /**
     * Use this constructor to create a CharacterMove as "selected" when a User intentionally chooses a move
     * @param move
     * @param isSelected
     * @return
     */
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

    /**
     * Use this constructor to create a CharacterMove with "selected" based on whether it is a default playbook move or not
     * @param move
     * @return
     */
    public static CharacterMove createFromMove(Move move) {
        CharacterMove characterMove = new CharacterMove();

        characterMove.setId(UUID.randomUUID().toString());
        characterMove.setName(move.getName());
        characterMove.setDescription(move.getDescription());
        characterMove.setStat(move.getStat());
        characterMove.setStatModifier(move.getStatModifier());
        characterMove.setRollModifier(move.getRollModifier());
        characterMove.setKind(move.getKind());
        characterMove.setPlaybook(move.getPlaybook());

        if (move.getKind().equals(MoveType.DEFAULT_CHARACTER)) {
            characterMove.setIsSelected(true);
        } else {
            characterMove.setIsSelected(false);
        }
        return characterMove;
    }
}
