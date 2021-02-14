package com.mersiades.awcdata.services.impl;

import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.services.MoveService;
import com.mersiades.awccontent.services.PlaybookCreatorService;
import com.mersiades.awccontent.services.StatModifierService;
import com.mersiades.awccontent.services.StatsOptionService;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.*;
import com.mersiades.awcdata.repositories.GameRoleRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

import static com.mersiades.awccontent.constants.MoveNames.collectorName;
import static com.mersiades.awccontent.constants.MoveNames.otherCarTankName;

@Service
public class GameRoleServiceImpl implements GameRoleService {

    private final GameRoleRepository gameRoleRepository;
    private final CharacterService characterService;
    private final StatsOptionService statsOptionService;
    private final MoveService moveService;
    private final PlaybookCreatorService playbookCreatorService;
    private final StatModifierService statModifierService;

    public GameRoleServiceImpl(GameRoleRepository gameRoleRepository, CharacterService characterService,
                               StatsOptionService statsOptionService, MoveService moveService,
                               PlaybookCreatorService playbookCreatorService, StatModifierService statModifierService) {
        this.gameRoleRepository = gameRoleRepository;
        this.characterService = characterService;
        this.statsOptionService = statsOptionService;
        this.moveService = moveService;
        this.playbookCreatorService = playbookCreatorService;
        this.statModifierService = statModifierService;
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

        CharacterHarm harm = CharacterHarm.builder()
                .id(UUID.randomUUID().toString())
                .hasChangedPlaybook(false)
                .hasComeBackHard(false)
                .hasComeBackWeird(false)
                .hasDied(false)
                .isStabilized(false)
                .value(0)
                .build();

        Character newCharacter = Character.builder()
                .hasCompletedCharacterCreation(false)
                .hasPlusOneForward(false)
                .harm(harm).build();
        characterService.save(newCharacter).block();
        assert gameRole != null;
        gameRole.getCharacters().add(newCharacter);
        gameRoleRepository.save(gameRole).block();
        return newCharacter;
    }

    @Override
    public Character setCharacterPlaybook(String gameRoleId, String characterId, PlaybookType playbookType) {
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
        character.setVehicles(new ArrayList<>());

        // Set default Vehicle and BattleVehicle counts by PlaybookType
        if (List.of(PlaybookType.DRIVER, PlaybookType.CHOPPER).contains(playbookType)) {
            character.setVehicleCount(1);
            character.setBattleVehicleCount(0);
        } else if (playbookType.equals(PlaybookType.HARDHOLDER)) {
            character.setVehicleCount(4);
            character.setBattleVehicleCount(4);
        } else {
            character.setVehicleCount(0);
            character.setBattleVehicleCount(0);
        }

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
    public Character setCharacterLook(String gameRoleId, String characterId, String look, LookType category) {

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

        StatsBlock statsBlock = StatsBlock.builder()
                .id(UUID.randomUUID().toString())
                .statsOptionId(statsOptionId)
                .build();

        character.setStatsBlock(statsBlock);

        // Create or update each CharacterStat in the StatsBlock
        Arrays.asList(StatType.values().clone()).forEach(stat -> {
            if (!stat.equals(StatType.HX)) {
                createOrUpdateCharacterStat(character, statsOption, stat);
            }
        });

        // Adjust CharacterStat if Character has a Move with a StatModifier
        if (character.getCharacterMoves() != null) {
            character.getCharacterMoves().forEach(characterMove -> {
                if (characterMove.getStatModifier() != null) {
                    character.getStatsBlock().getStats().forEach(characterStat -> {
                        if (characterStat.getStat().equals(characterMove.getStatModifier().getStatToModify())) {
                            characterStat.setValue(characterStat.getValue() + characterMove.getStatModifier().getModification());
                            characterStat.setModifier(characterMove.getStatModifier().getId());
                        }
                    });
                }
            });
        }

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
    public Character adjustCharacterHx(String gameRoleId, String characterId, String hxId, int value) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.getHxBlock().forEach(hxStat -> {
            if (hxStat.getCharacterId().equals(hxId)) {
                hxStat.setHxValue(value);
            }
        });

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
                    .findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.UNIQUE)
                    .collectList().block();
            assert angelKitMoves != null;

            AngelKit angelKit = AngelKit.builder().id(UUID.randomUUID().toString())
                    .hasSupplier(hasSupplier)
                    .angelKitMoves(angelKitMoves)
                    .stock(stock).build();

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
    public Character setVehicleCount(String gameRoleId, String characterId, int vehicleCount) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();


        character.setVehicleCount(vehicleCount);

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();
        return character;
    }

    @Override
    public Character setVehicle(String gameRoleId, String characterId, Vehicle vehicle) {
        // If it's a new Vehicle, add id
        if (vehicle.getId() == null) {
            vehicle.setId(UUID.randomUUID().toString());
        }

        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();


        if (character.getVehicles().size() == 0) {
            // Add first Vehicle
            character.getVehicles().add(vehicle);
        } else {
            // Replace Vehicle with updated data, if it already exists
            ListIterator<Vehicle> iterator = character.getVehicles().listIterator();
            boolean hasReplaced = false;
            while (iterator.hasNext()) {
                Vehicle nextVehicle = iterator.next();
                if (nextVehicle.getId().equals(vehicle.getId())) {
                    iterator.set(vehicle);
                    hasReplaced = true;
                }
            }

            if (!hasReplaced) {
                // Add a new Vehicle to the existing Vehicles List
                character.getVehicles().add(vehicle);
            }
        }


        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();
        return character;
    }

    @Override
    public Character setGang(String gameRoleId, String characterId, Gang gang) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (gang.getId() == null) {
            gang.setId(UUID.randomUUID().toString());
        }

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.GANG) {
            PlaybookUnique gangUnique = PlaybookUnique.builder()
                    .id(UUID.randomUUID().toString())
                    .type(UniqueType.GANG)
                    .gang(gang)
                    .build();

            character.setPlaybookUnique(gangUnique);
        } else {
            // Update existing AngelKit
            character.getPlaybookUnique().setGang(gang);
        }

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Mono<GameRole> addThreat(String gameRoleId, Threat threat) {
        return gameRoleRepository.findById(gameRoleId).flatMap(gameRole -> {
            if (threat.getId() == null) {
                threat.setId(UUID.randomUUID().toString());
            }
            if (gameRole.getThreats().size() == 0) {
                gameRole.getThreats().add(threat);

            } else {
                ListIterator<Threat> iterator = gameRole.getThreats().listIterator();
                boolean hasReplaced = false;
                while (iterator.hasNext()) {
                    Threat nextThreat = iterator.next();
                    if (nextThreat.getId().equals(threat.getId())) {
                        iterator.set(threat);
                        hasReplaced = true;
                    }
                }

                if (!hasReplaced) {
                    gameRole.getThreats().add(threat);
                }
            }
            return Mono.just(gameRole);
        }).flatMap(gameRoleRepository::save);
    }

    @Override
    public Mono<GameRole> addNpc(String gameRoleId, Npc npc) {
        return gameRoleRepository.findById(gameRoleId).map(gameRole -> {
            if (npc.getId() == null) {
                npc.setId(UUID.randomUUID().toString());
            }
            if (gameRole.getNpcs().size() == 0) {
                gameRole.getNpcs().add(npc);

            } else {
                ListIterator<Npc> iterator = gameRole.getNpcs().listIterator();
                boolean hasReplaced = false;
                while (iterator.hasNext()) {
                    Npc nextNpc = iterator.next();
                    if (nextNpc.getId().equals(npc.getId())) {
                        iterator.set(npc);
                        hasReplaced = true;
                    }
                }

                if (!hasReplaced) {
                    gameRole.getNpcs().add(npc);
                }
            }
            return gameRole;
        }).flatMap(gameRoleRepository::save);
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
    public Character setHolding(String gameRoleId, String characterId, Holding holding, int vehicleCount, int battleVehicleCount) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (holding.getId() == null) {
            holding.setId(UUID.randomUUID().toString());
        }

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.HOLDING) {

            PlaybookUnique playbookUniqueHardHolder = PlaybookUnique.builder()
                    .id(UUID.randomUUID().toString())
                    .type(UniqueType.HOLDING)
                    .holding(holding)
                    .build();

            character.setPlaybookUnique(playbookUniqueHardHolder);
        } else {
            // Update existing PlaybookUnique
            character.getPlaybookUnique().setHolding(holding);
        }
        character.setVehicleCount(vehicleCount);
        character.setBattleVehicleCount(battleVehicleCount);

        // Remove any extra vehicles
        if (character.getVehicles().size() > vehicleCount) {
            character.setVehicles(character.getVehicles().subList(0, vehicleCount));
        }

        if (character.getBattleVehicles().size() > battleVehicleCount) {
            character.setBattleVehicles(character.getBattleVehicles().subList(0, battleVehicleCount));
        }

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character setFollowers(String gameRoleId, String characterId, Followers followers) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (followers.getId() == null) {
            followers.setId(UUID.randomUUID().toString());
        }

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.FOLLOWERS) {

            PlaybookUnique playbookUniqueHardHolder = PlaybookUnique.builder()
                    .id(UUID.randomUUID().toString())
                    .type(UniqueType.FOLLOWERS)
                    .followers(followers)
                    .build();

            character.setPlaybookUnique(playbookUniqueHardHolder);
        } else {
            // Update existing PlaybookUnique
            character.getPlaybookUnique().setFollowers(followers);
        }

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character setWeapons(String gameRoleId, String characterId, List<String> weapons) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.WEAPONS) {
            // Create new PlaybookUnique for Battlebabe
            Weapons newWeapons = Weapons.builder()
                    .id(UUID.randomUUID().toString())
                    .weapons(weapons)
                    .build();

            PlaybookUnique gunluggerUnique = PlaybookUnique.builder()
                    .id(UUID.randomUUID().toString())
                    .type(UniqueType.WEAPONS)
                    .weapons(newWeapons)
                    .build();

            character.setPlaybookUnique(gunluggerUnique);
        } else {
            // Update existing PlaybookUnique
            character.getPlaybookUnique().getWeapons().setWeapons(weapons);
        }

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character setSkinnerGear(String gameRoleId, String characterId, SkinnerGear skinnerGear) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        if (skinnerGear.getId() == null) {
            skinnerGear.setId(UUID.randomUUID().toString());
        }

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.SKINNER_GEAR) {
            // Create new PlaybookUnique for Battlebabe
            PlaybookUnique playbookUniqueSkinner = PlaybookUnique.builder()
                    .id(UUID.randomUUID().toString())
                    .type(UniqueType.SKINNER_GEAR)
                    .skinnerGear(skinnerGear)
                    .build();

            character.setPlaybookUnique(playbookUniqueSkinner);
        } else {
            // Update existing PlaybookUnique
            character.getPlaybookUnique().setSkinnerGear(skinnerGear);
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

        List<String> previousCharacterMoveNames = character.getCharacterMoves()
                .stream().map(Move::getName).collect(Collectors.toList());

        PlaybookCreator playbookCreator = playbookCreatorService.findByPlaybookType(character.getPlaybook()).block();
        assert playbookCreator != null;

        List<Move> playbookMoves = playbookCreator.getOptionalMoves()
                .stream().filter(characterMove -> moveIds.contains(characterMove.getId())).collect(Collectors.toList());

        List<Move> playbookDefaultMoves = playbookCreator.getDefaultMoves();

        playbookMoves.addAll(playbookDefaultMoves);

        List<CharacterMove> characterMoves = playbookMoves.stream()
                .map(move -> CharacterMove.createFromMove(move, true))
                .collect(Collectors.toList());

        // Preemptively remove moved-based stat modifications
        character.getStatsBlock().getStats().forEach(characterStat -> {
            if (characterStat.getModifier() != null) {
                StatModifier statModifier = statModifierService.findById(characterStat.getModifier()).block();
                assert statModifier != null;
                characterStat.setValue(characterStat.getValue() - statModifier.getModification());
                characterStat.setModifier(null);
            }
        });

        // Flesh out each CharacterMove
        characterMoves.forEach(characterMove -> {
            // Mark the CharacterMoves as selected
//            characterMove.setIsSelected(true);
            // Adjust CharacterStat if CharacterMove has a StatModifier
            if (characterMove.getStatModifier() != null) {
                CharacterStat statToBeModified = character.getStatsBlock().getStats()
                        .stream()
                        .filter(characterStat -> characterStat.getStat().equals(characterMove.getStatModifier().getStatToModify()))
                        .findFirst().orElseThrow();

                statToBeModified.setValue(statToBeModified.getValue() + characterMove.getStatModifier().getModification());
                statToBeModified.setModifier(characterMove.getStatModifier().getId());

            }
            // Give CharacterMove an id
            characterMove.setId(UUID.randomUUID().toString());
        });

        List<String> newCharacterMoveNames = characterMoves
                .stream().map(Move::getName).collect(Collectors.toList());

        // Adjust vehicleCount and battleVehicleCount move for Driver
        if (character.getPlaybook().equals(PlaybookType.DRIVER)) {
            if (newCharacterMoveNames.contains(collectorName) && !previousCharacterMoveNames.contains(collectorName)) {
                character.setVehicleCount(character.getVehicleCount() + 2);
            } else if (!newCharacterMoveNames.contains(collectorName) && previousCharacterMoveNames.contains(collectorName)) {
                int newCount = character.getVehicleCount() - 2;
                character.setVehicleCount(newCount);
                character.setVehicles(character.getVehicles().subList(0, newCount));
            }

            if (newCharacterMoveNames.contains(otherCarTankName) && !previousCharacterMoveNames.contains(otherCarTankName)) {
                character.setBattleVehicleCount(character.getBattleVehicleCount() + 1);
            } else if (!newCharacterMoveNames.contains(otherCarTankName) && previousCharacterMoveNames.contains(otherCarTankName)) {
                int newCount = character.getBattleVehicleCount() - 1;
                character.setBattleVehicleCount(newCount);
                // TODO: uncomment this after battleVehicles field has been added
//                character.setBattleVehicles(character.getBattleVehicles.subList(0, newCount));
            }
        }



        // This will also overwrite an existing set of CharacterMoves
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

    @Override
    public Character setHoldingBarter(String gameRoleId, String characterId, int amount) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.getPlaybookUnique().getHolding().setBarter(amount);

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character updateFollowers(String gameRoleId, String characterId, int barter, int followers, String description) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.getPlaybookUnique().getFollowers().setBarter(barter);
        character.getPlaybookUnique().getFollowers().setFollowers(followers);
        character.getPlaybookUnique().getFollowers().setDescription(description);

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character setCharacterHarm(String gameRoleId, String characterId, CharacterHarm harm) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.setHarm(harm);

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    @Override
    public Character toggleStatHighlight(String gameRoleId, String characterId, StatType stat) {
        // Get the GameRole
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.getStatsBlock().getStats().forEach(characterStat -> {
            if (characterStat.getStat().equals(stat)) {
                characterStat.setIsHighlighted(!characterStat.getIsHighlighted());
            }
        });

        // Save to db
        characterService.save(character).block();
        gameRoleRepository.save(gameRole).block();

        return character;
    }

    private void createOrUpdateCharacterStat(Character character, StatsOption statsOption, StatType stat) {
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

        Optional<CharacterStat> optionalStat = character.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(stat)).findFirst();

        if (optionalStat.isEmpty()) {
            CharacterStat newStat = CharacterStat.builder()
                    .id(UUID.randomUUID().toString())
                    .stat(stat)
                    .value(value)
                    .isHighlighted(false)
                    .build();
            character.getStatsBlock().getStats().add(newStat);
        } else {
            optionalStat.get().setValue(value);
        }
    }
}
