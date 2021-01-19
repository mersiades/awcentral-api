package com.mersiades.awccontent.enums;

public enum StatType {
    COOL("COOL"),
    HARD("HARD"),
    HOT("HOT"),
    SHARP("SHARP"),
    WEIRD("WEIRD"),
    HX("HX");

    private final String code;

    StatType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
