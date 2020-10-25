package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PlaybookCreator {

    // enum type string
    private Playbooks playbookType;

    // long string
    private String gearInstructions;

    // long string
    private String improvementInstructions;

    // long string
    private String movesInstructions;

    // long string
    private String hxInstructions;

    // one to one
    private Playbook playbook;

    // one to many
    private final Set<Name> names = new HashSet<>();

    // one to many
    private final Set<Look> looks = new HashSet<>();

    // one to many
    private final Set<StatsOption> statsOptions = new HashSet<>();

    @Override
    public String toString() {
        return "PlaybookCreator{" +
                "playbookType=" + playbookType +
                '}';
    }
}
