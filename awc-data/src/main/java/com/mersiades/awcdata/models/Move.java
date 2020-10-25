package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.MoveKinds;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.enums.Stats;
import lombok.Data;

@Data
public class Move  {

    private String id;

    private String name;

    // Long string
    private String description;

    // Enum type string
    private Stats stat;

    // Enum type string
    private MoveKinds kind;

    // enum type string
    private Playbooks playbook;

}
