package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.enums.*;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
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
    final String MOCK_GAME_1_ID = "0ca6cc54-77a5-4d6e-ba2e-ee1543d6a249";
    final String MOCK_GAME_2_ID = "ecb645d2-06d3-46dc-ad7f-20bbd167085d";
    final String DAVE_AS_PLAYER_ID = "2a7aba8d-f6e8-4880-8021-99809c800acc";
    final String SARA_AS_PLAYER_ID = "be6b09af-9c96-452a-8b05-922be820c88f";

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

        System.out.println("Character count: " + Objects.requireNonNull(characterRepository.count().block()).toString());
        System.out.println("Game count: " + Objects.requireNonNull(gameRepository.count().block()).toString());
        System.out.println("GameRole count: " + Objects.requireNonNull(gameRoleRepository.count().block()).toString());
        System.out.println("Npc count: " + Objects.requireNonNull(npcRepository.count().block()).toString());
        System.out.println("Threat count: " + Objects.requireNonNull(threatRepository.count().block()).toString());
        System.out.println("User count: " + Objects.requireNonNull(userRepository.count().block()).toString());
    }

    private void loadMockCharacters() {
        GameRole sarahAsPlayer = gameRoleService.findById(SARA_AS_PLAYER_ID).block();
        List<Look> looks = lookService.findAllByPlaybookType(Playbooks.ANGEL).collectList().block();
        assert looks != null;
        Look genderLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.GENDER)).findFirst().orElseThrow();
        Look clothesLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.CLOTHES)).findFirst().orElseThrow();
        Look bodyLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.BODY)).findFirst().orElseThrow();
        Look faceLook = looks.stream()
                .filter(look -> look.getCategory().equals(LookCategories.FACE)).findFirst().orElseThrow();
        Look eyesLook = looks.stream()
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

        List<Move> angelMoves = moveService.findAllByPlaybookAndKind(Playbooks.ANGEL, MoveKinds.CHARACTER)
                .filter(move -> move.getName().equals("ANGEL SPECIAL") || move.getName().equals("SIXTH SENSE") || move.getName().equals("INFIRMARY"))
                .collectList().block();

        assert angelMoves != null;
        List<CharacterMove> characterMoves = angelMoves.stream().map(move -> CharacterMove.createFromMove(move, true)).collect(Collectors.toList());

        // ---------------------------------- Add Characters to Players --------------------------------- //
        Character mockCharacter1 = Character.builder()
                .name("Doc")
                .playbook(Playbooks.ANGEL)
                .looks(List.of(genderLook, clothesLook, bodyLook, faceLook, eyesLook))
                .gear(List.of("Shotgun", "Rusty screwdriver"))
                .statsBlock(List.of(angelCool, angelHard, angelHot, angelSharp, angelWeird))
                .barter(2)
                .characterMoves(characterMoves)
                .hasCompletedCharacterCreation(true)
                .build();
        assert sarahAsPlayer != null;
        if (sarahAsPlayer.getCharacters().size() == 0) {
            sarahAsPlayer.getCharacters().add(mockCharacter1);

//        Character mockCharacter2 = new Character("Leah", daveAsPlayer, Playbooks.SAVVYHEAD, "workshop with tools");
//        daveAsPlayer.getCharacters().add(mockCharacter2);

            characterService.save(mockCharacter1).block();
            gameRoleService.save(sarahAsPlayer).block();
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
            mockGame1.setMc(mockUser1);
            mockGame1.getPlayers().add(mockUser2);
            gameService.save(mockGame1).block();

            mockUser1.getGameRoles().add(daveAsMC);
//        userService.save(mockUser1);

            mockUser2.getGameRoles().add(sarahAsPlayer);
//        userService.save(mockUser2);


            daveAsMC.setUser(mockUser1);
            daveAsMC.setGame(mockGame1);
            sarahAsPlayer.setGame(mockGame1);
            sarahAsPlayer.setUser(mockUser2);
            gameRoleService.saveAll(Flux.just(daveAsMC, sarahAsPlayer)).blockLast();

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
            userService.saveAll(Flux.just(mockUser1, mockUser2)).blockLast();


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
