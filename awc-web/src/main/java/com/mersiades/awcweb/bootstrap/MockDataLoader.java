package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.enums.*;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.AngelKit;
import com.mersiades.awcdata.models.uniques.BrainerGear;
import com.mersiades.awcdata.models.uniques.CustomWeapons;
import com.mersiades.awcdata.repositories.*;
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
@Order(value = 1)
@Profile("dev")
public class MockDataLoader implements CommandLineRunner {

    private final GameService gameService;
    private final GameRoleService gameRoleService;
    private final ThreatService threatService;
    private final NpcService npcService;
    private final UserService userService;
    private final LookService lookService;
    private final CharacterService characterService;
    private final StatsOptionService statsOptionService;
    private final MoveService moveService;

    final String KEYCLOAK_ID_1 = System.getenv("DAVE_ID");
    final String KEYCLOAK_DISPLAY_NAME_1 = "dave";
    final String KEYCLOAK_EMAIL_1 = "dave@email.com";
    final String KEYCLOAK_ID_2 = System.getenv("SARA_ID");
    final String KEYCLOAK_DISPLAY_NAME_2 = "sara";
    final String KEYCLOAK_EMAIL_2 = "sara@email.com";
    final String KEYCLOAK_ID_3 = System.getenv("JOHN_ID");
    final String KEYCLOAK_DISPLAY_NAME_3 = "john";
    final String KEYCLOAK_EMAIL_3 = "john@email.com";
    final String KEYCLOAK_ID_4 = System.getenv("MAYA_ID");
    final String KEYCLOAK_DISPLAY_NAME_4 = "maya";
    final String KEYCLOAK_EMAIL_4 = "maya@email.com";
    final String KEYCLOAK_ID_5 = System.getenv("AHMAD_ID");
    final String KEYCLOAK_DISPLAY_NAME_5 = "ahmad";
    final String KEYCLOAK_EMAIL_5 = "ahmad@email.com";
    final String MOCK_GAME_1_ID = "0ca6cc54-77a5-4d6e-ba2e-ee1543d6a249";
    final String MOCK_GAME_2_ID = "ecb645d2-06d3-46dc-ad7f-20bbd167085d";
    final String DAVE_AS_PLAYER_ID = "2a7aba8d-f6e8-4880-8021-99809c800acc";
    final String SARA_AS_PLAYER_ID = "be6b09af-9c96-452a-8b05-922be820c88f";
    final String JOHN_AS_PLAYER_ID = "5ffe67b72e21523778660910)";
    final String MAYA_AS_PLAYER_ID = "5ffe67b72e21523778660911)";
    final String AHMAD_AS_PLAYER_ID = "5ffe67b72e21523778660912)";

    @Autowired
    UserRepository userRepository;

    @Autowired
    ThreatRepository threatRepository;

    @Autowired
    NpcRepository npcRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameRoleRepository gameRoleRepository;

    public MockDataLoader(GameService gameService,
                          GameRoleService gameRoleService,
                          ThreatService threatService,
                          NpcService npcService,
                          UserService userService,
                          LookService lookService,
                          CharacterService characterService, StatsOptionService statsOptionService, MoveService moveService) {
        this.gameService = gameService;
        this.gameRoleService = gameRoleService;
        this.threatService = threatService;
        this.npcService = npcService;
        this.userService = userService;
        this.lookService = lookService;
        this.characterService = characterService;
        this.statsOptionService = statsOptionService;
        this.moveService = moveService;
    }

    @Override
    public void run(String... args) {
        loadMockData();
        loadMockCharacters();
        loadHx();

        System.out.println("Character count: " + Objects.requireNonNull(characterRepository.count().block()).toString());
        System.out.println("Game count: " + Objects.requireNonNull(gameRepository.count().block()).toString());
        System.out.println("GameRole count: " + Objects.requireNonNull(gameRoleRepository.count().block()).toString());
        System.out.println("Npc count: " + Objects.requireNonNull(npcRepository.count().block()).toString());
        System.out.println("Threat count: " + Objects.requireNonNull(threatRepository.count().block()).toString());
        System.out.println("User count: " + Objects.requireNonNull(userRepository.count().block()).toString());
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

    private void loadMockCharacters() {
        GameRole saraAsPlayer = gameRoleService.findById(SARA_AS_PLAYER_ID).block();
        assert saraAsPlayer != null;
        GameRole johnAsPlayer = gameRoleService.findById(JOHN_AS_PLAYER_ID).block();
        assert johnAsPlayer != null;
        GameRole mayaAsPlayer = gameRoleService.findById(MAYA_AS_PLAYER_ID).block();
        assert mayaAsPlayer != null;
        GameRole ahmadAsPlayer = gameRoleService.findById(AHMAD_AS_PLAYER_ID).block();
        assert ahmadAsPlayer != null;

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
                // hxBlock
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
                // hxBlock
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
                // hxBlock
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
                // hxBlock
                .characterMoves(characterMoves4)
                .hasCompletedCharacterCreation(true)
                .build();

        // ----------------------- Add Characters to players and save ----------------------------- //

        if (saraAsPlayer.getCharacters().size() == 0) {
            saraAsPlayer.getCharacters().add(mockCharacter1);
            characterService.save(mockCharacter1).block();
            gameRoleService.save(saraAsPlayer).block();
        }

        if (johnAsPlayer.getCharacters().size() == 0) {
            johnAsPlayer.getCharacters().add(mockCharacter2);
            characterService.save(mockCharacter2).block();
            gameRoleService.save(johnAsPlayer).block();
        }

        if (mayaAsPlayer.getCharacters().size() == 0) {
            mayaAsPlayer.getCharacters().add(mockCharacter3);
            characterService.save(mockCharacter3).block();
            gameRoleService.save(mayaAsPlayer).block();
        }

        if (ahmadAsPlayer.getCharacters().size() == 0) {
            ahmadAsPlayer.getCharacters().add(mockCharacter4);
            characterService.save(mockCharacter4).block();
            gameRoleService.save(ahmadAsPlayer).block();
        }
    }

    private void loadMockData() {
        List<User> existingUsers = userRepository.findAll().collectList().block();
        assert existingUsers != null;
        if (existingUsers.isEmpty()) {

            // -------------------------------------- Set up mock Users -------------------------------------- //
            User mockUser1 = User.builder()
                    .id(KEYCLOAK_ID_1)
                    .displayName(KEYCLOAK_DISPLAY_NAME_1)
                    .email(KEYCLOAK_EMAIL_1).build();


            User mockUser2 = User.builder()
                    .id(KEYCLOAK_ID_2)
                    .displayName(KEYCLOAK_DISPLAY_NAME_2)
                    .email(KEYCLOAK_EMAIL_2).build();

            User mockUser3 = User.builder()
                    .id(KEYCLOAK_ID_3)
                    .displayName(KEYCLOAK_DISPLAY_NAME_3)
                    .email(KEYCLOAK_EMAIL_3).build();

            User mockUser4 = User.builder()
                    .id(KEYCLOAK_ID_4)
                    .displayName(KEYCLOAK_DISPLAY_NAME_4)
                    .email(KEYCLOAK_EMAIL_4).build();

            User mockUser5 = User.builder()
                    .id(KEYCLOAK_ID_5)
                    .displayName(KEYCLOAK_DISPLAY_NAME_5)
                    .email(KEYCLOAK_EMAIL_5).build();

            // ------------------------------ Set up mock Game 1 with Game Roles ----------------------------- //
            Game mockGame1 = Game.builder()
                    .id(MOCK_GAME_1_ID)
                    .name("Mock Game 1")
                    .commsApp("Zoom")
                    .commsUrl("https://zoom.com/somethingsomething?something=something&somethingelse=somethingelse")
                    .hasFinishedPreGame(false)
                    .build();

            GameRole daveAsMC = GameRole.builder().id(DAVE_AS_PLAYER_ID).role(Roles.MC).build();
            GameRole sarahAsPlayer = GameRole.builder().id(SARA_AS_PLAYER_ID).role(Roles.PLAYER).build();
            GameRole johnAsPlayer = GameRole.builder().id(JOHN_AS_PLAYER_ID).role(Roles.PLAYER).build();
            GameRole mayaAsPlayer = GameRole.builder().id(MAYA_AS_PLAYER_ID).role(Roles.PLAYER).build();
            GameRole ahmadAsPlayer = GameRole.builder().id(AHMAD_AS_PLAYER_ID).role(Roles.PLAYER).build();

            Npc mockNpc1 = Npc.builder().name("Vision").description("Badass truck; driver").build();
            Npc mockNpc2 = Npc.builder().name("Nbeke").build();

            Threat mockThreat1 = Threat.builder().name("Tum Tum").threatKind(Threats.WARLORD).impulse("Slaver: to own and sell people").build();
            Threat mockThreat2 = Threat.builder().name("Gnarly").threatKind(Threats.GROTESQUE).impulse("Cannibal: craves satiety and plenty").build();

            daveAsMC.getNpcs().add(mockNpc1);
            daveAsMC.getNpcs().add(mockNpc2);
            daveAsMC.getThreats().add(mockThreat1);
            daveAsMC.getThreats().add(mockThreat2);

            mockGame1.getGameRoles().add(daveAsMC);
            mockGame1.getGameRoles().add(sarahAsPlayer);
            mockGame1.getGameRoles().add(johnAsPlayer);
            mockGame1.getGameRoles().add(mayaAsPlayer);
            mockGame1.getGameRoles().add(ahmadAsPlayer);
            mockGame1.setMc(mockUser1);
            mockGame1.getPlayers().add(mockUser2);
            mockGame1.getPlayers().add(mockUser3);
            mockGame1.getPlayers().add(mockUser4);
            mockGame1.getPlayers().add(mockUser5);
            gameService.save(mockGame1).block();

            mockUser1.getGameRoles().add(daveAsMC);

            mockUser2.getGameRoles().add(sarahAsPlayer);
            mockUser3.getGameRoles().add(johnAsPlayer);
            mockUser4.getGameRoles().add(mayaAsPlayer);
            mockUser5.getGameRoles().add(ahmadAsPlayer);

            daveAsMC.setUser(mockUser1);
            daveAsMC.setGame(mockGame1);
            sarahAsPlayer.setGame(mockGame1);
            sarahAsPlayer.setUser(mockUser2);
            johnAsPlayer.setGame(mockGame1);
            johnAsPlayer.setUser(mockUser3);
            mayaAsPlayer.setGame(mockGame1);
            mayaAsPlayer.setUser(mockUser4);
            ahmadAsPlayer.setGame(mockGame1);
            ahmadAsPlayer.setUser(mockUser5);
            gameRoleService.saveAll(Flux.just(daveAsMC, sarahAsPlayer, johnAsPlayer, mayaAsPlayer, ahmadAsPlayer)).blockLast();

            threatService.saveAll(Flux.just(mockThreat1, mockThreat2)).blockLast();
            npcService.saveAll(Flux.just(mockNpc1, mockNpc2)).blockLast();

            // ------------------------------ Set up mock Game 2 with Game Roles ----------------------------- //
            Game mockGame2 = Game.builder()
                    .id(MOCK_GAME_2_ID)
                    .name("Mock Game 2")
                    .commsApp("Discord")
                    .commsUrl("https://discord.com/something")
                    .hasFinishedPreGame(false)
                    .build();

            GameRole daveAsPlayer = GameRole.builder().id(UUID.randomUUID().toString()).role(Roles.PLAYER).build();
            GameRole sarahAsMC = GameRole.builder().id(UUID.randomUUID().toString()).role(Roles.MC).build();

            Npc mockNpc3 = Npc.builder().name("Batty").description("Overly polite gun for hire").build();
            Npc mockNpc4 = Npc.builder().name("Farley").build();

            Threat mockThreat3 = Threat.builder().name("Fleece").threatKind(Threats.BRUTE).impulse("Hunting pack: to victimize anyone vulnerable").build();
            Threat mockThreat4 = Threat.builder().name("Wet Rot").threatKind(Threats.AFFLICTION).impulse("Condition: to expose people to danger").build();

            sarahAsMC.getNpcs().add(mockNpc3);
            sarahAsMC.getNpcs().add(mockNpc4);
            sarahAsMC.getThreats().add(mockThreat3);
            sarahAsMC.getThreats().add(mockThreat4);

            mockGame2.getGameRoles().add(daveAsPlayer);
            mockGame2.getGameRoles().add(sarahAsMC);
            mockGame2.setMc(mockUser2);
            mockGame2.getPlayers().add(mockUser1);
            gameService.save(mockGame2).block();

            mockUser1.getGameRoles().add(daveAsPlayer);
            mockUser2.getGameRoles().add(sarahAsMC);
            userService.saveAll(Flux.just(mockUser1, mockUser2, mockUser3, mockUser4, mockUser5)).blockLast();


            daveAsPlayer.setUser(mockUser1);
            daveAsPlayer.setGame(mockGame2);
            sarahAsMC.setGame(mockGame2);
            sarahAsMC.setUser(mockUser2);
            gameRoleService.saveAll(Flux.just(daveAsPlayer, sarahAsMC)).blockLast();

            threatService.saveAll(Flux.just(mockThreat3, mockThreat4)).blockLast();
            npcService.saveAll(Flux.just(mockNpc3, mockNpc4)).blockLast();

        }


    }
}
