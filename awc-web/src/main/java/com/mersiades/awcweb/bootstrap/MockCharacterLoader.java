package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.AngelKit;
import com.mersiades.awcdata.models.uniques.BrainerGear;
import com.mersiades.awcdata.models.uniques.CustomWeapons;
import com.mersiades.awcdata.repositories.CharacterRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.Look;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.models.StatsOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import com.mersiades.awccontent.services.LookService;
import com.mersiades.awccontent.services.MoveService;
import com.mersiades.awccontent.services.StatsOptionService;

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

    @Autowired
    CharacterRepository characterRepository;

    public MockCharacterLoader(GameRoleService gameRoleService,
                               LookService lookService,
                               CharacterService characterService,
                               StatsOptionService statsOptionService,
                               MoveService moveService) {
        this.gameRoleService = gameRoleService;
        this.lookService = lookService;
        this.characterService = characterService;
        this.statsOptionService = statsOptionService;
        this.moveService = moveService;
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
        List<Look> angelLooks = lookService.findAllByPlaybookType(PlaybookType.ANGEL).collectList().block();
        assert angelLooks != null;

        Look genderLook = angelLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.GENDER)).findFirst().orElseThrow();
        Look clothesLook = angelLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.CLOTHES)).findFirst().orElseThrow();
        Look bodyLook = angelLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.BODY)).findFirst().orElseThrow();
        Look faceLook = angelLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.FACE)).findFirst().orElseThrow();
        Look eyesLook = angelLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.EYES)).findFirst().orElseThrow();

        StatsOption angelStatsOption = statsOptionService.findAllByPlaybookType(PlaybookType.ANGEL).blockFirst();
        assert angelStatsOption != null;

        CharacterStat angelCool = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.COOL).value(angelStatsOption.getCOOL()).isHighlighted(false).build();
        CharacterStat angelHard = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.HARD).value(angelStatsOption.getHARD()).isHighlighted(true).build();
        CharacterStat angelHot = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.HOT).value(angelStatsOption.getHOT()).isHighlighted(true).build();
        CharacterStat angelSharp = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.SHARP).value(angelStatsOption.getSHARP()).isHighlighted(false).build();
        CharacterStat angelWeird = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.WEIRD).value(angelStatsOption.getWEIRD()).isHighlighted(false).build();

        StatsBlock angelStatsBlock1 = StatsBlock.builder().id(UUID.randomUUID().toString())
                .statsOptionId(angelStatsOption.getId())
                .stats(List.of(angelCool, angelHard, angelHot, angelSharp, angelWeird))
                .build();

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


        List<CharacterMove> characterMoves = angelMoves
                .stream().map(move -> CharacterMove.createFromMove(move, true)).collect(Collectors.toList());

        List<CharacterMove> characterDefaultMoves = angelDefaultMoves
                .stream().map(CharacterMove::createFromMove).collect(Collectors.toList());

        characterMoves.addAll(characterDefaultMoves);

        // -------------------------------- Set up John's Battlebabe ----------------------------------- //
        List<Look> battlebabeLooks = lookService.findAllByPlaybookType(PlaybookType.BATTLEBABE).collectList().block();
        assert battlebabeLooks != null;

        Look genderLookBattlebabe = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.GENDER)).findFirst().orElseThrow();
        Look clothesLookBattlebabe = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.CLOTHES)).findFirst().orElseThrow();
        Look bodyLookBattlebabe = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.BODY)).findFirst().orElseThrow();
        Look faceLookBattlebabe = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.FACE)).findFirst().orElseThrow();
        Look eyesLookBattlebabe = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.EYES)).findFirst().orElseThrow();

        StatsOption battlebabeStatsOption = statsOptionService.findAllByPlaybookType(PlaybookType.BATTLEBABE).blockFirst();
        assert battlebabeStatsOption != null;

        CharacterStat battlebabeCool = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.COOL).value(battlebabeStatsOption.getCOOL()).isHighlighted(false).build();
        CharacterStat battlebabeHard = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.HARD).value(battlebabeStatsOption.getHARD()).isHighlighted(true).build();
        CharacterStat battlebabeHot = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.HOT).value(battlebabeStatsOption.getHOT()).isHighlighted(true).build();
        CharacterStat battlebabeSharp = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.SHARP).value(battlebabeStatsOption.getSHARP()).isHighlighted(false).build();
        CharacterStat battlebabeWeird = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.WEIRD).value(battlebabeStatsOption.getWEIRD()).isHighlighted(false).build();

        StatsBlock battlebabeStatsBlock = StatsBlock.builder().id(UUID.randomUUID().toString())
                .statsOptionId(battlebabeStatsOption.getId())
                .stats(List.of(battlebabeCool, battlebabeHard, battlebabeHot, battlebabeSharp, battlebabeWeird))
                .build();

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

        List<CharacterMove> characterMoves2 = battlebabeMoves
                .stream().map(move -> CharacterMove.createFromMove(move, true)).collect(Collectors.toList());

        List<CharacterMove> characterDefaultMoves2 = battlebabeDefaultMoves
                .stream().map(CharacterMove::createFromMove).collect(Collectors.toList());

        characterMoves2.addAll(characterDefaultMoves2);

        // -------------------------------- Set up Maya's Brainer ----------------------------------- //
        List<Look> brainerLooks = lookService.findAllByPlaybookType(PlaybookType.BRAINER).collectList().block();
        assert brainerLooks != null;

        Look genderLookBrainer = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.GENDER)).findFirst().orElseThrow();
        Look clothesLookBrainer = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.CLOTHES)).findFirst().orElseThrow();
        Look bodyLookBrainer = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.BODY)).findFirst().orElseThrow();
        Look faceLookBrainer = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.FACE)).findFirst().orElseThrow();
        Look eyesLookBrainer = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookType.EYES)).findFirst().orElseThrow();

        StatsOption brainerStatsOption = statsOptionService.findAllByPlaybookType(PlaybookType.BRAINER).blockFirst();
        assert brainerStatsOption != null;

        CharacterStat brainerCool = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.COOL).value(brainerStatsOption.getCOOL()).isHighlighted(false).build();
        CharacterStat brainerHard = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.HARD).value(brainerStatsOption.getHARD()).isHighlighted(true).build();
        CharacterStat brainerHot = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.HOT).value(brainerStatsOption.getHOT()).isHighlighted(true).build();
        CharacterStat brainerSharp = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.SHARP).value(brainerStatsOption.getSHARP()).isHighlighted(false).build();
        CharacterStat brainerWeird = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(StatType.WEIRD).value(brainerStatsOption.getWEIRD()).isHighlighted(false).build();

        StatsBlock brainerStatsBlock = StatsBlock.builder().id(UUID.randomUUID().toString())
                .statsOptionId(brainerStatsOption.getId())
                .stats(List.of(brainerCool, brainerHard, brainerHot, brainerSharp, brainerWeird))
                .build();

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

        List<CharacterMove> characterMoves3 = brainerMoves
                .stream().map(move -> CharacterMove.createFromMove(move, true)).collect(Collectors.toList());

        List<CharacterMove> characterDefaultMoves3 = brainerMoves
                .stream().map(CharacterMove::createFromMove).collect(Collectors.toList());

        characterMoves3.addAll(characterDefaultMoves3);

        // -------------------------------- Set up Ahmad's Angel ----------------------------------- //
        List<Move> angelMoves2 = moveService.findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.CHARACTER)
                .filter(move -> move.getName().equals("PROFESSIONAL COMPASSION") ||
                        move.getName().equals("TOUCHED BY DEATH"))
                .collectList().block();
        assert angelMoves2 != null;

        List<Move> angelDefaultMoves2 = moveService
                .findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.DEFAULT_CHARACTER).collectList().block();
        assert angelDefaultMoves2 != null;

        List<CharacterMove> characterMoves4 = angelMoves2
                .stream().map(move -> CharacterMove.createFromMove(move, true)).collect(Collectors.toList());

        List<CharacterMove> characterDefaultMoves4 = angelMoves2
                .stream().map(CharacterMove::createFromMove).collect(Collectors.toList());

        characterMoves4.addAll(characterDefaultMoves4);

        // -------------------------------- Create Sara's Angel ----------------------------------- //
        Character mockCharacter1 = Character.builder()
                .name("Doc")
                .playbook(PlaybookType.ANGEL)
                .looks(List.of(genderLook, clothesLook, bodyLook, faceLook, eyesLook))
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
                .looks(List.of(genderLookBattlebabe, clothesLookBattlebabe, bodyLookBattlebabe, faceLookBattlebabe, eyesLookBattlebabe))
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
                .looks(List.of(genderLookBrainer, bodyLookBrainer, clothesLookBrainer, faceLookBrainer, eyesLookBrainer))
                .gear(List.of("Sharp kitchen knife", "Wireless radio"))
                .statsBlock(brainerStatsBlock)
                .barter(2)
                .playbookUnique(brainerUnique)
                .characterMoves(characterMoves3)
                .hasCompletedCharacterCreation(true)
                .build();

        // -------------------------------- Create Ahmad's Angel ----------------------------------- //
        Character mockCharacter4 = Character.builder()
                .name("Nee")
                .playbook(PlaybookType.ANGEL)
                .looks(List.of(genderLook, clothesLook, bodyLook, faceLook, eyesLook))
                .gear(List.of("Shiny scalpel", "Beatles LP collection"))
                .statsBlock(angelStatsBlock1)
                .barter(2)
                .playbookUnique(angelUnique)
                .characterMoves(characterMoves4)
                .hasCompletedCharacterCreation(true)
                .build();

        // ----------------------- Add Characters to players and save ----------------------------- //

        if (saraAsPlayer.getCharacters().size() == 0) {
            harm.setId(UUID.randomUUID().toString());
            mockCharacter1.setHarm(harm);
            saraAsPlayer.getCharacters().add(mockCharacter1);
            characterService.save(mockCharacter1).block();
            gameRoleService.save(saraAsPlayer).block();
        }

        if (johnAsPlayer.getCharacters().size() == 0) {
            harm.setId(UUID.randomUUID().toString());
            mockCharacter2.setHarm(harm);
            johnAsPlayer.getCharacters().add(mockCharacter2);
            characterService.save(mockCharacter2).block();
            gameRoleService.save(johnAsPlayer).block();
        }

        if (mayaAsPlayer.getCharacters().size() == 0) {
            harm.setId(UUID.randomUUID().toString());
            mockCharacter3.setHarm(harm);
            mayaAsPlayer.getCharacters().add(mockCharacter3);
            characterService.save(mockCharacter3).block();
            gameRoleService.save(mayaAsPlayer).block();
        }

        if (ahmadAsPlayer.getCharacters().size() == 0) {
            harm.setId(UUID.randomUUID().toString());
            mockCharacter4.setHarm(harm);
            ahmadAsPlayer.getCharacters().add(mockCharacter4);
            characterService.save(mockCharacter4).block();
            gameRoleService.save(ahmadAsPlayer).block();
        }
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
}
