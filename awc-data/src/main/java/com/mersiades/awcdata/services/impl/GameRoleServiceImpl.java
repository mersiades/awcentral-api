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

import static com.mersiades.awccontent.constants.MoveNames.*;
import static com.mersiades.awccontent.content.MovesContent.*;
import static com.mersiades.awccontent.content.PlaybookCreatorsContent.*;

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
                .allowedOtherPlaybookMoves(0)
                .harm(harm).build();
        characterService.save(newCharacter);
        assert gameRole != null;
        gameRole.getCharacters().add(newCharacter);
        gameRoleRepository.save(gameRole);
        return newCharacter;
    }

    @Override
    public Character setCharacterPlaybook(String gameRoleId, String characterId, PlaybookType playbookType) {
        GameRole gameRole = getGameRole(gameRoleId);
        PlaybookCreator playbookCreator = playbookCreatorService.findByPlaybookType(playbookType);

        Character character = getCharacterById(gameRole, characterId);

        CharacterHarm harm = CharacterHarm.builder()
                .id(new ObjectId().toString())
                .hasChangedPlaybook(false)
                .hasComeBackHard(false)
                .hasComeBackWeird(false)
                .hasDied(false)
                .isStabilized(false)
                .value(0)
                .build();

        // Reset fields if already filled
        character.setName(null);
        character.setPlaybookUniques(null);
        character.setHasCompletedCharacterCreation(false);
        character.setHasPlusOneForward(false);
        character.setBarter(-1);
        character.setHarm(harm);
        character.setStatsBlock(null);
        character.setVehicleCount(0);
        character.setBattleVehicleCount(0);
        character.setExperience(0);
        character.setAllowedImprovements(0);
        character.setAllowedOtherPlaybookMoves(0);
        character.setBattleVehicles(new ArrayList<>());
        character.setVehicles(new ArrayList<>());
        character.setHxBlock(new ArrayList<>());
        character.setGear(new ArrayList<>());
        character.setLooks(new ArrayList<>());
        character.setCharacterMoves(new ArrayList<>());
        character.setImprovementMoves(new ArrayList<>());
        character.setFutureImprovementMoves(new ArrayList<>());
        character.setHolds(new ArrayList<>());

        // Set default moves for playbook
        List<Move> defaultMoves = moveService.findAllByPlaybookAndKind(playbookType, MoveType.DEFAULT_CHARACTER);
        List<CharacterMove> defaultCharacterMoves = defaultMoves.stream().map(CharacterMove::createFromMove).collect(Collectors.toList());
        character.setCharacterMoves(defaultCharacterMoves);

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
        addUnique(character, playbookType);
        character.setPlaybook(playbookType);
        character.setAllowedPlaybookMoves(playbookCreator.getMoveChoiceCount());
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

        GameRole gameRole = getGameRole(gameRoleId);
        Character character = getCharacterById(gameRole, characterId);

        List<String> previousCharacterMoveNames = character.getCharacterMoves()
                .stream().map(Move::getName).collect(Collectors.toList());

        // Get selected moves from db
        List<Move> selectedMoves = moveService.findAllById(moveIds);

        // Convert Moves to CharacterMoves
        List<CharacterMove> characterMoves = selectedMoves.stream()
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

        // Add AngelKit if it's the preparedForTheInevitable move
        if (newCharacterMoveNames.contains(preparedForTheInevitableName) &&
                character.getPlaybookUniques().getAngelKit() == null &&
                !previousCharacterMoveNames.contains(preparedForTheInevitableName)
        ) {
            addUnique(character, preparedForTheInevitableName);
        }

        // Remove AngelKit if preparedForTheInevitable move is being removed
        if (previousCharacterMoveNames.contains(preparedForTheInevitableName)
                && !newCharacterMoveNames.contains(preparedForTheInevitableName)) {
            removeUnique(character, preparedForTheInevitableName);
        }

        // Adjust vehicleCount and battleVehicleCount move for Driver
        if (character.getPlaybook().equals(PlaybookType.DRIVER)) {
            if (newCharacterMoveNames.contains(collectorName)
                    && !previousCharacterMoveNames.contains(collectorName)) {
                character.setVehicleCount(character.getVehicleCount() + 2);
            } else if (!newCharacterMoveNames.contains(collectorName)
                    && previousCharacterMoveNames.contains(collectorName)) {
                int newCount = character.getVehicleCount() - 2;
                character.setVehicleCount(newCount);
                character.setVehicles(character.getVehicles().subList(0, newCount));
            }

            if (newCharacterMoveNames.contains(otherCarTankName)
                    && !previousCharacterMoveNames.contains(otherCarTankName)) {
                character.setBattleVehicleCount(character.getBattleVehicleCount() + 1);
            } else if (!newCharacterMoveNames.contains(otherCarTankName)
                    && previousCharacterMoveNames.contains(otherCarTankName)) {
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

        GameRole gameRole = getGameRole(gameRoleId);
        Character character = getCharacterById(gameRole, characterId);

        if (character.getPlaybookUniques() != null && character.getPlaybookUniques().getAngelKit() != null) {
            // Update existing AngelKit
            character.getPlaybookUniques().getAngelKit().setStock(stock);
            character.getPlaybookUniques().getAngelKit().setHasSupplier(hasSupplier);
        } else if (character.getPlaybookUniques() != null && character.getPlaybookUniques().getAngelKit() == null) {
            // Add new AngelKit to existing PlaybookUniques
            AngelKit angelKit = AngelKit.builder().id(new ObjectId().toString())
                    .hasSupplier(hasSupplier)
                    .angelKitMoves(List.of(stabilizeAndHeal, speedTheRecoveryOfSomeone, reviveSomeone, treatAnNpc))
                    .stock(stock).build();

            character.getPlaybookUniques().setAngelKit(angelKit);
        } else {
            // Make new PlaybookUniques & AngelKit
            AngelKit angelKit = AngelKit.builder().id(new ObjectId().toString())
                    .hasSupplier(hasSupplier)
                    .angelKitMoves(List.of(stabilizeAndHeal, speedTheRecoveryOfSomeone, reviveSomeone, treatAnNpc))
                    .stock(stock).build();

            PlaybookUniques angelUnique = PlaybookUniques.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.ANGEL_KIT)
                    .angelKit(angelKit)
                    .build();

            character.setPlaybookUniques(angelUnique);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setBrainerGear(String gameRoleId, String characterId, List<String> brainerGear) {

        GameRole gameRole = getGameRole(gameRoleId);
        Character character = getCharacterById(gameRole, characterId);

        BrainerGear brainerGear1 = BrainerGear.builder()
                .id(new ObjectId().toString())
                .allowedItemsCount(brainerGearCreator.getDefaultItemCount())
                .brainerGear(brainerGear)
                .build();

        if (character.getPlaybookUniques() == null) {
            // make new brainerGear & set
            PlaybookUniques brainerUnique = PlaybookUniques.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.BRAINER_GEAR)
                    .brainerGear(brainerGear1)
                    .build();

            character.setPlaybookUniques(brainerUnique);
        } else if (character.getPlaybookUniques() != null && character.getPlaybookUniques().getBrainerGear() == null) {
            character.getPlaybookUniques().setBrainerGear(brainerGear1);
        } else if (character.getPlaybookUniques() != null && character.getPlaybookUniques().getBrainerGear() != null) {
            character.getPlaybookUniques().setBrainerGear(brainerGear1);
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

        if (character.getPlaybookUniques() == null || character.getPlaybookUniques().getType() != UniqueType.CUSTOM_WEAPONS) {
            // Create new PlaybookUniques for Battlebabe
            CustomWeapons customWeapons = CustomWeapons.builder().id(new ObjectId().toString())
                    .weapons(weapons)
                    .build();

            PlaybookUniques battlebabeUnique = PlaybookUniques.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.CUSTOM_WEAPONS)
                    .customWeapons(customWeapons)
                    .build();

            character.setPlaybookUniques(battlebabeUnique);
        } else {
            // Update existing PlaybookUniques
            character.getPlaybookUniques().getCustomWeapons().setWeapons(weapons);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setEstablishment(String gameRoleId, String characterId, Establishment establishment) {

        GameRole gameRole = getGameRole(gameRoleId);
        Character character = getCharacterById(gameRole, characterId);

        if (establishment.getId() == null) {
            establishment.setId(new ObjectId().toString());
        }

        if (character.getPlaybookUniques() == null) {
            PlaybookUniques establishmentUnique = PlaybookUniques.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.ESTABLISHMENT)
                    .establishment(establishment)
                    .build();

            character.setPlaybookUniques(establishmentUnique);
        } else {
            // Update existing Establishment
            character.getPlaybookUniques().setEstablishment(establishment);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setFollowers(String gameRoleId, String characterId, Followers followers) {

        GameRole gameRole = getGameRole(gameRoleId);
        Character character = getCharacterById(gameRole, characterId);

        if (followers.getId() == null) {
            followers.setId(new ObjectId().toString());
        }

        if (character.getPlaybookUniques() == null) {

            PlaybookUniques playbookUniqueHardHolder = PlaybookUniques.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.FOLLOWERS)
                    .followers(followers)
                    .build();

            character.setPlaybookUniques(playbookUniqueHardHolder);
        } else {
            // Update existing PlaybookUniques
            character.getPlaybookUniques().setFollowers(followers);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setGang(String gameRoleId, String characterId, Gang gang) {

        GameRole gameRole = getGameRole(gameRoleId);
        Character character = getCharacterById(gameRole, characterId);

        if (gang.getId() == null) {
            gang.setId(new ObjectId().toString());
        }

        if (character.getPlaybookUniques() != null) {
            // Will overwrite existing gang
            character.getPlaybookUniques().setGang(gang);
        } else {
            // Defensive coding, probably not necessary
            PlaybookUniques gangUnique = PlaybookUniques.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.GANG)
                    .gang(gang)
                    .build();

            character.setPlaybookUniques(gangUnique);
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

        if (character.getPlaybookUniques() == null || character.getPlaybookUniques().getType() != UniqueType.HOLDING) {

            PlaybookUniques playbookUniqueHardHolder = PlaybookUniques.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.HOLDING)
                    .holding(holding)
                    .build();

            character.setPlaybookUniques(playbookUniqueHardHolder);
        } else {
            // Update existing PlaybookUniques
            character.getPlaybookUniques().setHolding(holding);
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

        if (character.getPlaybookUniques() == null || character.getPlaybookUniques().getType() != UniqueType.SKINNER_GEAR) {
            // Create new PlaybookUniques for Battlebabe
            PlaybookUniques playbookUniqueSkinner = PlaybookUniques.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.SKINNER_GEAR)
                    .skinnerGear(skinnerGear)
                    .build();

            character.setPlaybookUniques(playbookUniqueSkinner);
        } else {
            // Update existing PlaybookUniques
            character.getPlaybookUniques().setSkinnerGear(skinnerGear);
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

        if (character.getPlaybookUniques() == null || character.getPlaybookUniques().getType() != UniqueType.WEAPONS) {
            // Create new PlaybookUniques for Battlebabe
            Weapons newWeapons = Weapons.builder()
                    .id(new ObjectId().toString())
                    .weapons(weapons)
                    .build();

            PlaybookUniques gunluggerUnique = PlaybookUniques.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.WEAPONS)
                    .weapons(newWeapons)
                    .build();

            character.setPlaybookUniques(gunluggerUnique);
        } else {
            // Update existing PlaybookUniques
            character.getPlaybookUniques().getWeapons().setWeapons(weapons);
        }

        // Save to db
        characterService.save(character);
        gameRoleRepository.save(gameRole);

        return character;
    }

    @Override
    public Character setWorkspace(String gameRoleId, String characterId, Workspace workspace) {

        GameRole gameRole = getGameRole(gameRoleId);
        Character character = getCharacterById(gameRole, characterId);

        if (workspace.getId() == null) {
            workspace.setId(new ObjectId().toString());
        }

        if (character.getPlaybookUniques() == null) {
            PlaybookUniques workspaceUnique = PlaybookUniques.builder()
                    .id(new ObjectId().toString())
                    .type(UniqueType.WORKSPACE)
                    .workspace(workspace)
                    .build();

            character.setPlaybookUniques(workspaceUnique);
        } else {
            // Update existing Establishment
            character.getPlaybookUniques().setWorkspace(workspace);
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

        character.getPlaybookUniques().getHolding().setBarter(amount);

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

        character.getPlaybookUniques().getFollowers().setBarter(barter);
        character.getPlaybookUniques().getFollowers().setFollowers(followers);
        character.getPlaybookUniques().getFollowers().setDescription(description);

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

        if (character.getPlaybookUniques().getWorkspace().getProjects().size() == 0) {
            // Add first Project
            character.getPlaybookUniques().getWorkspace().getProjects().add(project);
        } else {
            // Replace Project with updated data, if it already exists
            ListIterator<Project> iterator = character.getPlaybookUniques().getWorkspace().getProjects().listIterator();
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
                character.getPlaybookUniques().getWorkspace().getProjects().add(project);
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

        List<Project> filteredProjects = character.getPlaybookUniques().getWorkspace().getProjects().stream()
                .filter(project1 -> !project1.getId().equals(project.getId())).collect(Collectors.toList());

        character.getPlaybookUniques().getWorkspace().setProjects(filteredProjects);

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
                        case ADD_CHARACTER_MOVE:
                            int newAllowedMoves = character.getAllowedPlaybookMoves() - 1;
                            List<CharacterMove> truncatedMoves = character.getCharacterMoves()
                                    .stream().limit(newAllowedMoves).collect(Collectors.toList());

                            character.setAllowedPlaybookMoves(newAllowedMoves);
                            character.setCharacterMoves(truncatedMoves);
                            break;
                        case ADD_OTHER_PB_MOVE:
                            character.setAllowedOtherPlaybookMoves(character.getAllowedOtherPlaybookMoves() - 1);
                            break;
                        case ADJUST_UNIQUE:
                            unAdjustUnique(character, characterMove);
                            break;
                        case ADD_UNIQUE:
                            removeUnique(character, characterMove.getName());
                            break;
                        case ADD_VEHICLE:
                            int newVehicleCount = character.getVehicleCount() - 1;
                            List<Vehicle> truncatedVehicles = character.getVehicles()
                                    .stream().limit(newVehicleCount).collect(Collectors.toList());

                            character.setVehicles(truncatedVehicles);
                            character.setVehicleCount(newVehicleCount);
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
                // Check if already an improvement
                Optional<CharacterMove> existingImprovementOptional = character.getImprovementMoves()
                        .stream().filter(characterMove -> characterMove.getId().equals(improvementID)).findFirst();

                if (existingImprovementOptional.isEmpty()) {
                    // Get move from db
                    Move move = moveService.findById(improvementID);
                    // Convert to CharacterMove
                    CharacterMove characterMove = CharacterMove.createFromMove(move, true);
                    // Give CharacterMove a PlaybookType
                    characterMove.setPlaybook(character.getPlaybook());

                    // Switch on move type, change character
                    switch (characterMove.getKind()) {
                        case IMPROVE_STAT:
                            modifyCharacterStat(character, characterMove);
                            break;
                        case ADD_CHARACTER_MOVE:
                            character.setAllowedPlaybookMoves(character.getAllowedPlaybookMoves() + 1);
                            break;
                        case ADD_OTHER_PB_MOVE:
                            character.setAllowedOtherPlaybookMoves(character.getAllowedOtherPlaybookMoves() + 1);
                            break;
                        case ADJUST_UNIQUE:
                            adjustUnique(character, characterMove);
                            break;
                        case ADD_UNIQUE:
                            addUnique(character, characterMove.getName());
                            break;
                        case ADD_VEHICLE:
                            character.setVehicleCount(character.getVehicleCount() + 1);
                            break;
                        default:
                            // TODO: throw exception
                    }
                    // Add move
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

        if (currentExperience >= 5) {
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

    // Removes PlaybookUnique from Character on removal of ADD_UNIQUE improvement or the Gunlugger move
    private void removeUnique(Character character, String characterMoveName) {

        List<CharacterMove> filteredMoves;

        switch (characterMoveName) {
            case preparedForTheInevitableName:
                // Remove AngelKit
                character.getPlaybookUniques().setAngelKit(null);
                break;
            case addGangLeadershipName:
                // Remove leadership move
                filteredMoves = character.getCharacterMoves().stream()
                        .filter(characterMove -> !characterMove.getName().equals(leadershipName)).collect(Collectors.toList());
                character.setCharacterMoves(filteredMoves);

                // Remove gang
                character.getPlaybookUniques().setGang(null);
                break;
            case addGangPackAlphaName:
                // Remove pack alpha move
                filteredMoves = character.getCharacterMoves().stream()
                        .filter(characterMove -> !characterMove.getName().equals(packAlphaName)).collect(Collectors.toList());
                character.setCharacterMoves(filteredMoves);

                // Remove gang
                character.getPlaybookUniques().setGang(null);
                break;
            case addHoldingName:
                // Remove wealth move
                filteredMoves = character.getCharacterMoves().stream()
                        .filter(characterMove -> !characterMove.getName().equals(wealthName)).collect(Collectors.toList());
                character.setCharacterMoves(filteredMoves);

                // Remove holding
                character.getPlaybookUniques().setHolding(null);
                break;
            case addWorkspaceName:
                // Remove Workspace
                character.getPlaybookUniques().setWorkspace(null);
                break;
            case addEstablishmentName:
                // Remove Establishment
                character.getPlaybookUniques().setEstablishment(null);
                break;
            case addFollowersName:
                // Remove fortunes move
                filteredMoves = character.getCharacterMoves().stream()
                        .filter(characterMove -> !characterMove.getName().equals(fortunesName)).collect(Collectors.toList());
                character.setCharacterMoves(filteredMoves);

                // Remove Followers
                character.getPlaybookUniques().setFollowers(null);
                break;
            default:
                // TODO: throw error
        }
    }

    // Adds PlaybookUnique with default options to Character on addition of ADD_UNIQUE improvement or the Gunlugger move
    private void addUnique(Character character, String characterMoveName) {

        Gang gang = Gang.builder()
                .id(new ObjectId().toString())
                .uniqueType(UniqueType.GANG)
                .size(gangCreator.getDefaultSize())
                .allowedStrengths(gangCreator.getStrengthChoiceCount())
                .harm(gangCreator.getDefaultHarm())
                .armor(gangCreator.getDefaultArmor())
                .tags(gangCreator.getDefaultTags())
                .build();

        switch (characterMoveName) {
            case preparedForTheInevitableName:
                AngelKit angelKit = AngelKit.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.ANGEL_KIT)
                        .hasSupplier(false)
                        .angelKitMoves(List.of(treatAnNpc, reviveSomeone, speedTheRecoveryOfSomeone, stabilizeAndHeal))
                        .stock(2)
                        .build();

                character.getPlaybookUniques().setAngelKit(angelKit);
                break;
            case addGangLeadershipName:
                // Add leadership move
                CharacterMove leadershipAsCM = CharacterMove.createFromMove(leadership);
                leadershipAsCM.setId(new ObjectId().toString());
                character.getCharacterMoves().add(leadershipAsCM);

                // Add gang
                character.getPlaybookUniques().setGang(gang);
                break;
            case addGangPackAlphaName:
                // Add pack alpha move
                CharacterMove packAlphaAsCM = CharacterMove.createFromMove(packAlpha);
                packAlphaAsCM.setId(new ObjectId().toString());
                character.getCharacterMoves().add(packAlphaAsCM);

                character.getPlaybookUniques().setGang(gang);
                break;
            case addHoldingName:
                // Add wealth move
                CharacterMove wealthAsCM = CharacterMove.createFromMove(wealth);
                wealthAsCM.setId(new ObjectId().toString());
                character.getCharacterMoves().add(wealthAsCM);

                // Add Holding with default settings
                Holding holding = Holding.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.HOLDING)
                        .holdingSize(holdingCreator.getDefaultHoldingSize())
                        .souls(holdingCreator.getDefaultSouls())
                        .gigs(holdingCreator.getDefaultGigs())
                        .wants(List.of(holdingCreator.getDefaultWant()))
                        .gangDefenseArmorBonus(holdingCreator.getDefaultArmorBonus())
                        .surplus(holdingCreator.getDefaultSurplus())
                        .gangSize(holdingCreator.getDefaultGangSize())
                        .gangHarm(holdingCreator.getDefaultGangHarm())
                        .gangArmor(holdingCreator.getDefaultGangArmor())
                        .strengthsCount(holdingCreator.getDefaultStrengthsCount())
                        .weaknessesCount(holdingCreator.getDefaultWeaknessesCount())
                        .gangTags(List.of(holdingCreator.getDefaultGangTag()))
                        .build();
                character.getPlaybookUniques().setHolding(holding);
                break;
            case addWorkspaceName:
                // Driver may not have a PlaybookUniques at this point, so conditionally add
                if (character.getPlaybookUniques() == null) {
                    PlaybookUniques playbookUniques = PlaybookUniques.builder()
                            .id(new ObjectId().toString())
                            // The only Unique a Driver can get is WORKSPACE
                            .type(UniqueType.WORKSPACE)
                            .build();
                    character.setPlaybookUniques(playbookUniques);
                }

                // Add Workspace with default settings
                Workspace workspace = Workspace.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.WORKSPACE)
                        .projectInstructions(workspaceCreator.getProjectInstructions())
                        .workspaceInstructions(workspaceCreator.getWorkspaceInstructions())
                        .itemsCount(workspaceCreator.getDefaultItemsCount())
                        .build();

                character.getPlaybookUniques().setWorkspace(workspace);
                break;
            case addEstablishmentName:
                // Add Establishment with default settings
                Establishment establishment = Establishment.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.ESTABLISHMENT)
                        .regulars(establishmentCreator.getRegularsNames())
                        .interestedParties(establishmentCreator.getInterestedPartyNames())
                        .build();

                character.getPlaybookUniques().setEstablishment(establishment);
                break;
            case addFollowersName:
                // Add fortunes move
                CharacterMove fortunesAsCM = CharacterMove.createFromMove(fortunes);
                fortunesAsCM.setId(new ObjectId().toString());
                character.getCharacterMoves().add(fortunesAsCM);

                // Add Followers with default settings
                Followers followers = Followers.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.FOLLOWERS)
                        .followers(followersCreator.getDefaultNumberOfFollowers())
                        .surplusBarter(followersCreator.getDefaultSurplusBarter())
                        .fortune(followersCreator.getDefaultFortune())
                        .wants(followersCreator.getDefaultWants())
                        .strengthsCount(followersCreator.getDefaultStrengthsCount())
                        .weaknessesCount(followersCreator.getDefaultWeaknessesCount())
                        .build();

                character.getPlaybookUniques().setFollowers(followers);
                break;
            default:
                // TODO: throw error
        }
    }

    // Adds PlaybookUnique with default options to Character when creating a new Playbook/Character
    private void addUnique(Character character, PlaybookType playbookType) {
        switch (playbookType) {
            case ANGEL:
                AngelKit angelKit = AngelKit.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.ANGEL_KIT)
                        .hasSupplier(false)
                        .angelKitMoves(List.of(treatAnNpc, reviveSomeone, speedTheRecoveryOfSomeone, stabilizeAndHeal))
                        .stock(angelKitCreator.getStartingStock())
                        .description(angelKitCreator.getAngelKitInstructions())
                        .build();

                PlaybookUniques playbookUniquesAngel = PlaybookUniques.builder()
                        .id(new ObjectId().toString())
                        .type(UniqueType.ANGEL_KIT)
                        .angelKit(angelKit)
                        .build();

                character.setPlaybookUniques(playbookUniquesAngel);
                break;
            case BATTLEBABE:
                CustomWeapons customWeapons = CustomWeapons.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.CUSTOM_WEAPONS)
                        .build();

                PlaybookUniques playbookUniquesBattlebabe = PlaybookUniques.builder()
                        .id(new ObjectId().toString())
                        .type(UniqueType.CUSTOM_WEAPONS)
                        .customWeapons(customWeapons)
                        .build();

                character.setPlaybookUniques(playbookUniquesBattlebabe);
                break;
            case BRAINER:
                BrainerGear brainerGear = BrainerGear.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.BRAINER_GEAR)
                        .allowedItemsCount(brainerGearCreator.getDefaultItemCount())
                        .build();
                PlaybookUniques playbookUniquesBrainer = PlaybookUniques.builder()
                        .id(new ObjectId().toString())
                        .type(UniqueType.BRAINER_GEAR)
                        .brainerGear(brainerGear)
                        .build();

                character.setPlaybookUniques(playbookUniquesBrainer);
                break;
            case CHOPPER:
                Gang gang = Gang.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.GANG)
                        .allowedStrengths(gangCreator.getStrengthChoiceCount())
                        .size(gangCreator.getDefaultSize())
                        .harm(gangCreator.getDefaultHarm())
                        .armor(gangCreator.getDefaultArmor())
                        .tags(gangCreator.getDefaultTags())
                        .build();

                PlaybookUniques playbookUniquesChopper = PlaybookUniques.builder()
                        .id(new ObjectId().toString())
                        .type(UniqueType.GANG)
                        .gang(gang)
                        .build();

                character.setPlaybookUniques(playbookUniquesChopper);
                break;
            case DRIVER:
                // Driver has no PlaybookUnique
                break;
            case GUNLUGGER:
                Weapons weapons = Weapons.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.WEAPONS)
                        .build();
                PlaybookUniques playbookUniquesGunlugger = PlaybookUniques.builder()
                        .id(new ObjectId().toString())
                        .type(UniqueType.WEAPONS)
                        .weapons(weapons)
                        .build();

                character.setPlaybookUniques(playbookUniquesGunlugger);
                break;
            case HARDHOLDER:
                Holding holding = Holding.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.HOLDING)
                        .holdingSize(holdingCreator.getDefaultHoldingSize())
                        .souls(holdingCreator.getDefaultSouls())
                        .gigs(holdingCreator.getDefaultGigs())
                        .wants(List.of(holdingCreator.getDefaultWant()))
                        .gangDefenseArmorBonus(holdingCreator.getDefaultArmorBonus())
                        .surplus(holdingCreator.getDefaultSurplus())
                        .gangSize(holdingCreator.getDefaultGangSize())
                        .gangHarm(holdingCreator.getDefaultGangHarm())
                        .gangArmor(holdingCreator.getDefaultGangArmor())
                        .gangTags(List.of(holdingCreator.getDefaultGangTag()))
                        .strengthsCount(holdingCreator.getDefaultStrengthsCount())
                        .weaknessesCount(holdingCreator.getDefaultWeaknessesCount())
                        .build();
                PlaybookUniques playbookUniquesHardholder = PlaybookUniques.builder()
                        .id(new ObjectId().toString())
                        .type(UniqueType.HOLDING)
                        .holding(holding)
                        .build();

                character.setVehicleCount(holdingCreator.getDefaultVehiclesCount());
                character.setBattleVehicleCount(holdingCreator.getDefaultBattleVehicleCount());

                character.setPlaybookUniques(playbookUniquesHardholder);
                break;
            case HOCUS:
                Followers followers = Followers.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.FOLLOWERS)
                        .followers(followersCreator.getDefaultNumberOfFollowers())
                        .surplusBarter(followersCreator.getDefaultSurplusBarter())
                        .fortune(followersCreator.getDefaultFortune())
                        .wants(followersCreator.getDefaultWants())
                        .strengthsCount(followersCreator.getDefaultStrengthsCount())
                        .weaknessesCount(followersCreator.getDefaultWeaknessesCount())
                        .build();

                PlaybookUniques playbookUniquesHocus = PlaybookUniques.builder()
                        .id(new ObjectId().toString())
                        .type(UniqueType.FOLLOWERS)
                        .followers(followers)
                        .build();

                character.setPlaybookUniques(playbookUniquesHocus);
                break;
            case MAESTRO_D:
                Establishment establishment = Establishment.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.ESTABLISHMENT)
                        .regulars(establishmentCreator.getRegularsNames())
                        .interestedParties(establishmentCreator.getInterestedPartyNames())
                        .build();
                PlaybookUniques playbookUniquesMaestroD = PlaybookUniques.builder()
                        .id(new ObjectId().toString())
                        .type(UniqueType.ESTABLISHMENT)
                        .establishment(establishment)
                        .build();

                character.setPlaybookUniques(playbookUniquesMaestroD);
                break;
            case SAVVYHEAD:
                Workspace workspace = Workspace.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.WORKSPACE)
                        .projectInstructions(workspaceCreator.getProjectInstructions())
                        .workspaceInstructions(workspaceCreator.getWorkspaceInstructions())
                        .itemsCount(workspaceCreator.getDefaultItemsCount())
                        .build();
                PlaybookUniques playbookUniquesSavvyhead = PlaybookUniques.builder()
                        .id(new ObjectId().toString())
                        .type(UniqueType.WORKSPACE)
                        .workspace(workspace)
                        .build();

                character.setPlaybookUniques(playbookUniquesSavvyhead);
                break;
            case SKINNER:
                SkinnerGear skinnerGear = SkinnerGear.builder()
                        .id(new ObjectId().toString())
                        .uniqueType(UniqueType.SKINNER_GEAR)
                        .build();
                PlaybookUniques playbookUniquesSkinner = PlaybookUniques.builder()
                        .id(new ObjectId().toString())
                        .type(UniqueType.SKINNER_GEAR)
                        .skinnerGear(skinnerGear)
                        .build();

                character.setPlaybookUniques(playbookUniquesSkinner);
                break;
            default:
                // TODO: throw error
        }
    }

    private void unAdjustUnique(Character character, CharacterMove characterMove) {
        switch (characterMove.getName()) {
            case adjustAngelUnique1Name:
                character.getPlaybookUniques().getAngelKit().setHasSupplier(false);
                break;
            case adjustBrainerUnique1Name:
                character.getPlaybookUniques().getBrainerGear().setAllowedItemsCount(
                        brainerGearCreator.getDefaultItemCount()
                );
                List<String> truncatedItems = character.getPlaybookUniques().getBrainerGear().getBrainerGear()
                        .stream().limit(brainerGearCreator.getDefaultItemCount()).collect(Collectors.toList());
                character.getPlaybookUniques().getBrainerGear().setBrainerGear(truncatedItems);
                break;
            case adjustChopperUnique1Name:
                // Deliberately falls through
            case adjustChopperUnique2Name:
                int newAllowedStrengths = character.getPlaybookUniques().getGang().getAllowedStrengths() -1;
                character.getPlaybookUniques().getGang()
                        .setAllowedStrengths(newAllowedStrengths);

                List<GangOption> truncatedStrengthOptions = character.getPlaybookUniques().getGang().getStrengths()
                        .stream().limit(newAllowedStrengths).collect(Collectors.toList());
                character.getPlaybookUniques().getGang().setStrengths(truncatedStrengthOptions);
                break;
            case adjustHardHolderUnique1Name:
                // Deliberately falls through
            case adjustHardHolderUnique2Name:
                int newHoldingStrengthsCount = character.getPlaybookUniques().getHolding().getStrengthsCount() -1;
                character.getPlaybookUniques().getHolding().setStrengthsCount(newHoldingStrengthsCount);

                List<HoldingOption> truncatedHoldingOptions = character.getPlaybookUniques().getHolding()
                        .getSelectedStrengths().stream().limit(newHoldingStrengthsCount).collect(Collectors.toList());

                character.getPlaybookUniques().getHolding().setSelectedStrengths(truncatedHoldingOptions);
                break;
            case adjustHardHolderUnique3Name:
                character.getPlaybookUniques().getHolding().setWeaknessesCount(
                        character.getPlaybookUniques().getHolding().getWeaknessesCount() + 1
                );
                break;
            case adjustHocusUnique1Name:
                // Deliberately falls through
            case adjustHocusUnique2Name:
                int newFollowersStrengthsCount = character.getPlaybookUniques().getFollowers().getStrengthsCount() - 1;
                character.getPlaybookUniques().getFollowers().setStrengthsCount(newFollowersStrengthsCount);

                List<FollowersOption> truncatedFollowersStrengths = character.getPlaybookUniques().getFollowers().getSelectedStrengths()
                        .stream().limit(newFollowersStrengthsCount).collect(Collectors.toList());

                character.getPlaybookUniques().getFollowers().setSelectedStrengths(truncatedFollowersStrengths);
                break;
            case adjustSavvyheadUnique1Name:
                int newWorkspaceItemsCount = character.getPlaybookUniques().getWorkspace().getItemsCount() - 2;
                character.getPlaybookUniques().getWorkspace().setItemsCount(newWorkspaceItemsCount);

                List<String> truncatedWorkspaceItems = character.getPlaybookUniques().getWorkspace().getWorkspaceItems()
                        .stream().limit(newWorkspaceItemsCount).collect(Collectors.toList());

                character.getPlaybookUniques().getWorkspace().setWorkspaceItems(truncatedWorkspaceItems);
                break;
            case adjustSavvyheadUnique2Name:
                int newWorkspaceItemsCount2 = character.getPlaybookUniques().getWorkspace().getItemsCount() - 1;
                character.getPlaybookUniques().getWorkspace().setItemsCount(newWorkspaceItemsCount2);

                List<String> filteredWorkspaceItems = character.getPlaybookUniques().getWorkspace().getWorkspaceItems()
                        .stream().filter(item -> !item.equals(WORKSPACE_LIFE_SUPPORT_ITEM)).collect(Collectors.toList());

                character.getPlaybookUniques().getWorkspace().setWorkspaceItems(filteredWorkspaceItems);
                break;
            default:
                // TODO: throw error
        }
    }

    private void adjustUnique(Character character, CharacterMove characterMove) {
        switch (characterMove.getName()) {
            case adjustAngelUnique1Name:
                character.getPlaybookUniques().getAngelKit().setHasSupplier(true);
                break;
            case adjustBrainerUnique1Name:
                character.getPlaybookUniques().getBrainerGear()
                        .setAllowedItemsCount(character.getPlaybookUniques().getBrainerGear().getAllowedItemsCount() + 2);
                break;
            case adjustChopperUnique1Name:
                // Deliberately falls through
            case adjustChopperUnique2Name:
                character.getPlaybookUniques().getGang()
                        .setAllowedStrengths(character.getPlaybookUniques().getGang().getAllowedStrengths() + 1);
                break;
            case adjustHardHolderUnique1Name:
                // Deliberately falls through
            case adjustHardHolderUnique2Name:
                character.getPlaybookUniques().getHolding().setStrengthsCount(
                        character.getPlaybookUniques().getHolding().getStrengthsCount() + 1
                );
                break;
            case adjustHardHolderUnique3Name:
                character.getPlaybookUniques().getHolding().setWeaknessesCount(
                        character.getPlaybookUniques().getHolding().getWeaknessesCount() - 1
                );
                break;
            case adjustHocusUnique1Name:
                // Deliberately falls through
            case adjustHocusUnique2Name:
                character.getPlaybookUniques().getFollowers().setStrengthsCount(
                        character.getPlaybookUniques().getFollowers().getStrengthsCount() + 1
                );
                break;
            case adjustSavvyheadUnique1Name:
                character.getPlaybookUniques().getWorkspace().setItemsCount(
                        character.getPlaybookUniques().getWorkspace().getItemsCount() + 2
                );
                break;
            case adjustSavvyheadUnique2Name:
                character.getPlaybookUniques().getWorkspace().setItemsCount(
                        character.getPlaybookUniques().getWorkspace().getItemsCount() + 1
                );

                List<String> newItems = new ArrayList<>();
                newItems.add(WORKSPACE_LIFE_SUPPORT_ITEM);
                newItems.addAll(character.getPlaybookUniques().getWorkspace().getWorkspaceItems());
                character.getPlaybookUniques().getWorkspace().setWorkspaceItems(newItems);
                break;
            default:
                // TODO: throw error
        }
    }
}
