package com.mersiades.awccontent.models;

import com.mersiades.awccontent.enums.ThreatType;
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
public class ThreatCreatorContent {

    @Id
    private String id;

    private ThreatType threatType;

    @Builder.Default
    private List<String> impulses = new ArrayList<>();

    @Builder.Default
    private List<String> moves = new ArrayList<>();
}
