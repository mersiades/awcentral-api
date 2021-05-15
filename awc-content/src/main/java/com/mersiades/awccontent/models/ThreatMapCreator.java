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
public class ThreatMapCreator {

    @Id
    private String id;

    @Builder.Default
    private List<String> names = new ArrayList<>();

    @Builder.Default
    private List<String> resources = new ArrayList<>();
}
