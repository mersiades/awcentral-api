package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.LookCategories;
import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "looks")
public class Look extends BaseEntity {

    @Column(name = "pb_type")
    private Playbooks playbookType;

    @Column(name = "category")
    private LookCategories category;

    @Column(name = "look")
    private String look;

    @ManyToOne
    @JoinColumn(name = "playbook_creator_id")
    private PlaybookCreator playbookCreator;
}
