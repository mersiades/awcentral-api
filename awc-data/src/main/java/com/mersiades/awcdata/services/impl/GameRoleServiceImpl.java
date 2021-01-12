package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.*;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.AngelKit;
import com.mersiades.awcdata.models.uniques.BrainerGear;
import com.mersiades.awcdata.models.uniques.CustomWeapons;
import com.mersiades.awcdata.repositories.GameRoleRepository;
import com.mersiades.awcdata.services.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameRoleServiceImpl implements GameRoleService {

    private final GameRoleRepository gameRoleRepository;
    private final CharacterService characterService;
    private final StatsOptionService statsOptionService;
    private final MoveService moveService;
    private final PlaybookCreatorService playbookCreatorService;

    public GameRoleServiceImpl(GameRoleRepository gameRoleRepository, CharacterService characterService,
                               StatsOptionService statsOptionService, MoveService moveService,
                               PlaybookCreatorService playbookCreatorService) {
        this.gameRoleRepository = gameRoleRepository;
        this.characterService = characterService;
        this.statsOptionService = statsOptionService;
        this.moveService = moveService;
        this.playbookCreatorService = playbookCreatorService;
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
        gameRole.getCharacters().forEach(characterService::delete);
        gameRoleRepository.delete(gameRole).log().block();
    }

    @Override
    public void deleteById(String id) {
        gameRoleRepository.deleteById(id);
    }

    @Override
    public Flux<GameRole> findAllByUser(User user) {
        return gameRoleRepository.findAllByUser(user);
    }

    @Override
    public Flux<GameRole> findAllByUserId(String userId) {
        return gameRoleRepository.findAllByUserId(userId).log();
    }

    @Override
    public Character addNewCharacter(String gameRoleId) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        Character newCharacter = Character.builder().hasCompletedCharacterCreation(false).build();
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

        // Reset fields if already filled
        character.setName(null);
        character.getLooks().clear();
        character.setStatsBlock(null);
        character.getGear().clear();
        character.setPlaybookUnique(null);
        character.setCharacterMoves(null);

        // Set new playbook
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

        // Create or update each CharacterStat
        Arrays.asList(Stats.values().clone()).forEach(stat -> {
            if (!stat.equals(Stats.HX)) {
                createOrUpdateCharacterStat(character, statsOption, stat);
            }
        });

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character setCharacterHx(String gameRoleId, String characterId, List<HxStat> hxStats) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        hxStats.forEach(hxStat -> hxStat.setId(UUID.randomUUID().toString()));

        character.setHxBlock(hxStats);

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character setCharacterGear(String gameRoleId, String characterId, List<String> gear) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.setGear(gear);

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character setBrainerGear(String gameRoleId, String characterId, List<String> brainerGear) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        BrainerGear brainerGear1 = BrainerGear.builder()
                .id(UUID.randomUUID().toString())
                .brainerGear(brainerGear)
                .build();

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.BRAINER_GEAR) {
            // make new brainerGear & set
            PlaybookUnique brainerUnique = PlaybookUnique.builder()
                    .id(UUID.randomUUID().toString())
                    .type(UniqueType.BRAINER_GEAR)
                    .brainerGear(brainerGear1)
                    .build();

            character.setPlaybookUnique(brainerUnique);
        } else {
            character.getPlaybookUnique().setBrainerGear(brainerGear1);
        }

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character setAngelKit(String gameRoleId, String characterId, int stock, Boolean hasSupplier) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.ANGEL_KIT) {
            // Make new AngelKit & set
            List<Move> angelKitMoves = moveService
                    .findAllByPlaybookAndKind(Playbooks.ANGEL, MoveKinds.UNIQUE)
                    .collectList().block();

            AngelKit angelKit = AngelKit.builder().id(UUID.randomUUID().toString())
                    .hasSupplier(hasSupplier)
                    .stock(stock).build();

            assert angelKitMoves != null;
            angelKitMoves.forEach(move -> angelKit.getAngelKitMoves().add(move));

            PlaybookUnique angelUnique = PlaybookUnique.builder()
                    .id(UUID.randomUUID().toString())
                    .type(UniqueType.ANGEL_KIT)
                    .angelKit(angelKit)
                    .build();

            character.setPlaybookUnique(angelUnique);
        } else {
            // Update existing AngelKit
            character.getPlaybookUnique().getAngelKit().setStock(stock);
            character.getPlaybookUnique().getAngelKit().setHasSupplier(hasSupplier);
        }

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character setCustomWeapons(String gameRoleId, String characterId, List<String> weapons) {

        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.CUSTOM_WEAPONS) {
            // Create new PlaybookUnique for Battlebabe
            CustomWeapons customWeapons = CustomWeapons.builder().id(UUID.randomUUID().toString())
                    .weapons(weapons)
                    .build();

            PlaybookUnique battlebabeUnique = PlaybookUnique.builder()
                    .id(UUID.randomUUID().toString())
                    .type(UniqueType.CUSTOM_WEAPONS)
                    .customWeapons(customWeapons)
                    .build();

            character.setPlaybookUnique(battlebabeUnique);
        } else {
            // Update existing PlaybookUnique
            character.getPlaybookUnique().getCustomWeapons().setWeapons(weapons);
        }

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character setCharacterMoves(String gameRoleId, String characterId, List<String> moveIds) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        PlaybookCreator playbookCreator = playbookCreatorService.findByPlaybookType(character.getPlaybook()).block();
        assert playbookCreator != null;

        List<CharacterMove> characterMoves = playbookCreator.getPlaybookMoves();

        characterMoves.forEach(characterMove -> {
            if (moveIds.contains(characterMove.getId())) {
                characterMove.setIsSelected(true);
            }
            characterMove.setId(UUID.randomUUID().toString());
        });


        character.setCharacterMoves(characterMoves);

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character finishCharacterCreation(String gameRoleId, String characterId) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.setHasCompletedCharacterCreation(true);

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character setCharacterBarter(String gameRoleId, String characterId, int amount) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.setBarter(amount);

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    private void createOrUpdateCharacterStat(Character character, StatsOption statsOption, Stats stat) {
        int value;
        switch (stat) {
            case COOL:
                value = statsOption.getCOOL();
                break;
            case HARD:
                value = statsOption.getHARD();
                break;
            case HOT:
                value = statsOption.getHOT();
                break;
            case SHARP:
                value = statsOption.getSHARP();
                break;
            case WEIRD:
                value = statsOption.getWEIRD();
                break;
            default:
                value = 0;
                break;
        }

        Optional<CharacterStat> optionalStat = character.getStatsBlock().stream()
                .filter(characterStat -> characterStat.getStat().equals(stat)).findFirst();

        if (optionalStat.isEmpty()) {
            CharacterStat newStat = CharacterStat.builder()
                    .id(UUID.randomUUID().toString())
                    .stat(stat)
                    .value(value)
                    .isHighlighted(false)
                    .build();
            character.getStatsBlock().add(newStat);
        } else {
            optionalStat.get().setValue(value);
        }
    }
}
