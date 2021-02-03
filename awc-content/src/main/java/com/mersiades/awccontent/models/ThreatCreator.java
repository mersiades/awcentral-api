package com.mersiades.awccontent.models;

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
public class ThreatCreator {

    @Id
    private String id;

    private String createThreatInstructions;

    private String essentialThreatInstructions;

    @Builder.Default
    private List<ThreatCreatorContent> threats = new ArrayList<>();

    @Builder.Default
    private List<String> threatNames = new ArrayList<>();


}
