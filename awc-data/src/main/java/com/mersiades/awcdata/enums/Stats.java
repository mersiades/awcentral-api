package com.mersiades.awcdata.enums;

public enum Stats {
    COOL("COOL"),
    HARD("HARD"),
    HOT("HOT"),
    SHARP("SHARP"),
    WEIRD("WEIRD");

    private String code;

    private Stats(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
