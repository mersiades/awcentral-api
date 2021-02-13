package com.mersiades.awccontent.models.uniquecreators;

import com.mersiades.awccontent.models.SkinnerGearItem;
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
public class SkinnerGearCreator {

    @Id
    private String id;

    private int graciousWeaponCount;

    private int luxeGearCount;

    @Builder.Default
    private List<SkinnerGearItem> graciousWeaponChoices = new ArrayList<>();

    @Builder.Default
    private List<SkinnerGearItem> luxeGearChoices = new ArrayList<>();
}
