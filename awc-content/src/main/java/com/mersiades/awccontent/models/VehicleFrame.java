package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.VehicleFrameType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleFrame {

    @Id
    private String id;

    private VehicleFrameType frameType;

    private int massive;

    private String examples;

    private int battleOptionCount;
}
