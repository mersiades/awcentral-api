package com.mersiades.awccontent.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlusOneForwardConditions {

    @Id
    private String id;

    private Boolean isManualGrant;

    private Boolean onTenPlus;

    private Boolean onSevenToNine;

    private Boolean onMiss;
}
