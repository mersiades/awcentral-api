package com.mersiades.awcdata.models;

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
public class StatsBlock {

    @Id
    private String id;

    private String statsOptionId;

    @Builder.Default
    private List<CharacterStat> stats = new ArrayList<>();
}
