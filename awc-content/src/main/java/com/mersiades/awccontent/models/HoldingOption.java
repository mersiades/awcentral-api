package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.GangSize;
import com.mersiades.awccontent.enums.HoldingSize;
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
public class HoldingOption {

    // new... means it replaces default value
    // ...Change means it modifies default value

    @Id
    private String id;

    private String description;

    // Ranges from -1 to 1, with -2 representing null
    private int surplusChange;

    private HoldingSize newHoldingSize;

    private String gigChange;

    private GangSize newGangSize;

    private String gangTagChange;

    // Ranges from -1 to 1, with -2 representing null
    private int gangHarmChange;

    // Ranges from 2 to 6, with - 1 representing null
    private int newVehicleCount;

    // Ranges from 2 to 7, with - 1 representing null
    private int newBattleVehicleCount;

    // Ranges from 0 - 2, with -1 representing null
    private int newArmorBonus;

    @Builder.Default
    private List<String> wantChange = new ArrayList<>();


}
