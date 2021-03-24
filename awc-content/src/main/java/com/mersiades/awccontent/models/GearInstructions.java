package com.mersiades.awccontent.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GearInstructions {

    @Id
    private String id;

    // A sentence introducing default gear options for the playbook
    private String gearIntro;

    // Each string is a default gear item
    @Builder.Default
    private List<String> youGetItems = new ArrayList<>();

    // A sentence introducing a list of options from which the player must choose some from
    private String introduceChoice;

    // How many chooseableGear options can the player choose?
    @Builder.Default
    private int numberCanChoose = 1;

    // Each string is a gear option
    @Builder.Default
    private List<String> chooseableGear = new ArrayList<>();

    // Instructions for prosthetic, vehicle etc for the playbook
    private String withMC;

    private int startingBarter;
}
