package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.StatType;
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
    private List<StatType> statToRollWith = new ArrayList<>();
}
