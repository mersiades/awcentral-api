package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.PlaybookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImprovementBlock {

    @Id
    private String id;

    private PlaybookType playbookType;

    private String improvementInstructions;

    @Builder.Default
    private List<Move> improvementMoves = new ArrayList<>();

    @Builder.Default
    private List<Move> futureImprovementMoves = new ArrayList<>();
}
