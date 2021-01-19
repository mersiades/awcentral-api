package com.mersiades.awcweb.bootstrap;

import com.mersiades.awccontent.enums.RoleType;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.repositories.*;
import com.mersiades.awcdata.services.*;
import com.mersiades.awccontent.enums.ThreatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@Order(value = 1)
@Profile("dev")
public class MockUserLoader implements CommandLineRunner {

    private final GameService gameService;
    private final GameRoleService gameRoleService;
    private final ThreatService threatService;
    private final NpcService npcService;
    private final UserService userService;

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

    public MockUserLoader(GameService gameService,
                          GameRoleService gameRoleService,
                          ThreatService threatService,
                          NpcService npcService,
                          UserService userService) {
        this.gameService = gameService;
        this.gameRoleService = gameRoleService;
        this.threatService = threatService;
        this.npcService = npcService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        loadMockData();

        System.out.println("Game count: " + Objects.requireNonNull(gameRepository.count().block()).toString());
        System.out.println("GameRole count: " + Objects.requireNonNull(gameRoleRepository.count().block()).toString());
        System.out.println("Npc count: " + Objects.requireNonNull(npcRepository.count().block()).toString());
        System.out.println("Threat count: " + Objects.requireNonNull(threatRepository.count().block()).toString());
        System.out.println("User count: " + Objects.requireNonNull(userRepository.count().block()).toString());
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

            // ------------------------------ Set up mock Game 1 with Game RoleType ----------------------------- //
            Game mockGame1 = Game.builder()
                    .id(MOCK_GAME_1_ID)
                    .name("Mock Game 1")
                    .commsApp("Zoom")
                    .commsUrl("https://zoom.com/somethingsomething?something=something&somethingelse=somethingelse")
                    .hasFinishedPreGame(false)
                    .build();

            GameRole daveAsMC = GameRole.builder().id(DAVE_AS_PLAYER_ID).role(RoleType.MC).build();
            GameRole sarahAsPlayer = GameRole.builder().id(SARA_AS_PLAYER_ID).role(RoleType.PLAYER).build();
            GameRole johnAsPlayer = GameRole.builder().id(JOHN_AS_PLAYER_ID).role(RoleType.PLAYER).build();
            GameRole mayaAsPlayer = GameRole.builder().id(MAYA_AS_PLAYER_ID).role(RoleType.PLAYER).build();
            GameRole ahmadAsPlayer = GameRole.builder().id(AHMAD_AS_PLAYER_ID).role(RoleType.PLAYER).build();

            Npc mockNpc1 = Npc.builder().name("Vision").description("Badass truck; driver").build();
            Npc mockNpc2 = Npc.builder().name("Nbeke").build();

            Threat mockThreat1 = Threat.builder().name("Tum Tum").threatKind(ThreatType.WARLORD).impulse("Slaver: to own and sell people").build();
            Threat mockThreat2 = Threat.builder().name("Gnarly").threatKind(ThreatType.GROTESQUE).impulse("Cannibal: craves satiety and plenty").build();

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

            // ------------------------------ Set up mock Game 2 with Game RoleType ----------------------------- //
            Game mockGame2 = Game.builder()
                    .id(MOCK_GAME_2_ID)
                    .name("Mock Game 2")
                    .commsApp("Discord")
                    .commsUrl("https://discord.com/something")
                    .hasFinishedPreGame(false)
                    .build();

            GameRole daveAsPlayer = GameRole.builder().id(UUID.randomUUID().toString()).role(RoleType.PLAYER).build();
            GameRole sarahAsMC = GameRole.builder().id(UUID.randomUUID().toString()).role(RoleType.MC).build();

            Npc mockNpc3 = Npc.builder().name("Batty").description("Overly polite gun for hire").build();
            Npc mockNpc4 = Npc.builder().name("Farley").build();

            Threat mockThreat3 = Threat.builder().name("Fleece").threatKind(ThreatType.BRUTE).impulse("Hunting pack: to victimize anyone vulnerable").build();
            Threat mockThreat4 = Threat.builder().name("Wet Rot").threatKind(ThreatType.AFFLICTION).impulse("Condition: to expose people to danger").build();

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
