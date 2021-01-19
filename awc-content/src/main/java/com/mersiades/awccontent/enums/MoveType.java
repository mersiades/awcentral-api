package com.mersiades.awccontent.enums;

public enum MoveType {
    CHARACTER("CHARACTER"),
    DEFAULT_CHARACTER("DEFAULT_CHARACTER"),
    BASIC("BASIC"),
    PERIPHERAL("PERIPHERAL"),
    BATTLE("BATTLE"),
    UNIQUE("UNIQUE"),
    ROAD_WAR("ROAD_WAR");

    private String code;

    private MoveType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
