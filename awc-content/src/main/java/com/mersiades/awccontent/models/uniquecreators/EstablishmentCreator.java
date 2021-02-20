package com.mersiades.awccontent.models.uniquecreators;

import com.mersiades.awccontent.models.SecurityOption;
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
public class EstablishmentCreator {

    @Id
    private String id;

    private int mainAttractionCount;

    private int sideAttractionCount;

    @Builder.Default
    private List<String> attractions = new ArrayList<>();

    @Builder.Default
    private List<String> atmospheres = new ArrayList<>();

    @Builder.Default
    private List<Integer> atmosphereCount = new ArrayList<>();

    @Builder.Default
    private List<String> regularsNames = new ArrayList<>();

    @Builder.Default
    private List<String> regularsQuestions = new ArrayList<>();

    @Builder.Default
    private List<String> interestedPartyNames = new ArrayList<>();

    @Builder.Default
    private List<String> interestedPartyQuestions = new ArrayList<>();

    @Builder.Default
    private List<SecurityOption> securityOptions = new ArrayList<>();

}
