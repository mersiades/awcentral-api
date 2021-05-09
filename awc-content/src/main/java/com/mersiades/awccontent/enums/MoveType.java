package com.mersiades.awccontent.enums;

public enum MoveType {
    /**
     * Moves are unofficially divided into two categories:
     * - action moves, which sees the character actually doing something in the game's story
     * - state moves, which sees the character's playbook change in some way (eg a stat increases)
     */

    // Moves in the character playbooks that the players can choose. Can be both action and state moves
    CHARACTER("CHARACTER"),

    // Moves in the character playbook that are there by default: the player can't choose them
    DEFAULT_CHARACTER("DEFAULT_CHARACTER"),

    // A set of 'basic' action moves
    BASIC("BASIC"),

    // A set of 'peripheral' action moves
    PERIPHERAL("PERIPHERAL"),

    // A set of 'battle' action moves
    BATTLE("BATTLE"),

    // A set moves attached to a character's Unique, namely the Angel's infirmary has some action moves
    UNIQUE("UNIQUE"),

    // A set of 'battle' action moves
    ROAD_WAR("ROAD_WAR"),

    // A set of state moves that increase character stats
    // This enum was added for the improvement moves,
    // but could be retroactively added to the CHARACTER moves that also increase stats
    IMPROVE_STAT("IMPROVE_STAT"),

    // A set of state moves that allow a player to select an extra CHARACTER move
    ADD_CHARACTER_MOVE("ADD_CHARACTER_MOVE"),

    // A set of state moves that allow players to adjust their character's Unique
    ADJUST_UNIQUE("ADJUST_UNIQUE"),

    // Two state moves that allow players to add a move form a different playbook to their character
    ADD_OTHER_PB_MOVE("ADD_OTHER_PB_MOVE"),

    // A set of state moves that let players add an additional Unique to their character
    ADD_UNIQUE("ADD_UNIQUE"),

    // A state move that adds a vehicle to the character playbook
    ADD_VEHICLE("ADD_VEHICLE"),

    // A state move that allows the player to increase a stat of their choice
    GENERIC_INCREASE_STAT("GENERIC_INCREASE_STAT"),

    // A state move that allows the player to retire their character
    RETIRE("RETIRE"),

    // A state move that allows the player play with a second character
    ADD_SECOND_CHARACTER("ADD_SECOND_CHARACTER"),

    // A state move that allows the player to change their character's playbook
    CHANGE_PLAYBOOK("CHANGE_PLAYBOOK"),

    // Two state moves that allow the player to advance 3 of the 6 basic moves
    IMPROVE_BASIC_MOVES("IMPROVE_BASIC_MOVES"),

    // Four state moves that the player can choose 'when life becomes untenable'
    DEATH("DEATH");

    private String code;

    private MoveType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
