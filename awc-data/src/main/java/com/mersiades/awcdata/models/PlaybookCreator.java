package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Data
public class PlaybookCreator {

    @Id
    private String id;

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

    public PlaybookCreator(Playbooks playbookType, String gearInstructions, String improvementInstructions, String movesInstructions, String hxInstructions) {
        this.playbookType = playbookType;
        this.gearInstructions = gearInstructions;
        this.improvementInstructions = improvementInstructions;
        this.movesInstructions = movesInstructions;
        this.hxInstructions = hxInstructions;
    }
}
