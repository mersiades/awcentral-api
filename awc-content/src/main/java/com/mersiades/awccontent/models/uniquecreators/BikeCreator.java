package com.mersiades.awccontent.models.uniquecreators;

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
public class BikeCreator {

    @Id
    private String id;

    private String introInstructions;

    private VehicleFrame frame;

    @Builder.Default
    private List<String> strengths = new ArrayList<>();

    @Builder.Default
    private List<String> looks = new ArrayList<>();

    @Builder.Default
    private List<String> weaknesses = new ArrayList<>();

    @Builder.Default
    private List<VehicleBattleOption> battleOptions = new ArrayList<>();
}
