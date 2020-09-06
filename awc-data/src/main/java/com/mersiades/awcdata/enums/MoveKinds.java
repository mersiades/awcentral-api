package com.mersiades.awcdata.enums;

public enum MoveKinds {
    CHARACTER("CHARACTER"),
    BASIC("BASIC"),
    PERIPHERAL("PERIPHERAL"),
    BATTLE("BATTLE"),
    ROAD_WAR("ROAD_WAR");

    private String code;

    private MoveKinds(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
