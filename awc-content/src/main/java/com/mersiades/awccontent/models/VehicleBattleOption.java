package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.BattleOptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleBattleOption {

    @Id
    private String id;

    private BattleOptionType battleOptionType;

    private String name;

}
