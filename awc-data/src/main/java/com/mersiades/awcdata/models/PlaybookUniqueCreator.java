package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.UniqueType;
import com.mersiades.awcdata.models.uniquecreators.AngelKitCreator;
import com.mersiades.awcdata.models.uniquecreators.BrainerGearCreator;
import com.mersiades.awcdata.models.uniquecreators.CustomWeaponsCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaybookUniqueCreator {

    @Id
    private String id;

    private UniqueType type;

    private AngelKitCreator angelKitCreator;

    private CustomWeaponsCreator customWeaponsCreator;

    private BrainerGearCreator brainerGearCreator;

}
