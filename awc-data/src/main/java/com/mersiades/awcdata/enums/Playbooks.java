package com.mersiades.awcdata.enums;

public enum Playbooks {
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

    private String code;

    private Playbooks(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
