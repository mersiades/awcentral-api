package com.mersiades.awcdata.services.impl;

import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.enums.StatType;
import com.mersiades.awccontent.enums.UniqueType;
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
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
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
    public List<GameRole> findAll() {
        return gameRoleRepository.findAll();
    }

    @Override
    public GameRole findById(String id) {
        return gameRoleRepository.findById(id).orElseThrow();
    }

    @Override
    public GameRole save(GameRole gameRole) {
        return gameRoleRepository.save(gameRole);
    }

    @Override
    public List<GameRole> saveAll(List<GameRole> gameRoles) {
        return gameRoleRepository.saveAll(gameRoles);
    }

    @Override
    public void delete(GameRole gameRole) {
        gameRole.getCharacters().forEach(characterService::delete);
        gameRoleRepository.delete(gameRole);
    }

    @Override
    public void deleteById(String id) {
        gameRoleRepository.deleteById(id);
    }

    // ---------------------------------------------- Game-related -------------------------------------------- //

    @Override
    public List<GameRole> findAllByUserId(String userId) {
        return gameRoleRepository.findAllByUserId(userId);
    }

    // ---------------------------------------------- MC stuff -------------------------------------------- //

    @Override
    public GameRole addThreat(String gameRoleId, Threat threat) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow();

        if (threat.getId() == null) {
            threat.setId(new ObjectId().toString());
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

        return gameRoleRepository.save(gameRole);
    }

    @Override
    public GameRole addNpc(String gameRoleId, Npc npc) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);

        if (npc.getId() == null) {
            npc.setId(new ObjectId().toString());
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

        return gameRoleRepository.save(gameRole);
    }

    // ------------------------------------ Creating and editing characters ---------------------------------- //

    @Override
    public Character addNewCharacter(String gameRoleId) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);

        CharacterHarm harm = CharacterHarm.builder()
                .id(new ObjectId().toString())
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
                .barter(-1)
                .experience(0)
                .harm(harm).build();
        characterService.save(newCharacter);
        assert gameRole != null;
        gameRole.getCharacters().add(newCharacter);
        gameRoleRepository.save(gameRole);
        return newCharacter;
    }

    @Override
    public Character setCharacterPlaybook(String gameRoleId, String characterId, PlaybookType playbookType) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
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
        character.setBattleVehicles(new ArrayList<>());
        character.setHasCompletedCharacterCreation(false);
        character.setExperience(0);

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
        characterService.save(character);
        gameRoleRepository.save(gameRole);
        return character;
    }

    @Override
    public Character setCharacterName(String gameRoleId, String characterId, String name) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();
        character.setName(name);
        characterService.save(character);
        gameRoleRepository.save(gameRole);
        return character;
    }

    @Override
    public Character setCharacterLook(String gameRoleId, String characterId, Look look) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;
        Character character = getCharacterById(gameRole, characterId);

        if (look.getId() == null) {
            look.setId(new ObjectId().toString());
        }

        // Check to see if the character already has a Look with the given category
        if (character.getLookByCategory(look.getCategory()).isEmpty()) {
            character.getLooks().add(look);
        } else {
            // Replace old Look if Look with matching category already exists
            ListIterator<Look> iterator = character.getLooks().listIterator();
            boolean hasReplaced = false;
            while (iterator.hasNext()) {
                Look nextLook = iterator.next();
                if (nextLook.getCategory().equals(look.getCategory())) {
                    iterator.set(look);
                    hasReplaced = true;
                }
            }

            if (!hasReplaced) {
                character.getLooks().add(look);
            }
            // Update existing Look
            Look lookToUpdate = character.getLookByCategory(look.getCategory()).get();
            lookToUpdate.setLook(look.getLook());
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setCharacterStats(String gameRoleId, String characterId, String statsOptionId) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        // Get statsOption from db
        StatsOption statsOption = statsOptionService.findById(statsOptionId);
        assert statsOption != null;

        StatsBlock statsBlock = StatsBlock.builder()
                .id(new ObjectId().toString())
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
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setCharacterGear(String gameRoleId, String characterId, List<String> gear) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.setGear(gear);

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setCharacterMoves(String gameRoleId, String characterId, List<String> moveIds) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        List<String> previousCharacterMoveNames = character.getCharacterMoves()
                .stream().map(Move::getName).collect(Collectors.toList());

        PlaybookCreator playbookCreator = playbookCreatorService.findByPlaybookType(character.getPlaybook());
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
                StatModifier statModifier = statModifierService.findById(characterStat.getModifier());
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
                modifyCharacterStat(character, characterMove);
            }
            // Give CharacterMove an id
            characterMove.setId(new ObjectId().toString());
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
                character.setBattleVehicles(character.getBattleVehicles().subList(0, newCount));
            }
        }


        // This will also overwrite an existing set of CharacterMoves
        character.setCharacterMoves(characterMoves);

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setCharacterHx(String gameRoleId, String characterId, List<HxStat> hxStats) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        hxStats.forEach(hxStat -> hxStat.setId(new ObjectId().toString()));

        character.setHxBlock(hxStats);

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character finishCharacterCreation(String gameRoleId, String characterId) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.setHasCompletedCharacterCreation(true);

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }


    // --------------------------------------- Setting Playbook Uniques ------------------------------------- //

    @Override
    public Character setAngelKit(String gameRoleId, String characterId, int stock, Boolean hasSupplier) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.ANGEL_KIT) {
            // Make new AngelKit & set
            List<Move> angelKitMoves = moveService
                    .findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.UNIQUE);
            assert angelKitMoves != null;

            AngelKit angelKit = AngelKit.builder().id(new ObjectId().toString())
                    .hasSupplier(hasSupplier)
                    .angelKitMoves(angelKitMoves)
                    .stock(stock).build();

            PlaybookUnique angelUnique = PlaybookUnique.builder()
                    .id(new ObjectId().toString())
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
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setBrainerGear(String gameRoleId, String characterId, List<String> brainerGear) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        BrainerGear brainerGear1 = BrainerGear.builder()
                .id(new ObjectId().toString())
                .brainerGear(brainerGear)
                .build();

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.BRAINER_GEAR) {
            // make new brainerGear & set
            PlaybookUnique brainerUnique = PlaybookUnique.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.BRAINER_GEAR)
                    .brainerGear(brainerGear1)
                    .build();

            character.setPlaybookUnique(brainerUnique);
        } else {
            character.getPlaybookUnique().setBrainerGear(brainerGear1);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setCustomWeapons(String gameRoleId, String characterId, List<String> weapons) {


        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.CUSTOM_WEAPONS) {
            // Create new PlaybookUnique for Battlebabe
            CustomWeapons customWeapons = CustomWeapons.builder().id(new ObjectId().toString())
                    .weapons(weapons)
                    .build();

            PlaybookUnique battlebabeUnique = PlaybookUnique.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.CUSTOM_WEAPONS)
                    .customWeapons(customWeapons)
                    .build();

            character.setPlaybookUnique(battlebabeUnique);
        } else {
            // Update existing PlaybookUnique
            character.getPlaybookUnique().getCustomWeapons().setWeapons(weapons);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setEstablishment(String gameRoleId, String characterId, Establishment establishment) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (establishment.getId() == null) {
            establishment.setId(new ObjectId().toString());
        }

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.ESTABLISHMENT) {
            PlaybookUnique gangUnique = PlaybookUnique.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.ESTABLISHMENT)
                    .establishment(establishment)
                    .build();

            character.setPlaybookUnique(gangUnique);
        } else {
            // Update existing Establishment
            character.getPlaybookUnique().setEstablishment(establishment);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setFollowers(String gameRoleId, String characterId, Followers followers) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (followers.getId() == null) {
            followers.setId(new ObjectId().toString());
        }

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.FOLLOWERS) {

            PlaybookUnique playbookUniqueHardHolder = PlaybookUnique.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.FOLLOWERS)
                    .followers(followers)
                    .build();

            character.setPlaybookUnique(playbookUniqueHardHolder);
        } else {
            // Update existing PlaybookUnique
            character.getPlaybookUnique().setFollowers(followers);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setGang(String gameRoleId, String characterId, Gang gang) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (gang.getId() == null) {
            gang.setId(new ObjectId().toString());
        }

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.GANG) {
            PlaybookUnique gangUnique = PlaybookUnique.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.GANG)
                    .gang(gang)
                    .build();

            character.setPlaybookUnique(gangUnique);
        } else {
            // Update existing AngelKit
            character.getPlaybookUnique().setGang(gang);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setHolding(String gameRoleId, String characterId, Holding holding, int vehicleCount, int battleVehicleCount) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (holding.getId() == null) {
            holding.setId(new ObjectId().toString());
        }

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.HOLDING) {

            PlaybookUnique playbookUniqueHardHolder = PlaybookUnique.builder()
                    .id(new ObjectId().toString())
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
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setSkinnerGear(String gameRoleId, String characterId, SkinnerGear skinnerGear) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        if (skinnerGear.getId() == null) {
            skinnerGear.setId(new ObjectId().toString());
        }

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.SKINNER_GEAR) {
            // Create new PlaybookUnique for Battlebabe
            PlaybookUnique playbookUniqueSkinner = PlaybookUnique.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.SKINNER_GEAR)
                    .skinnerGear(skinnerGear)
                    .build();

            character.setPlaybookUnique(playbookUniqueSkinner);
        } else {
            // Update existing PlaybookUnique
            character.getPlaybookUnique().setSkinnerGear(skinnerGear);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setWeapons(String gameRoleId, String characterId, List<String> weapons) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.WEAPONS) {
            // Create new PlaybookUnique for Battlebabe
            Weapons newWeapons = Weapons.builder()
                    .id(new ObjectId().toString())
                    .weapons(weapons)
                    .build();

            PlaybookUnique gunluggerUnique = PlaybookUnique.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.WEAPONS)
                    .weapons(newWeapons)
                    .build();

            character.setPlaybookUnique(gunluggerUnique);
        } else {
            // Update existing PlaybookUnique
            character.getPlaybookUnique().getWeapons().setWeapons(weapons);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setWorkspace(String gameRoleId, String characterId, Workspace workspace) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (workspace.getId() == null) {
            workspace.setId(new ObjectId().toString());
        }

        if (character.getPlaybookUnique() == null || character.getPlaybookUnique().getType() != UniqueType.WORKSPACE) {
            PlaybookUnique gangUnique = PlaybookUnique.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.WORKSPACE)
                    .workspace(workspace)
                    .build();

            character.setPlaybookUnique(gangUnique);
        } else {
            // Update existing Establishment
            character.getPlaybookUnique().setWorkspace(workspace);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    // ------------------------------------------ Setting Vehicles ---------------------------------------- //

    @Override
    public Character setVehicleCount(String gameRoleId, String characterId, int vehicleCount) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();


        character.setVehicleCount(vehicleCount);

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);
        return character;
    }

    @Override
    public Character setBattleVehicleCount(String gameRoleId, String characterId, int battleVehicleCount) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();


        character.setBattleVehicleCount(battleVehicleCount);

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);
        return character;
    }

    @Override
    public Character setVehicle(String gameRoleId, String characterId, Vehicle vehicle) {
        // If it's a new Vehicle, add id
        if (vehicle.getId() == null) {
            vehicle.setId(new ObjectId().toString());
        }


        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
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
        characterService.save(character);
        gameRoleRepository.save(gameRole);
        return character;
    }

    @Override
    public Character setBattleVehicle(String gameRoleId, String characterId, BattleVehicle battleVehicle) {
        // If it's a new BattleVehicle, add id
        if (battleVehicle.getId() == null) {
            battleVehicle.setId(new ObjectId().toString());
        }


        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();


        if (character.getVehicles().size() == 0) {
            // Add first Vehicle
            character.getBattleVehicles().add(battleVehicle);
        } else {
            // Replace Vehicle with updated data, if it already exists
            ListIterator<BattleVehicle> iterator = character.getBattleVehicles().listIterator();
            boolean hasReplaced = false;
            while (iterator.hasNext()) {
                BattleVehicle nextVehicle = iterator.next();
                if (nextVehicle.getId().equals(battleVehicle.getId())) {
                    iterator.set(battleVehicle);
                    hasReplaced = true;
                }
            }

            if (!hasReplaced) {
                // Add a new Vehicle to the existing Vehicles List
                character.getBattleVehicles().add(battleVehicle);
            }
        }


        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);
        return character;
    }

    // ------------------------------------- Adjusting from PlaybookPanel ----------------------------------- //

    @Override
    public Character adjustCharacterHx(String gameRoleId, String characterId, HxStat hxStat) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = getCharacterById(gameRole, characterId);

        if (hxStat.getId() == null) {
            hxStat.setId(new ObjectId().toString());
        }

        // Character improvement: increase experience if hxValue crosses threshold, rest hxValue
        if (hxStat.getHxValue() >= 4) {
            hxStat.setHxValue(1);
            character.setExperience(character.getExperience() + 1);
        }

        if (hxStat.getHxValue() <= -3) {
            hxStat.setHxValue(0);
            character.setExperience(character.getExperience() + 1);
        }

        Optional<HxStat> existingHxStatOptional = character.getHxBlock()
                .stream().filter(hxStat1 -> hxStat1.getId().equals(hxStat.getId())).findFirst();

        if (existingHxStatOptional.isEmpty()) {
            // Add new HxStat

            character.getHxBlock().add(hxStat);
        } else {
            // Replace HdxStat with updated data, if it already exists
            ListIterator<HxStat> iterator = character.getHxBlock().listIterator();
            boolean hasReplaced = false;
            while (iterator.hasNext()) {
                HxStat nextHxStat = iterator.next();
                if (nextHxStat.getId().equals(hxStat.getId())) {
                    iterator.set(hxStat);
                    hasReplaced = true;
                }
            }

            if (!hasReplaced) {
                // Add a new HxStat to the existing HxBlock List (just in case)
                character.getHxBlock().add(hxStat);
            }
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setCharacterHarm(String gameRoleId, String characterId, CharacterHarm harm) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.setHarm(harm);

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character toggleStatHighlight(String gameRoleId, String characterId, StatType stat) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
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
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setCharacterBarter(String gameRoleId, String characterId, int amount) {


        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();
        if (amount < 0) {
            return character;
        }

        character.setBarter(amount);

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setHoldingBarter(String gameRoleId, String characterId, int amount) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.getPlaybookUnique().getHolding().setBarter(amount);

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character updateFollowers(String gameRoleId, String characterId, int barter, int followers, String description) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        character.getPlaybookUnique().getFollowers().setBarter(barter);
        character.getPlaybookUnique().getFollowers().setFollowers(followers);
        character.getPlaybookUnique().getFollowers().setDescription(description);

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character addProject(String gameRoleId, String characterId, Project project) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        if (project.getId() == null) {
            project.setId(new ObjectId().toString());
        }

        if (character.getPlaybookUnique().getWorkspace().getProjects().size() == 0) {
            // Add first Project
            character.getPlaybookUnique().getWorkspace().getProjects().add(project);
        } else {
            // Replace Project with updated data, if it already exists
            ListIterator<Project> iterator = character.getPlaybookUnique().getWorkspace().getProjects().listIterator();
            boolean hasReplaced = false;
            while (iterator.hasNext()) {
                Project nextProject = iterator.next();
                if (nextProject.getId().equals(project.getId())) {
                    iterator.set(project);
                    hasReplaced = true;
                }
            }

            if (!hasReplaced) {
                // Add a new Project to the existing Projects List
                character.getPlaybookUnique().getWorkspace().getProjects().add(project);
            }
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character removeProject(String gameRoleId, String characterId, Project project) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;

        // GameRoles can have multiple characters, so get the right character
        Character character = gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();

        List<Project> filteredProjects = character.getPlaybookUnique().getWorkspace().getProjects().stream()
                .filter(project1 -> !project1.getId().equals(project.getId())).collect(Collectors.toList());

        character.getPlaybookUnique().getWorkspace().setProjects(filteredProjects);

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character removeHold(String gameRoleId, String characterId, Hold hold) {

        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
        assert gameRole != null;
        Character character = getCharacterById(gameRole, characterId);

        List<Hold> filteredHolds = character.getHolds().stream()
                .filter(hold1 -> !hold1.getId().equals(hold.getId())).collect(Collectors.toList());

        character.setHolds(filteredHolds);

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character adjustImprovements(String gameRoleId, String characterId,
                                        List<String> improvementIDs, List<String> futureImprovementIDs) {
        GameRole gameRole = getGameRole(gameRoleId);
        Character character = getCharacterById(gameRole, characterId);
        int allowedImprovements = character.getAllowedImprovements();
        AtomicBoolean shouldAbort = new AtomicBoolean(false);

        if (allowedImprovements >= improvementIDs.size() + futureImprovementIDs.size()) {
            // Check if existing improvements is not in improvementIDs lists,
            // remove if it is not in the lists
            character.getImprovementMoves().forEach(characterMove -> {
                if (!improvementIDs.contains(characterMove.getId())) {
                    // switch on MoveType, reverse move
                    switch (characterMove.getKind()) {
                        case IMPROVE_STAT:
                            unModifyCharacterStat(character, characterMove);

                            break;
                        default:
                            // TODO: throw exception
                    }
                    character.setImprovementMoves(
                            character.getImprovementMoves().stream()
                                    .filter(characterMove1 -> !characterMove1.getId().equals(characterMove.getId()))
                                    .collect(Collectors.toList()));
                }
            });

            // Check if existing futureImprovements is not in futureImprovementIDs lists,
            // abort operation if there's a problem. Existing futureImprovements
            // should not be removed
            character.getFutureImprovementMoves().forEach(characterMove -> {
                if (!futureImprovementIDs.contains(characterMove.getId())) {
                    // Don't try to reverse future improvements
                    // TODO: Send error to front-end
                    shouldAbort.set(true);
                }
            });

            if (shouldAbort.get()) {
                // Return without making any changes
                return character;
            }


            improvementIDs.forEach(improvementID -> {
                // check if already an improvement
                Optional<CharacterMove> existingImprovementOptional = character.getImprovementMoves()
                        .stream().filter(characterMove -> characterMove.getId().equals(improvementID)).findFirst();

                if (existingImprovementOptional.isEmpty()) {
                    // Get move from db
                    Move move = moveService.findById(improvementID);
                    // Convert to CharacterMove
                    CharacterMove characterMove = CharacterMove.createFromMove(move, true);
                    // Give CharacterMove an id
                    characterMove.setId(new ObjectId().toString());

                    // switch on move type, add move, change character
                    switch (characterMove.getKind()) {
                        case IMPROVE_STAT:
                            modifyCharacterStat(character, characterMove);

                            break;
                        default:
                            // TODO: throw exception
                    }
                    character.getImprovementMoves().add(characterMove);
                }
            });


        }


        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character spendExperience(String gameRoleId, String characterId) {
        GameRole gameRole = getGameRole(gameRoleId);
        Character character = getCharacterById(gameRole, characterId);

        int currentExperience = character.getExperience();

        if (currentExperience >= 5 ) {
            int allowedImprovementsIncrease = currentExperience / 5;
            int remainingExperience = currentExperience % 5;
            character.setExperience(remainingExperience);
            character.setAllowedImprovements(character.getAllowedImprovements() + allowedImprovementsIncrease);
        }


        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

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
                    .id(new ObjectId().toString())
                    .stat(stat)
                    .value(value)
                    .isHighlighted(false)
                    .build();
            character.getStatsBlock().getStats().add(newStat);
        } else {
            optionalStat.get().setValue(value);
        }
    }

    private Character getCharacterById(GameRole gameRole, String characterId) {
        return gameRole.getCharacters().stream()
                .filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();
    }

    private GameRole getGameRole(String gameRoleId) {
        return gameRoleRepository.findById(gameRoleId).orElseThrow(NoSuchElementException::new);
    }

    private void modifyCharacterStat(Character character, CharacterMove characterMove) {

        CharacterStat statToBeModified = character.getStatsBlock().getStats()
                .stream()
                .filter(characterStat -> characterStat.getStat().equals(characterMove.getStatModifier().getStatToModify()))
                .findFirst().orElseThrow();

        // Only increase stat if under the limit
        if (statToBeModified.getValue() < characterMove.getStatModifier().getMaxLimit()) {
            statToBeModified.setValue(statToBeModified.getValue() + characterMove.getStatModifier().getModification());
            statToBeModified.setModifier(characterMove.getStatModifier().getId());
        }
    }

    private void unModifyCharacterStat(Character character, CharacterMove characterMove) {
        CharacterStat statToBeModified = character.getStatsBlock().getStats()
                .stream()
                .filter(characterStat -> characterStat.getStat().equals(characterMove.getStatModifier().getStatToModify()))
                .findFirst().orElseThrow();

        statToBeModified.setValue(statToBeModified.getValue() - characterMove.getStatModifier().getModification());
        statToBeModified.setModifier(null);
    }
}
