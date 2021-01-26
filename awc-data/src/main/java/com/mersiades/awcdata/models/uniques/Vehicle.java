package com.mersiades.awcdata.models.uniques;

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
public class Vehicle {

    @Id
    private String id;

    private VehicleFrame vehicleFrame;

    private int speed;

    private int handling;

    private int armor;

    private int massive;

    @Builder.Default
    private List<String> tags = new ArrayList<>();

    @Builder.Default
    private List<VehicleBattleOption> battleOptions = new ArrayList<>();



}
