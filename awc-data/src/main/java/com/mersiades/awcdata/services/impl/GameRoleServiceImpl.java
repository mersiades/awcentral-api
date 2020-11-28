package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.LookCategories;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.enums.Stats;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.repositories.GameRoleRepository;
import com.mersiades.awcdata.services.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Service
public class GameRoleServiceImpl implements GameRoleService {

    private final GameRoleRepository gameRoleRepository;
    private final CharacterService characterService;
    private final StatsOptionService statsOptionService;

    public GameRoleServiceImpl(GameRoleRepository gameRoleRepository, CharacterService characterService,
                                StatsOptionService statsOptionService) {
        this.gameRoleRepository = gameRoleRepository;
        this.characterService = characterService;
        this.statsOptionService = statsOptionService;

    }

    @Override
    public Flux<GameRole> findAll() {
        return gameRoleRepository.findAll();
    }

    @Override
    public Mono<GameRole> findById(String id) {
        return gameRoleRepository.findById(id);
    }

    @Override
    public Mono<GameRole> save(GameRole gameRole) {
        return gameRoleRepository.save(gameRole);
    }

    @Override
    public Flux<GameRole> saveAll(Flux<GameRole> gameRoles) {
        return gameRoleRepository.saveAll(gameRoles);
    }

    @Override
    public void delete(GameRole gameRole) {
        System.out.println("gameRole in gamerRoleService.delete= " + gameRole);
        gameRoleRepository.delete(gameRole);
    }

    @Override
    public void deleteById(String id) {
        gameRoleRepository.deleteById(id);
    }

    @Override
    public Flux<GameRole> findAllByUser(User user) {
        System.out.println("user = " + user);
        if (user.getGameRoles() == null) {
            System.out.println("gameRoles: null");
        } else {
            System.out.println("gameRoles: " + user.getGameRoles().toString());
        }
        return gameRoleRepository.findAllByUser(user);
    }

    @Override
    public Character addNewCharacter(String gameRoleId) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        Character newCharacter = new Character();
        System.out.println("newCharacter = " + newCharacter);
        characterService.save(newCharacter).block();
        assert gameRole != null;
        gameRole.getCharacters().add(newCharacter);
        gameRoleRepository.save(gameRole).block();
        return newCharacter;
    }

    @Override
    public Character setCharacterPlaybook(String gameRoleId, String characterId, Playbooks playbookType) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();
        character.setPlaybook(playbookType);
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();
        return character;
    }

    @Override
    public Character setCharacterName(String gameRoleId, String characterId, String name) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();
        character.setName(name);
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();
        return character;
    }

    @Override
    public Character setCharacterLook(String gameRoleId, String characterId, String look, LookCategories category) {

        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        // Check to see if the character already has a Look with the given category
        if (character.getLookByCategory(category).isEmpty()) {
            // Create new Look
            Look newLook = Look.builder().id(UUID.randomUUID().toString()).look(look).category(category).build();
            character.getLooks().add(newLook);
        } else {
            // Update existing Look
            Look lookToUpdate = character.getLookByCategory(category).get();
            lookToUpdate.setLook(look);
        }

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character setCharacterStats(String gameRoleId, String characterId, String statsOptionId) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        // Get statsOption from db

        StatsOption statsOption = statsOptionService.findById(statsOptionId).block();
            assert statsOption != null;


        // Create or update COOL CharacterStat
        Optional<CharacterStat> optionalCool = character.getStatsBlock().getCharacterStatbyStat(Stats.COOL);
        if (optionalCool.isEmpty()) {
            CharacterStat newCool = CharacterStat.builder()
                    .id(UUID.randomUUID().toString())
                    .stat(Stats.COOL)
                    .value(statsOption.getCOOL())
                    .isHighlighted(false)
                    .build();
            character.getStatsBlock().getStats().add(newCool);
        } else {
           optionalCool.get().setValue(statsOption.getCOOL());
        }

        // Create or update HARD CharacterStat
        Optional<CharacterStat> optionalHard = character.getStatsBlock().getCharacterStatbyStat(Stats.HARD);
        if (optionalHard.isEmpty()) {
            CharacterStat newHard = CharacterStat.builder()
                    .id(UUID.randomUUID().toString())
                    .stat(Stats.COOL)
                    .value(statsOption.getHARD())
                    .isHighlighted(false)
                    .build();
            character.getStatsBlock().getStats().add(newHard);
        } else {
            optionalHard.get().setValue(statsOption.getHARD());
        }

        // Create or update HOT CharacterStat
        Optional<CharacterStat> optionalHot = character.getStatsBlock().getCharacterStatbyStat(Stats.HOT);
        if (optionalHot.isEmpty()) {
            CharacterStat newHot = CharacterStat.builder()
                    .id(UUID.randomUUID().toString())
                    .stat(Stats.HOT)
                    .value(statsOption.getHOT())
                    .isHighlighted(false)
                    .build();
            character.getStatsBlock().getStats().add(newHot);
        } else {
            optionalHot.get().setValue(statsOption.getHOT());
        }

        // Create or update SHARP CharacterStat
        Optional<CharacterStat> optionalSharp = character.getStatsBlock().getCharacterStatbyStat(Stats.SHARP);
        if (optionalSharp.isEmpty()) {
            CharacterStat newSharp = CharacterStat.builder()
                    .id(UUID.randomUUID().toString())
                    .stat(Stats.SHARP)
                    .value(statsOption.getSHARP())
                    .isHighlighted(false)
                    .build();
            character.getStatsBlock().getStats().add(newSharp);
        } else {
            optionalSharp.get().setValue(statsOption.getSHARP());
        }

        // Create or update WEIRD CharacterStat
        Optional<CharacterStat> optionalWeird = character.getStatsBlock().getCharacterStatbyStat(Stats.WEIRD);
        if (optionalWeird.isEmpty()) {
            CharacterStat newWeird = CharacterStat.builder()
                    .id(UUID.randomUUID().toString())
                    .stat(Stats.WEIRD)
                    .value(statsOption.getWEIRD())
                    .isHighlighted(false)
                    .build();
            character.getStatsBlock().getStats().add(newWeird);
        } else {
            optionalWeird.get().setValue(statsOption.getWEIRD());
        }


        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return null;
    }
}
