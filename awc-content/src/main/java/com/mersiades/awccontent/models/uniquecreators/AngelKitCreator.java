package com.mersiades.awccontent.models.uniquecreators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AngelKitCreator {

    @Id
    private String id;

    // ---------------------------------------------- ANGEL KIT --------------------------------------------------- //
    // No creator necessary for the ANGEL_KIT because the player has not choices for the ANGEL_KIT
    // However, to maintain consistency in the character creation process, a minimal unique creator is included
    private String angelKitInstructions;

    private int startingStock;
}
