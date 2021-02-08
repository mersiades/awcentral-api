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

    private String surplusChange;

    @Builder.Default
    private List<String> wantChange = new ArrayList<>();

    private HoldingSize newHoldingSize;

    private String gigChange;

    private GangSize newGangSize;

    private String gangTagChange;

    private int gangHarmChange;

    private int newVehicleCount;

    private int newBattleVehicleCount;

    private int newArmorBonus;




}
