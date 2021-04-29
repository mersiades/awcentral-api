package com.mersiades.awccontent.content;

import com.mersiades.awccontent.enums.MoveActionType;
import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.enums.RollType;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.models.MoveAction;
import org.bson.types.ObjectId;

import static com.mersiades.awccontent.constants.MoveNames.adjustAngelUnique1Name;
import static com.mersiades.awccontent.constants.MoveNames.underFireName;
import static com.mersiades.awccontent.enums.StatType.COOL;

public class MovesContent {

    public static final MoveAction doSomethingUnderFireAction = MoveAction.builder()
            .id(new ObjectId().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(COOL)
            .build();
    public static final Move doSomethingUnderFire = Move.builder()
            .name(underFireName)
            .description("When you _**do something under fire**_, or dig in to endure fire, roll+cool.\n" +
                    "\n" +
                    "On a 10+, you do it.\n" +
                    "\n" +
                    "On a 7–9, you flinch, hesitate, or stall: the MC can offer you a worse outcome, a hard bargain, or an ugly choice.\n" +
                    "\n" +
                    "On a miss, be prepared for the worst.")
            .kind(MoveType.BASIC)
            .moveAction(doSomethingUnderFireAction)
            .playbook(null)
            .build();

    public static final Move adjustAngelUnique1 = Move.builder()
            .name(adjustAngelUnique1Name)
            .description("get a supplier (_cf_, detail with the MC)\n")
            .kind(MoveType.ADJUST_UNIQUE)
            .playbook(PlaybookType.ANGEL)
            .build();
}
