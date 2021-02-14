package com.mersiades.awcdata.models;

import com.mersiades.awccontent.enums.VehicleType;
import com.mersiades.awccontent.models.VehicleBattleOption;
import com.mersiades.awccontent.models.VehicleFrame;
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
public class BattleVehicle {

    @Id
    private String id;

    private String name;

    private VehicleType vehicleType;

    private VehicleFrame vehicleFrame;

    private int speed;

    private int handling;

    private int armor;

    private int massive;

    @Builder.Default
    private List<String> strengths = new ArrayList<>();

    @Builder.Default
    private List<String> weaknesses = new ArrayList<>();

    @Builder.Default
    private List<String> looks = new ArrayList<>();

    @Builder.Default
    private List<String> weapons = new ArrayList<>();

    @Builder.Default
    private List<VehicleBattleOption> battleOptions = new ArrayList<>();

    @Builder.Default
    private List<VehicleBattleOption> battleVehicleOptions = new ArrayList<>();



}
