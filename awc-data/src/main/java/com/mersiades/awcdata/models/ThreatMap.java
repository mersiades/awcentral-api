package com.mersiades.awcdata.models;

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
public class ThreatMap {

    @Id
    private String id;

    private String gameId;

    private String wonderNotes;

    @Builder.Default
    private List<Threat> threats = new ArrayList<>();
}
