package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.Playbooks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatsOption {

    @Id
    private String id;

    private Playbooks playbookType;

    private int COOL;

    private int HARD;

    private int HOT;

    private int SHARP;

    private int WEIRD;

    // many to one
    private PlaybookCreator playbookCreator;

    public StatsOption(Playbooks playbookType, int COOL, int HARD, int HOT, int SHARP, int WEIRD) {
        this.playbookType = playbookType;
        this.COOL = COOL;
        this.HARD = HARD;
        this.HOT = HOT;
        this.SHARP = SHARP;
        this.WEIRD = WEIRD;
    }
}
