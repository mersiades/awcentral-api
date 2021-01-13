package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.LookCategories;
import com.mersiades.awcdata.enums.Playbooks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Builder.Default
    private List<CharacterStat> statsBlock = new ArrayList<>();

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
