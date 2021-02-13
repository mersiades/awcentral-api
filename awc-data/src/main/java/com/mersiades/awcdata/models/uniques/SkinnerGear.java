package com.mersiades.awcdata.models.uniques;

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
public class SkinnerGear {

    @Id
    private String id;

    private SkinnerGearItem graciousWeapon;

    @Builder.Default
    private List<SkinnerGearItem> luxeGear = new ArrayList<>();
}
