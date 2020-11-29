package com.mersiades.awcdata.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GearInstructions {

    @Id
    private String id;

    // A sentence introducing default gear options for the playbook
    private String youGet;

    // Each string is a default gear item
    private List<String> youGetItems;

    // A sentence explaining the default gear, but with no list of items
    private String inAddition;

    // A sentence introducing a list of options from which the player must choose some from
    private String introduceChoice;

    // How many chooseableGear options can the player choose?
    private int numberCanChoose = 1;

    // Each string is a gear option
    private List<String> chooseableGear;

    // Instructions for prosthetic, vehicle etc for the playbook
    private String withMC;

    private int startingBarter;
}
