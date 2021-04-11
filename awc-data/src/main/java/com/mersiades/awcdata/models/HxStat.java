package com.mersiades.awcdata.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HxStat {

    @Id
    private String id;

    private String characterId;

    private String characterName;

//    @Size(min = -2, max = 3, message = "Hx must be between -2 and 3")
    private int hxValue;
}
