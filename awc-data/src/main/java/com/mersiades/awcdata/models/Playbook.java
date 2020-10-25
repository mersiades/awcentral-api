package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Playbook  {

    @Id
    private String id;

    // enum type string
    private Playbooks playbookType;

    // long string
    private String barter_instructions;

    // long string
    private String intro;

    // long string
    private String intro_comment;

    private String playbook_image_url;

    // one to one
    private PlaybookCreator creator;

    public Playbook(Playbooks playbookType, String barter_instructions, String intro, String intro_comment) {
        this.playbookType = playbookType;
        this.barter_instructions = barter_instructions;
        this.intro = intro;
        this.intro_comment = intro_comment;
    }
}
