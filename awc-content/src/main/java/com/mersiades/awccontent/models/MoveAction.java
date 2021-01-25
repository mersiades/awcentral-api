package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.MoveActionType;
import com.mersiades.awccontent.enums.RollType;
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
public class MoveAction {

    @Id
    private String id;

    private MoveActionType actionType;

    // ------------------------------- For MoveActions with type ROLL ------------------------------- //
    private RollType rollType;

    private StatType statToRollWith;

    private HoldConditions holdConditions;

    private PlusOneForwardConditions plusOneForwardConditions;

    // ---------------------------------------------------------------------------------------------- //

}
