package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;

@Data
public class Playbook  {

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

}
