package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.enums.*;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.AngelKit;
import com.mersiades.awcdata.models.uniques.BrainerGear;
import com.mersiades.awcdata.models.uniques.CustomWeapons;
import com.mersiades.awcdata.repositories.CharacterRepository;
import com.mersiades.awcdata.services.*;
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
        List<Look> angelLooks = lookService.findAllByPlaybookType(Playbooks.ANGEL).collectList().block();
        assert angelLooks != null;

        Look genderLook = angelLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.GENDER)).findFirst().orElseThrow();
        Look clothesLook = angelLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.CLOTHES)).findFirst().orElseThrow();
        Look bodyLook = angelLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.BODY)).findFirst().orElseThrow();
        Look faceLook = angelLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.FACE)).findFirst().orElseThrow();
        Look eyesLook = angelLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.EYES)).findFirst().orElseThrow();

        StatsOption angelStatsOption = statsOptionService.findAllByPlaybookType(Playbooks.ANGEL).blockFirst();
        assert angelStatsOption != null;

        CharacterStat angelCool = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.COOL).value(angelStatsOption.getCOOL()).isHighlighted(false).build();
        CharacterStat angelHard = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.HARD).value(angelStatsOption.getHARD()).isHighlighted(true).build();
        CharacterStat angelHot = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.HOT).value(angelStatsOption.getHOT()).isHighlighted(true).build();
        CharacterStat angelSharp = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.SHARP).value(angelStatsOption.getSHARP()).isHighlighted(false).build();
        CharacterStat angelWeird = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.WEIRD).value(angelStatsOption.getWEIRD()).isHighlighted(false).build();

        AngelKit angelKit = AngelKit.builder().id(UUID.randomUUID().toString())
                .hasSupplier(false)
                .stock(2).build();

        PlaybookUnique angelUnique = PlaybookUnique.builder()
                .id(UUID.randomUUID().toString())
                .type(UniqueType.ANGEL_KIT)
                .angelKit(angelKit)
                .build();

        List<Move> angelMoves = moveService.findAllByPlaybookAndKind(Playbooks.ANGEL, MoveKinds.CHARACTER)
                .filter(move -> move.getName().equals("ANGEL SPECIAL") ||
                        move.getName().equals("SIXTH SENSE") ||
                        move.getName().equals("HEALING TOUCH"))
                .collectList().block();

        assert angelMoves != null;
        List<CharacterMove> characterMoves = angelMoves
                .stream().map(move -> CharacterMove.createFromMove(move, true)).collect(Collectors.toList());

        // -------------------------------- Set up John's Battlebabe ----------------------------------- //
        List<Look> battlebabeLooks = lookService.findAllByPlaybookType(Playbooks.BATTLEBABE).collectList().block();
        assert battlebabeLooks != null;

        Look genderLookBattlebabe = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.GENDER)).findFirst().orElseThrow();
        Look clothesLookBattlebabe = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.CLOTHES)).findFirst().orElseThrow();
        Look bodyLookBattlebabe = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.BODY)).findFirst().orElseThrow();
        Look faceLookBattlebabe = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.FACE)).findFirst().orElseThrow();
        Look eyesLookBattlebabe = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.EYES)).findFirst().orElseThrow();

        StatsOption battlebabeStatsOption = statsOptionService.findAllByPlaybookType(Playbooks.BATTLEBABE).blockFirst();
        assert battlebabeStatsOption != null;

        CharacterStat battlebabeCool = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.COOL).value(battlebabeStatsOption.getCOOL()).isHighlighted(false).build();
        CharacterStat battlebabeHard = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.HARD).value(battlebabeStatsOption.getHARD()).isHighlighted(true).build();
        CharacterStat battlebabeHot = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.HOT).value(battlebabeStatsOption.getHOT()).isHighlighted(true).build();
        CharacterStat battlebabeSharp = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.SHARP).value(battlebabeStatsOption.getSHARP()).isHighlighted(false).build();
        CharacterStat battlebabeWeird = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.WEIRD).value(battlebabeStatsOption.getWEIRD()).isHighlighted(false).build();

        CustomWeapons customWeapons = CustomWeapons.builder()
                .id(UUID.randomUUID().toString())
                .weapons(List.of("antique rifle (2-harm, load, valuable", "Ornate staff (1-harm, valuable)"))
                .build();


        PlaybookUnique battlebabeUnique = PlaybookUnique.builder()
                .id(UUID.randomUUID().toString())
                .type(UniqueType.CUSTOM_WEAPONS)
                .customWeapons(customWeapons)
                .build();

        List<Move> battlebabeMoves = moveService.findAllByPlaybookAndKind(Playbooks.BATTLEBABE, MoveKinds.CHARACTER)
                .filter(move -> move.getName().equals("BATTLEBABE SPECIAL") ||
                        move.getName().equals("DANGEROUS & SEXY") ||
                        move.getName().equals("PERFECT INSTINCTS"))
                .collectList().block();
        assert battlebabeMoves != null;

        List<CharacterMove> characterMoves2 = battlebabeMoves
                .stream().map(move -> CharacterMove.createFromMove(move, true)).collect(Collectors.toList());

        // -------------------------------- Set up Maya's Brainer ----------------------------------- //
        List<Look> brainerLooks = lookService.findAllByPlaybookType(Playbooks.BRAINER).collectList().block();
        assert brainerLooks != null;

        Look genderLookBrainer = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.GENDER)).findFirst().orElseThrow();
        Look clothesLookBrainer = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.CLOTHES)).findFirst().orElseThrow();
        Look bodyLookBrainer = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.BODY)).findFirst().orElseThrow();
        Look faceLookBrainer = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.FACE)).findFirst().orElseThrow();
        Look eyesLookBrainer = battlebabeLooks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.EYES)).findFirst().orElseThrow();

        StatsOption brainerStatsOption = statsOptionService.findAllByPlaybookType(Playbooks.BRAINER).blockFirst();
        assert brainerStatsOption != null;

        CharacterStat brainerCool = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.COOL).value(brainerStatsOption.getCOOL()).isHighlighted(false).build();
        CharacterStat brainerHard = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.HARD).value(brainerStatsOption.getHARD()).isHighlighted(true).build();
        CharacterStat brainerHot = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.HOT).value(brainerStatsOption.getHOT()).isHighlighted(true).build();
        CharacterStat brainerSharp = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.SHARP).value(brainerStatsOption.getSHARP()).isHighlighted(false).build();
        CharacterStat brainerWeird = CharacterStat.builder().id(UUID.randomUUID().toString())
                .stat(Stats.WEIRD).value(brainerStatsOption.getWEIRD()).isHighlighted(false).build();

        BrainerGear brainerGear = BrainerGear.builder()
                .id(UUID.randomUUID().toString())
                .brainerGear(List.of("brain relay", "violation glove"))
                .build();

        PlaybookUnique brainerUnique = PlaybookUnique.builder()
                .id(UUID.randomUUID().toString())
                .type(UniqueType.BRAINER_GEAR)
                .brainerGear(brainerGear)
                .build();

        List<Move> brainerMoves = moveService.findAllByPlaybookAndKind(Playbooks.BRAINER, MoveKinds.CHARACTER)
                .filter(move -> move.getName().equals("BRAINER SPECIAL") ||
                        move.getName().equals("DEEP BRAIN SCAN") ||
                        move.getName().equals("PRETERNATURAL BRAIN ATTUNEMENT"))
                .collectList().block();
        assert brainerMoves != null;

        List<CharacterMove> characterMoves3 = brainerMoves
                .stream().map(move -> CharacterMove.createFromMove(move, true)).collect(Collectors.toList());

        // -------------------------------- Set up Ahmad's Angel ----------------------------------- //


        List<Move> angelMoves2 = moveService.findAllByPlaybookAndKind(Playbooks.ANGEL, MoveKinds.CHARACTER)
                .filter(move -> move.getName().equals("INFIRMARY") ||
                        move.getName().equals("PROFESSIONAL COMPASSION") ||
                        move.getName().equals("TOUCHED BY DEATH"))
                .collectList().block();
        assert angelMoves2 != null;

        List<CharacterMove> characterMoves4 = angelMoves2
                .stream().map(move -> CharacterMove.createFromMove(move, true)).collect(Collectors.toList());

        // -------------------------------- Create Sara's Angel ----------------------------------- //
        Character mockCharacter1 = Character.builder()
                .name("Doc")
                .playbook(Playbooks.ANGEL)
                .looks(List.of(genderLook, clothesLook, bodyLook, faceLook, eyesLook))
                .gear(List.of("Shotgun", "Rusty screwdriver"))
                .statsBlock(List.of(angelCool, angelHard, angelHot, angelSharp, angelWeird))
                .barter(2)
                .playbookUnique(angelUnique)
                .characterMoves(characterMoves)
                .hasCompletedCharacterCreation(true)
                .build();

        // -------------------------------- Create John's Battlebabe ----------------------------------- //
        Character mockCharacter2 = Character.builder()
                .name("Scarlet")
                .playbook(Playbooks.BATTLEBABE)
                .looks(List.of(genderLookBattlebabe, clothesLookBattlebabe, bodyLookBattlebabe, faceLookBattlebabe, eyesLookBattlebabe))
                .gear(List.of("Black leather boots", "Broken motorcycle helmet"))
                .statsBlock(List.of(battlebabeCool, battlebabeHard, battlebabeHot, battlebabeSharp, battlebabeWeird))
                .barter(2)
                .playbookUnique(battlebabeUnique)
                .characterMoves(characterMoves2)
                .hasCompletedCharacterCreation(true)
                .build();

        // -------------------------------- Create Maya's Brainer ----------------------------------- //
        Character mockCharacter3 = Character.builder()
                .name("Smith")
                .playbook(Playbooks.BRAINER)
                .looks(List.of(genderLookBrainer, bodyLookBrainer, clothesLookBrainer, faceLookBrainer, eyesLookBrainer))
                .gear(List.of("Sharp kitchen knife", "Wireless radio"))
                .statsBlock(List.of(brainerCool, brainerHard, brainerHot, brainerSharp, brainerWeird))
                .barter(2)
                .playbookUnique(brainerUnique)
                .characterMoves(characterMoves3)
                .hasCompletedCharacterCreation(true)
                .build();

        // -------------------------------- Create Ahmad's Angel ----------------------------------- //
        Character mockCharacter4 = Character.builder()
                .name("Nee")
                .playbook(Playbooks.ANGEL)
                .looks(List.of(genderLook, clothesLook, bodyLook, faceLook, eyesLook))
                .gear(List.of("Shiny scalpel", "Beatles LP collection"))
                .statsBlock(List.of(angelCool, angelHard, angelHot, angelSharp, angelWeird))
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
