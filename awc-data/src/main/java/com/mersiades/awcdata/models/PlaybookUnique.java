package com.mersiades.awcdata.models;

import com.mersiades.awccontent.enums.UniqueType;
import com.mersiades.awcdata.models.uniques.AngelKit;
import com.mersiades.awcdata.models.uniques.BrainerGear;
import com.mersiades.awcdata.models.uniques.CustomWeapons;
import com.mersiades.awcdata.models.uniques.Vehicle;
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
public class PlaybookUnique {

    @Id
    private String id;

    private UniqueType type;

    private AngelKit angelKit;

    private CustomWeapons customWeapons;

    private BrainerGear brainerGear;

    @Builder.Default
    private List<Vehicle> vehicles = new ArrayList<>();
}
