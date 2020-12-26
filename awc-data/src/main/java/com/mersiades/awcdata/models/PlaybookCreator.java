package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaybookCreator {

    @Id
    private String id;

    private Playbooks playbookType;

    private GearInstructions gearInstructions;

    private String improvementInstructions;

    private String movesInstructions;

    private String hxInstructions;

    private Playbook playbook;

    private PlaybookUniqueCreator playbookUniqueCreator;

    @Builder.Default
    private List<Move> playbookMoves = new ArrayList<>();

    @Builder.Default
    private List<Name> names = new ArrayList<>();

    @Builder.Default
    private List<Look> looks = new ArrayList<>();

    @Builder.Default
    private List<StatsOption> statsOptions = new ArrayList<>();

    public PlaybookCreator(Playbooks playbookType, GearInstructions gearInstructions, String improvementInstructions, String movesInstructions, String hxInstructions) {
        this.playbookType = playbookType;
        this.gearInstructions = gearInstructions;
        this.improvementInstructions = improvementInstructions;
        this.movesInstructions = movesInstructions;
        this.hxInstructions = hxInstructions;
    }
}
