package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Name {

    @Id
    private String id;

    // enum type string
    private Playbooks playbookType;

    private String name;

    // Many to one
    private PlaybookCreator playbookCreator;

    public Name(Playbooks playbookType, String name) {
        this.playbookType = playbookType;
        this.name = name;
    }
}
