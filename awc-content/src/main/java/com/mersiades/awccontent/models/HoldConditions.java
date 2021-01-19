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
public class HoldConditions {

    @Id
    private String id;

    private int onTenPlus;

    private int onSevenToNine;

    private int onMiss;
}
