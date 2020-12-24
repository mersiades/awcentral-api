package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.Stats;
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
public class RollModifier {

    @Id
    private String id;

    @Builder.Default
    private List<Move> movesToModify = new ArrayList<>();

    @Builder.Default
    private List<Stats> statToRollWith = new ArrayList<>();
}
