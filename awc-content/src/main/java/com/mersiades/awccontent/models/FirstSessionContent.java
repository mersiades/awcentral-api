package com.mersiades.awccontent.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirstSessionContent {

    @Id
    private String id;

    private String intro;

    private ContentItem duringCharacterCreation;

    private TickerList duringFirstSession;

    private ContentItem threatMapInstructions;

    private TickerList afterFirstSession;
}
