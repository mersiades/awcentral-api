package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.UniqueType;
import com.mersiades.awccontent.models.uniquecreators.*;
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

    private BrainerGearCreator brainerGearCreator;

    private CustomWeaponsCreator customWeaponsCreator;

    private EstablishmentCreator establishmentCreator;

    private FollowersCreator followersCreator;

    private GangCreator gangCreator;

    private HoldingCreator holdingCreator;

    private SkinnerGearCreator skinnerGearCreator;

    private WeaponsCreator weaponsCreator;

    private WorkspaceCreator workspaceCreator;
}
