package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(exclude = "playbookCreator", callSuper = false)
@Data
@Entity
@Table(name = "stats_options")
public class StatsOption extends BaseEntity {

    @Column(name = "pb_type")
    private Playbooks playbookType;

    @Column(name = "cool")
    private int COOL;

    @Column(name = "hard")
    private int HARD;

    @Column(name = "hot")
    private int HOT;

    @Column(name = "sharp")
    private int SHARP;

    @Column(name = "weird")
    private int WEIRD;

    @ManyToOne
    @JoinColumn(name = "playbook_creator_id")
    private PlaybookCreator playbookCreator;
}
