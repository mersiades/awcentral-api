package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.LookCategories;
import com.mersiades.awcdata.enums.Playbooks;
import lombok.Data;

@Data
public class Look {

    private String id;

    private Playbooks playbookType;

    private LookCategories category;

    private String look;

    private PlaybookCreator playbookCreator;
}
