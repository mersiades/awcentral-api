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
public class CarCreator {

    @Id
    private String id;

    private VehicleType vehicleType;

    private String introInstructions;

    private List<VehicleFrame> frames;

    @Builder.Default
    private List<String> strengths = new ArrayList<>();

    @Builder.Default
    private List<String> looks = new ArrayList<>();

    @Builder.Default
    private List<String> weaknesses = new ArrayList<>();

    @Builder.Default
    private List<VehicleBattleOption> battleOptions = new ArrayList<>();
}
