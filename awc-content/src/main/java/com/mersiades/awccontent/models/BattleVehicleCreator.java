package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BattleVehicleCreator {

    @Id
    private String id;

    private VehicleType vehicleType;

    @Builder.Default
    private List<VehicleBattleOption> battleVehicleOptions = new ArrayList<>();
}
