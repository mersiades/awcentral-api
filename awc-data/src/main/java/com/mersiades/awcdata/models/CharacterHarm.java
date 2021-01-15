package com.mersiades.awcdata.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterHarm {

    @Id
    private String id;

    private int value;

    private Boolean isStabilized;

    private Boolean hasComeBackHard;

    private Boolean hasComeBackWeird;

    private Boolean hasChangedPlaybook;

    private Boolean hasDied;
}
