package com.mersiades.awccontent.enums;

public enum PlaybookType {
    ANGEL("ANGEL"),
    BATTLEBABE("BATTLEBABE"),
    BRAINER("BRAINER"),
    CHOPPER("CHOPPER"),
    DRIVER("DRIVER"),
    GUNLUGGER("GUNLUGGER"),
    HARDHOLDER("HARDHOLDER"),
    HOCUS("HOCUS"),
    MAESTRO_D("MAESTRO_D"),
    SAVVYHEAD("SAVVYHEAD"),
    SKINNER("SKINNER");

    private final String code;

    PlaybookType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
