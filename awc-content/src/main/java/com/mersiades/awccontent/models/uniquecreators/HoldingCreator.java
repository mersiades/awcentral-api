package com.mersiades.awccontent.models.uniquecreators;

import com.mersiades.awccontent.enums.GangSize;
import com.mersiades.awccontent.enums.HoldingSize;
import com.mersiades.awccontent.models.HoldingOption;
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
public class HoldingCreator {

    @Id
    private String id;

    // HoldingSize provided by enum
    private static final HoldingSize defaultHoldingSize = HoldingSize.MEDIUM;

    // GangSize provided by enum

    // Souls calculated on setHolding based on HoldingSize

    private String instructions;

    @Builder.Default
    private static final List<String> defaultGigs = new ArrayList<>(List.of("hunting", "crude farming", "scavenging"));

    private static final int defaultArmorBonus = 1;

    private static final int defaultVehiclesCount = 4;

    private static final int defaultBattleVehicleCount = 4;

    private static final GangSize defaultGangSize = GangSize.MEDIUM;

    private static final int defaultGangHarm = 2;

    private static final int defaultGangArmor = 1;

    private static final String defaultGangTag = "unruly";

    private static final int strengthCount = 4;

    private static final int weaknessCount = 2;

    @Builder.Default
    private List<HoldingOption> strengthOptions = new ArrayList<>();

    @Builder.Default
    private List<HoldingOption> weaknessOptions = new ArrayList<>();


}
