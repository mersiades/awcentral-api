package com.mersiades.awccontent.enums;

public enum MoveActionType {
    // All moves that require a dice roll come under this category.
    // Has the sub-category "RollType" to cover various kinds of rolls
    // Handled by various mutations, see RollType documentation
    ROLL,

    // All moves that require no special handling except printing the move as a GameMessage
    // Handled by performPrintMove() mutation
    PRINT,

    // All moves that require the Character's barter to be reduced.
    // Just two moves: LIFESTYLE AND GIGS, and GIVE BARTER
    // Handled by performBarterMove() mutation
    BARTER,

    // All moves that require adjustments in Hx values (with no dice roll)
    // Only four moves: INFLICT HARM ON PC, HEAL PC HARM, ANGEL SPECIAL and CHOPPER SPECIAL
    // Handled by performInflictHarmMove(), performHealHarmMove(), performAngelSpecialMove, and performChopperSpecialMove()
    ADJUST_HX,

    // All moves that require an adjustment to an AngelKit's stock (with no dice roll)
    // Only three moves: SPEED THE RECOVERY OF SOMEONE, REVIVE SOMEONE, and TREAT AN NPC
    // Handled by: TODO
    STOCK,
}
