package com.mersiades.awccontent.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleCreator {
    @Id
    private String id;

    private CarCreator carCreator;

    private BikeCreator bikeCreator;

    private CombatVehicleCreator combatVehicleCreator;
}
