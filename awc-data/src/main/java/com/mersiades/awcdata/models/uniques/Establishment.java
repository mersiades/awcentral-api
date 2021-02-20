package com.mersiades.awcdata.models.uniques;

import com.mersiades.awccontent.models.SecurityOption;
import com.mersiades.awcdata.models.CastCrew;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Establishment {

    @Id
    private String id;

    private String mainAttraction;
    private String bestRegular;
    private String worstRegular;
    private String wantsInOnIt;
    private String oweForIt;
    private String wantsItGone;

    @Builder.Default
    List<String> sideAttractions = new ArrayList<>();

    @Builder.Default
    List<String> atmospheres = new ArrayList<>();

    @Builder.Default
    List<String> regulars = new ArrayList<>();

    @Builder.Default
    List<String> interestedParties = new ArrayList<>();

    @Builder.Default
    List<SecurityOption> securityOptions = new ArrayList<>();

    @Builder.Default
    List<CastCrew> castAndCrew = new ArrayList<>();
}
