package com.mersiades.awcdata.models.uniques;

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

    private String description;

    private String travelOption;

    private int followers;

    private int fortune;

    private int barter;

    private int surplusBarter;

    @Builder.Default
    private List<String> surplus = new ArrayList<>();

    @Builder.Default
    private List<String> wants = new ArrayList<>();
}
