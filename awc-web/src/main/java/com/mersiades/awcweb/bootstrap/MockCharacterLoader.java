package com.mersiades.awcweb.bootstrap;

import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.models.uniquecreators.BikeCreator;
import com.mersiades.awccontent.models.uniquecreators.CarCreator;
import com.mersiades.awccontent.services.LookService;
import com.mersiades.awccontent.services.MoveService;
import com.mersiades.awccontent.services.StatsOptionService;
import com.mersiades.awccontent.services.PlaybookCreatorService;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.AngelKit;
import com.mersiades.awcdata.models.uniques.BrainerGear;
import com.mersiades.awcdata.models.uniques.CustomWeapons;
import com.mersiades.awcdata.models.uniques.Vehicle;
import com.mersiades.awcdata.repositories.CharacterRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
@Order(value = 2)
@Profile("dev")
public class MockCharacterLoader implements CommandLineRunner {

    final String SARA_AS_PLAYER_ID = "be6b09af-9c96-452a-8b05-922be820c88f";
    final String JOHN_AS_PLAYER_ID = "5ffe67b72e21523778660910)";
    final String MAYA_AS_PLAYER_ID = "5ffe67b72e21523778660911)";
    final String AHMAD_AS_PLAYER_ID = "5ffe67b72e21523778660912)";

    private final GameRoleService gameRoleService;
    private final LookService lookService;
    private final CharacterService characterService;
    private final StatsOptionService statsOptionService;
    private final MoveService moveService;
    private final PlaybookCreatorService playbookCreatorService;

    @Autowired
    CharacterRepository characterRepository;

    public MockCharacterLoader(GameRoleService gameRoleService,
                               LookService lookService,
                               CharacterService characterService,
                               StatsOptionService statsOptionService,
                               MoveService moveService, PlaybookCreatorService playbookCreatorService) {
        this.gameRoleService = gameRoleService;
        this.lookService = lookService;
        this.characterService = characterService;
        this.statsOptionService = statsOptionService;
        this.moveService = moveService;
        this.playbookCreatorService = playbookCreatorService;
    }

    @Override
    public void run(String... args) {
        loadMockCharacters();
        loadHx();

        System.out.println("Character count: " + Objects.requireNonNull(characterRepository.count().block()).toString());
    }

    private void loadMockCharacters() {
        GameRole saraAsPlayer = gameRoleService.findById(SARA_AS_PLAYER_ID).block();
        assert saraAsPlayer != null;
        GameRole johnAsPlayer = gameRoleService.findById(JOHN_AS_PLAYER_ID).block();
        assert johnAsPlayer != null;
        GameRole mayaAsPlayer = gameRoleService.findById(MAYA_AS_PLAYER_ID).block();
        assert mayaAsPlayer != null;
        GameRole ahmadAsPlayer = gameRoleService.findById(AHMAD_AS_PLAYER_ID).block();
        assert ahmadAsPlayer != null;

        CharacterHarm harm = CharacterHarm.builder()
                .hasChangedPlaybook(false)
                .hasComeBackHard(false)
                .hasComeBackWeird(false)
                .hasDied(false)
                .isStabilized(false)
                .value(0)
                .build();

        // -------------------------------- Set up Sara's Angel ----------------------------------- //
        List<Look> angelLooks = getLooks(PlaybookType.ANGEL);

        StatsBlock angelStatsBlock1 = getStatsBlock(PlaybookType.ANGEL);

        List<Move> angelKitMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.UNIQUE)
                .collectList().block();

        AngelKit angelKit = AngelKit.builder().id(UUID.randomUUID().toString())
                .hasSupplier(false)
                .angelKitMoves(angelKitMoves)
                .stock(2).build();

        PlaybookUnique angelUnique = PlaybookUnique.builder()
                .id(UUID.randomUUID().toString())
                .type(UniqueType.ANGEL_KIT)
                .angelKit(angelKit)
                .build();

        List<Move> angelMoves = moveService.findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.CHARACTER)
                .filter(move -> move.getName().equals("SIXTH SENSE") ||
                        move.getName().equals("HEALING TOUCH"))
                .collectList().block();
        assert angelMoves != null;

        List<Move> angelDefaultMoves = moveService.
                findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.DEFAULT_CHARACTER).collectList().block();
        assert angelDefaultMoves != null;

        List<CharacterMove> characterMoves = createAndMergeCharacterMoves(angelMoves, angelDefaultMoves);

        // -------------------------------- Set up John's Battlebabe ----------------------------------- //
        List<Look> battlebabeLooks = getLooks(PlaybookType.BATTLEBABE);

        StatsBlock battlebabeStatsBlock = getStatsBlock(PlaybookType.BATTLEBABE);

        CustomWeapons customWeapons = CustomWeapons.builder()
                .id(UUID.randomUUID().toString())
                .weapons(List.of("antique rifle (2-harm, load, valuable", "Ornate staff (1-harm, valuable)"))
                .build();

        PlaybookUnique battlebabeUnique = PlaybookUnique.builder()
                .id(UUID.randomUUID().toString())
                .type(UniqueType.CUSTOM_WEAPONS)
                .customWeapons(customWeapons)
                .build();

        List<Move> battlebabeMoves = moveService.findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.CHARACTER)
                .filter(move -> move.getName().equals("DANGEROUS & SEXY") ||
                        move.getName().equals("PERFECT INSTINCTS"))
                .collectList().block();
        assert battlebabeMoves != null;

        List<Move> battlebabeDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.DEFAULT_CHARACTER).collectList().block();
        assert battlebabeDefaultMoves != null;

        List<CharacterMove> characterMoves2 = createAndMergeCharacterMoves(battlebabeMoves, battlebabeDefaultMoves);

        // -------------------------------- Set up Maya's Brainer ----------------------------------- //
        List<Look> brainerLooks = getLooks(PlaybookType.BRAINER);

        StatsBlock brainerStatsBlock = getStatsBlock(PlaybookType.BRAINER);

        BrainerGear brainerGear = BrainerGear.builder()
                .id(UUID.randomUUID().toString())
                .brainerGear(List.of("brain relay", "violation glove"))
                .build();

        PlaybookUnique brainerUnique = PlaybookUnique.builder()
                .id(UUID.randomUUID().toString())
                .type(UniqueType.BRAINER_GEAR)
                .brainerGear(brainerGear)
                .build();

        List<Move> brainerMoves = moveService.findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.CHARACTER)
                .filter(move -> move.getName().equals("DEEP BRAIN SCAN") ||
                        move.getName().equals("PRETERNATURAL BRAIN ATTUNEMENT"))
                .collectList().block();
        assert brainerMoves != null;

        List<Move> brainerDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.DEFAULT_CHARACTER).collectList().block();
        assert brainerDefaultMoves != null;

        List<CharacterMove> characterMoves3 = createAndMergeCharacterMoves(brainerMoves, brainerDefaultMoves);

        // -------------------------------- Set up Ahmad's Driver ----------------------------------- //
        List<Look> driverLooks = getLooks(PlaybookType.DRIVER);

        StatsBlock driverStatsBlock = getStatsBlock(PlaybookType.DRIVER);

        List<Move> driverMoves = moveService.findAllByPlaybookAndKind(PlaybookType.DRIVER, MoveType.CHARACTER)
                .filter(move -> move.getName().equals("EYE ON THE DOOR") ||
                        move.getName().equals("COLLECTOR"))
                .collectList().block();
        assert driverMoves != null;

        List<Move> driverDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.DRIVER, MoveType.DEFAULT_CHARACTER).collectList().block();
        assert driverDefaultMoves != null;

        List<CharacterMove> characterMoves4 = createAndMergeCharacterMoves(driverMoves, driverDefaultMoves);

        PlaybookCreator driverCreator = playbookCreatorService.findByPlaybookType(PlaybookType.DRIVER).block();
        assert driverCreator != null;
        CarCreator carCreator = driverCreator.getPlaybookUniqueCreator().getCarCreator();
        BikeCreator bikeCreator = driverCreator.getPlaybookUniqueCreator().getBikeCreator();


        Vehicle car1 = Vehicle.builder()
                .id(UUID.randomUUID().toString())
                // SMALL frame
                .vehicleFrame(carCreator.getFrames().get(1))
                // +1speed, +1armor
                .battleOptions(List.of(carCreator.getBattleOptions().get(0), carCreator.getBattleOptions().get(3)))
                .massive(1)
                .speed(1)
                .armor(1)
                .handling(0)
                .build();

        Vehicle car2 = Vehicle.builder()
                .id(UUID.randomUUID().toString())
                // LARGE frame
                .vehicleFrame(carCreator.getFrames().get(3))
                // +1massive, +1armor
                .battleOptions(List.of(carCreator.getBattleOptions().get(2), carCreator.getBattleOptions().get(3)))
                .massive(4)
                .speed(0)
                .armor(1)
                .handling(0)
                .build();

        Vehicle bike = Vehicle.builder()
                .id(UUID.randomUUID().toString())
                // BIKE frame
                .vehicleFrame(bikeCreator.getFrame())
                // +1speed
                .battleOptions(List.of(bikeCreator.getBattleOptions().get(0)))
                .massive(0)
                .speed(1)
                .armor(0)
                .handling(0)
                .build();

        PlaybookUnique driverPlaybookUnique = PlaybookUnique.builder()
                .id(UUID.randomUUID().toString())
                .vehicles(List.of(car1, car2, bike))
                .build();

        // -------------------------------- Create Sara's Angel ----------------------------------- //
        Character mockCharacter1 = Character.builder()
                .name("Doc")
                .playbook(PlaybookType.ANGEL)
                .looks(angelLooks)
                .gear(List.of("Shotgun", "Rusty screwdriver"))
                .statsBlock(angelStatsBlock1)
                .barter(2)
                .playbookUnique(angelUnique)
                .characterMoves(characterMoves)
                .hasCompletedCharacterCreation(true)
                .build();

        // -------------------------------- Create John's Battlebabe ----------------------------------- //
        Character mockCharacter2 = Character.builder()
                .name("Scarlet")
                .playbook(PlaybookType.BATTLEBABE)
                .looks(battlebabeLooks)
                .gear(List.of("Black leather boots", "Broken motorcycle helmet"))
                .statsBlock(battlebabeStatsBlock)
                .barter(2)
                .playbookUnique(battlebabeUnique)
                .characterMoves(characterMoves2)
                .hasCompletedCharacterCreation(true)
                .build();

        // -------------------------------- Create Maya's Brainer ----------------------------------- //
        Character mockCharacter3 = Character.builder()
                .name("Smith")
                .playbook(PlaybookType.BRAINER)
                .looks(brainerLooks)
                .gear(List.of("Sharp kitchen knife", "Wireless radio"))
                .statsBlock(brainerStatsBlock)
                .barter(2)
                .playbookUnique(brainerUnique)
                .characterMoves(characterMoves3)
                .hasCompletedCharacterCreation(true)
                .build();

        // -------------------------------- Create Ahmad's Driver ----------------------------------- //
        Character mockCharacter4 = Character.builder()
                .name("Phoenix")
                .playbook(PlaybookType.DRIVER)
                .looks(driverLooks)
                .gear(List.of("Leather jacket", ".38 revolver"))
                .statsBlock(driverStatsBlock)
                .barter(4)
                .playbookUnique(driverPlaybookUnique)
                .characterMoves(characterMoves4)
                .hasCompletedCharacterCreation(true)
                .build();

        // ----------------------- Add Characters to players and save ----------------------------- //

        saveCharacter(saraAsPlayer, harm, mockCharacter1);
        saveCharacter(johnAsPlayer, harm, mockCharacter2);
        saveCharacter(mayaAsPlayer, harm, mockCharacter3);
        saveCharacter(ahmadAsPlayer, harm, mockCharacter4);
    }

    private void loadHx() {
        GameRole saraAsPlayer = gameRoleService.findById(SARA_AS_PLAYER_ID).block();
        assert saraAsPlayer != null;
        GameRole johnAsPlayer = gameRoleService.findById(JOHN_AS_PLAYER_ID).block();
        assert johnAsPlayer != null;
        GameRole mayaAsPlayer = gameRoleService.findById(MAYA_AS_PLAYER_ID).block();
        assert mayaAsPlayer != null;
        GameRole ahmadAsPlayer = gameRoleService.findById(AHMAD_AS_PLAYER_ID).block();
        assert ahmadAsPlayer != null;

        Character doc = saraAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert doc != null;
        Character scarlet = johnAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert scarlet != null;
        Character smith = mayaAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert smith != null;
        Character nee = ahmadAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert nee != null;

        // ------------------------------ Doc's Hx --------------------------------- //
        HxStat doc1 = HxStat.builder().id(UUID.randomUUID().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(-2).build();

        HxStat doc2 = HxStat.builder().id(UUID.randomUUID().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(2).build();

        HxStat doc3 = HxStat.builder().id(UUID.randomUUID().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(3).build();
        doc.setHxBlock(List.of(doc1, doc2, doc3));

        // ------------------------------ Scarlet's Hx --------------------------------- //
        HxStat scarlet1 = HxStat.builder().id(UUID.randomUUID().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(-1).build();

        HxStat scarlet2 = HxStat.builder().id(UUID.randomUUID().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(3).build();

        HxStat scarlet3 = HxStat.builder().id(UUID.randomUUID().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(-1).build();
        scarlet.setHxBlock(List.of(scarlet1, scarlet2, scarlet3));

        // ------------------------------ Smith's Hx --------------------------------- //
        HxStat smith1 = HxStat.builder().id(UUID.randomUUID().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(2).build();

        HxStat smith2 = HxStat.builder().id(UUID.randomUUID().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(2).build();

        HxStat smith3 = HxStat.builder().id(UUID.randomUUID().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(3).build();
        smith.setHxBlock(List.of(smith1, smith2, smith3));

        // ------------------------------ Nee's Hx --------------------------------- //
        HxStat nee1 = HxStat.builder().id(UUID.randomUUID().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(-2).build();

        HxStat nee2 = HxStat.builder().id(UUID.randomUUID().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(2).build();

        HxStat nee3 = HxStat.builder().id(UUID.randomUUID().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(3).build();
        nee.setHxBlock(List.of(nee1, nee2, nee3));

        // ------------------------------ Save to db --------------------------------- //
        characterService.saveAll(Flux.just(doc, scarlet, smith, nee)).blockLast();
        gameRoleService.saveAll(Flux.just(saraAsPlayer, johnAsPlayer, mayaAsPlayer, ahmadAsPlayer)).blockLast();

    }

    private List<CharacterMove> createAndMergeCharacterMoves(List<Move> choiceMoves, List<Move> defaultMoves) {

        List<CharacterMove> characterMoves = choiceMoves
                .stream().map(move -> CharacterMove.createFromMove(move, true)).collect(Collectors.toList());

        List<CharacterMove> characterDefaultMoves = defaultMoves
                .stream().map(CharacterMove::createFromMove).collect(Collectors.toList());

        characterMoves.addAll(characterDefaultMoves);
        return characterMoves;
    }

    private List<Look> getLooks(PlaybookType playbookType) {
        List<Look> looks = lookService.findAllByPlaybookType(playbookType).collectList().block();
        assert looks != null;

        Look genderLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookType.GENDER)).findFirst().orElseThrow();
        Look clothesLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookType.CLOTHES)).findFirst().orElseThrow();
        Look bodyLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookType.BODY)).findFirst().orElseThrow();
        Look faceLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookType.FACE)).findFirst().orElseThrow();
        Look eyesLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookType.EYES)).findFirst().orElseThrow();

        return List.of(genderLook, bodyLook, clothesLook, faceLook, eyesLook);
    }

    private StatsBlock getStatsBlock(PlaybookType playbookType) {
        StatsOption statsOption = statsOptionService.findAllByPlaybookType(playbookType).blockFirst();
        assert statsOption != null;

        CharacterStat cool = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.COOL).value(statsOption.getCOOL()).isHighlighted(false).build();
        CharacterStat hard = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.HARD).value(statsOption.getHARD()).isHighlighted(true).build();
        CharacterStat hot = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.HOT).value(statsOption.getHOT()).isHighlighted(true).build();
        CharacterStat sharp = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.SHARP).value(statsOption.getSHARP()).isHighlighted(false).build();
        CharacterStat weird = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.WEIRD).value(statsOption.getWEIRD()).isHighlighted(false).build();

        return StatsBlock.builder().id(UUID.randomUUID().toString())
                .statsOptionId(statsOption.getId())
                .stats(List.of(cool, hard, hot, sharp, weird))
                .build();
    }

    private void saveCharacter(GameRole gameRole, CharacterHarm harm, Character character) {
        if (gameRole.getCharacters().size() == 0) {
            harm.setId(UUID.randomUUID().toString());
            character.setHarm(harm);
            gameRole.getCharacters().add(character);
            characterService.save(character).block();
            gameRoleService.save(gameRole).block();
        }
    }
}
