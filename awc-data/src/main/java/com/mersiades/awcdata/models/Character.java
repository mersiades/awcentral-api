package com.mersiades.awcdata.models;

import com.mersiades.awcdata.enums.LookCategories;
import com.mersiades.awcdata.enums.Playbooks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Document
public class Character {

    @Id
    private String id;

    private String name;

    private StatsBlock statsBlock = new StatsBlock();

    // TODO: add harm

    private Playbooks playbook;

    private List<String> gear = new ArrayList<>();

    private List<Look> looks = new ArrayList<>();

    private GameRole gameRole;

    public Character() {
        this.id = UUID.randomUUID().toString();
    }

    public Character(String id, String name, GameRole gameRole, Playbooks playbook) {
        this.id = id;
        this.name = name;
        this.gameRole = gameRole;
        this.playbook = playbook;
    }

    public Optional<Look> getLookByCategory(LookCategories category) {
        return this.looks.stream()
                .filter(look -> look.getCategory().equals(category))
                .findFirst();
    }
}
