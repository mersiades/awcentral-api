package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
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
}
