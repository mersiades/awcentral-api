package com.mersiades.awcdata.models.uniques;

import com.mersiades.awccontent.enums.GangSize;
import com.mersiades.awccontent.enums.HoldingSize;
import com.mersiades.awccontent.enums.UniqueType;
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
public class Holding {

    @Id
    private String id;

    @Builder.Default
    private UniqueType uniqueType = UniqueType.HOLDING;

    private HoldingSize holdingSize;

    private GangSize gangSize;

    // Determined by HoldingSize
    private String souls;

    // How much barter is gained when rolling the Wealth move
    private int surplus;

    // the barter 'balance'
    private int barter;

    private int gangHarm;

    private int gangArmor;

    private int gangDefenseArmorBonus;

    // The vehicles and battleVehicles, battleVehiclesCount and vehiclesCount
    // will be on the Character model directly

    @Builder.Default
    private List<String> wants = new ArrayList<>();

    @Builder.Default
    private List<String> gigs = new ArrayList<>();

    @Builder.Default
    private List<String> gangTags = new ArrayList<>();

    @Builder.Default
    private List<HoldingOption> selectedStrengths = new ArrayList<>();

    @Builder.Default
    private List<HoldingOption> selectedWeaknesses = new ArrayList<>();

}
