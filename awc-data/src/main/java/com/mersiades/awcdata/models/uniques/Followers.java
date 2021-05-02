package com.mersiades.awcdata.models.uniques;

import com.mersiades.awccontent.enums.UniqueType;
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
public class Followers {

    @Id
    private String id;

    @Builder.Default
    private UniqueType uniqueType = UniqueType.FOLLOWERS;

    private String description;

    private String travelOption;

    private String characterization;

    private int followers;

    private int fortune;

    private int barter;

    private int surplusBarter;

    @Builder.Default
    private List<String> surplus = new ArrayList<>();

    @Builder.Default
    private List<String> wants = new ArrayList<>();

    @Builder.Default
    private List<FollowersOption> selectedStrengths = new ArrayList<>();

    @Builder.Default
    private List<FollowersOption> selectedWeaknesses = new ArrayList<>();
}
