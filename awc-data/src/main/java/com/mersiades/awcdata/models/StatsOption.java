package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;

@Data
public class StatsOption {

    private String id;

    private Playbooks playbookType;

    private int COOL;

    private int HARD;

    private int HOT;

    private int SHARP;

    private int WEIRD;

    // many to one
    private PlaybookCreator playbookCreator;
}
