package com.mersiades.awccontent.models.uniquecreators;

import com.mersiades.awccontent.models.FollowersOption;
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
public class FollowersCreator {

    @Id
    private String id;

    private String instructions;

    private int defaultNumberOfFollowers;

    private int defaultSurplusBarter;

    private int strengthCount;

    private int weaknessCount;

    @Builder.Default
    private List<String> travelOptions = new ArrayList<>();

    @Builder.Default
    private List<String> characterizationOptions = new ArrayList<>();

    @Builder.Default
    private List<String> defaultWants = new ArrayList<>();

    @Builder.Default
    private List<FollowersOption> strengthOptions = new ArrayList<>();

    @Builder.Default
    private List<FollowersOption> weaknessOptions = new ArrayList<>();
}
