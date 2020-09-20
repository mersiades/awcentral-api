package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.MoveKinds;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.enums.Stats;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "moves")
public class Move extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "stat")
    private Stats stat;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind")
    private MoveKinds kind;

    @Enumerated(EnumType.STRING)
    @Column(name = "playbook")
    private Playbooks playbook;

}
