package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "pb_creators")
public class PlaybookCreator extends BaseEntity {

    @Column(name = "pb_type")
    private Playbooks playbookType;

    @Lob
    @Column(name = "gear_instr")
    private String gearInstructions;

    @Lob
    @Column(name = "imp_instr")
    private String improvementInstructions;

    @Lob
    @Column(name = "moves_instr")
    private String movesInstructions;

    @Lob
    @Column(name = "hx_instr")
    private String hxInstructions;

    @OneToOne
    @JoinColumn(name = "playbook_id")
    private Playbook playbook;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playbookCreator")
    private final Set<Name> names = new HashSet<>();

//    public void addName(Name name) {
//        this.names.add(name);
//    }
//
//    public void setNames(Set<Name> names) {
//        this.names.addAll(names);
//    }

    @Override
    public String toString() {
        return "PlaybookCreator{" +
                "playbookType=" + playbookType +
                '}';
    }
}
