package com.mersiades.awcdata.models;

import com.mersiades.awccontent.enums.LookType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Look;
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

    private PlaybookType playbook;

    private PlaybookUniques playbookUniques;

    private GameRole gameRole;

    private Boolean hasCompletedCharacterCreation;

    private Boolean hasPlusOneForward;

    private int barter;

    private CharacterHarm harm;

    private StatsBlock statsBlock;

    private int vehicleCount;

    private int battleVehicleCount;



//    @Size(max = 5, message = "Experience must be between 0 and 5")
//    @NotNull
    private int experience;

    // Min 0, max 16
    private int allowedImprovements;

    // The number of playbook moves the Character can have
    // Does not include default playbook moves
    // Usually equal to PlaybookCreator.getMoveChoiceCount(),
    // but can be increased by ADD_CHARACTER_MOVE improvements
    private int allowedPlaybookMoves;

    // The number of moves the character can have from other playbooks.
    // Usually 0, but can be increased by ADD_OTHER_PB_MOVE improvements
    private int allowedOtherPlaybookMoves;

    @Builder.Default
    private Boolean isDead = false;

    @Builder.Default
    private Boolean isRetired = false;

    @Builder.Default
    private Boolean mustChangePlaybook = false;

    @Builder.Default
    private List<BattleVehicle> battleVehicles = new ArrayList<>();

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

    @Builder.Default
    private List<CharacterMove> improvementMoves = new ArrayList<>();

    @Builder.Default
    private List<CharacterMove> futureImprovementMoves = new ArrayList<>();

    @Builder.Default
    private List<CharacterMove> deathMoves = new ArrayList<>();

    // The names of the basic Moves the player has chosen to advance.
    @Builder.Default
    private List<String> advancedBasicMoves = new ArrayList<>();

    @Builder.Default
    private List<Hold> holds = new ArrayList<>();

    public Optional<Look> getLookByCategory(LookType category) {
        return this.looks.stream()
                .filter(look -> look.getCategory().equals(category))
                .findFirst();
    }
}
