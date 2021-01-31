package com.mersiades.awccontent.models.uniquecreators;

import com.mersiades.awccontent.enums.GangSize;
import com.mersiades.awccontent.models.GangOption;
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
public class GangCreator {

    @Id
    private String id;

    private String intro;

    private GangSize defaultSize;

    private int defaultHarm;

    private int defaultArmor;

    private int strengthChoiceCount;

    private int weaknessChoiceCount;

    @Builder.Default
    private List<String> defaultTags = new ArrayList<>();

    @Builder.Default
    private List<GangOption> strengths = new ArrayList<>();

    @Builder.Default
    private List<GangOption> weaknesses = new ArrayList<>();
}
