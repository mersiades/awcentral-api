package com.mersiades.awccontent.enums;

public enum Stats {
    COOL("COOL"),
    HARD("HARD"),
    HOT("HOT"),
    SHARP("SHARP"),
    WEIRD("WEIRD"),
    HX("HX");

    private final String code;

    Stats(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
