package com.mersiades.awcdata.models;

import com.mersiades.awccontent.enums.LookCategories;
import com.mersiades.awccontent.enums.Playbooks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.mersiades.awccontent.models.Look;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Character {

    @Id
    private String id;

    private String name;

    private Playbooks playbook;

    private PlaybookUnique playbookUnique;

    private GameRole gameRole;

    private Boolean hasCompletedCharacterCreation;

    private int barter;

    private CharacterHarm harm;

    private StatsBlock statsBlock;

    @Builder.Default
    private List<HxStat> hxBlock = new ArrayList<>();

    @Builder.Default
    private List<String> gear = new ArrayList<>();

    @Builder.Default
    private List<Look> looks = new ArrayList<>();

    @Builder.Default
    private List<CharacterMove> characterMoves = new ArrayList<>();

    public Optional<Look> getLookByCategory(LookCategories category) {
        return this.looks.stream()
                .filter(look -> look.getCategory().equals(category))
                .findFirst();
    }
}
