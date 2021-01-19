package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.UniqueType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.mersiades.awccontent.models.uniquecreators.AngelKitCreator;
import com.mersiades.awccontent.models.uniquecreators.BrainerGearCreator;
import com.mersiades.awccontent.models.uniquecreators.CustomWeaponsCreator;
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
