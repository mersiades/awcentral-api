package com.mersiades.awcdata.models;

import com.mersiades.awccontent.enums.LookType;
import com.mersiades.awccontent.enums.PlaybookType;
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

    private PlaybookType playbook;

    private PlaybookUnique playbookUnique;

    private GameRole gameRole;

    private Boolean hasCompletedCharacterCreation;

    private Boolean hasPlusOneForward;

    private int holds;

    private int barter;

    private CharacterHarm harm;

    private StatsBlock statsBlock;

    private int vehicleCount;

    private int battleVehicleCount;

    // TODO: add battleVehicleCount

    @Builder.Default
    private List<Vehicle> vehicles = new ArrayList<>();

    @Builder.Default
    private List<HxStat> hxBlock = new ArrayList<>();

    @Builder.Default
    private List<String> gear = new ArrayList<>();

    @Builder.Default
    private List<Look> looks = new ArrayList<>();

    @Builder.Default
    private List<CharacterMove> characterMoves = new ArrayList<>();

    public Optional<Look> getLookByCategory(LookType category) {
        return this.looks.stream()
                .filter(look -> look.getCategory().equals(category))
                .findFirst();
    }
}
