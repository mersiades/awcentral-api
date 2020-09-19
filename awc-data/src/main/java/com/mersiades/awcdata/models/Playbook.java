package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "playbooks")
public class Playbook extends BaseEntity {

    @Column(name = "pb_type")
    private Playbooks playbookType;

    @Lob
    @Column(name = "barter_instr")
    private String barter_instructions;

    @Lob
    @Column(name = "intro")
    private String intro;

    @Lob
    @Column(name = "intro_comment")
    private String intro_comment;

    @Column(name = "image_url")
    private String playbook_image_url;

    @OneToOne(mappedBy = "playbook", cascade = CascadeType.ALL)
    private PlaybookCreator creator;

}
