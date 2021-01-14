package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Stats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
public class CharacterStat {
    @Id
    private String id;

    private Stats stat;

    private int value;

    private Boolean isHighlighted;

    // The id of StatModifierRepository applied to the CharacterStat
    private String modifier;

}
