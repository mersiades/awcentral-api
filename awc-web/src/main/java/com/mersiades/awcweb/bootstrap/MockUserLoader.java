package com.mersiades.awcweb.bootstrap;

import com.mersiades.awccontent.enums.RoleType;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.*;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(value = 2)
@Profile({"dev", "cypress"})
@Slf4j
public class MockUserLoader implements CommandLineRunner {

    private final GameService gameService;
    private final GameRoleService gameRoleService;
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
    final String KEYCLOAK_ID_6 = System.getenv("TAKESHI_ID");
    final String KEYCLOAK_DISPLAY_NAME_6 = "takeshi";
    final String KEYCLOAK_EMAIL_6 = "takeshi@email.com";
    final String KEYCLOAK_ID_7 = System.getenv("MARAMA_ID");
    final String KEYCLOAK_DISPLAY_NAME_7 = "marama";
    final String KEYCLOAK_EMAIL_7 = "marama@email.com";
    final String KEYCLOAK_ID_8 = System.getenv("OLAYINKA_ID");
    final String KEYCLOAK_DISPLAY_NAME_8 = "olayinka";
    final String KEYCLOAK_EMAIL_8 = "olayinka@email.com";
    final String KEYCLOAK_ID_9 = System.getenv("WILMER_ID");
    final String KEYCLOAK_DISPLAY_NAME_9 = "wilmer";
    final String KEYCLOAK_EMAIL_9 = "wilmer@email.com";
    final String KEYCLOAK_ID_10 = System.getenv("IVETTE_ID");
    final String KEYCLOAK_DISPLAY_NAME_10 = "ivette";
    final String KEYCLOAK_EMAIL_10 = "ivette@email.com";
    final String KEYCLOAK_ID_11 = System.getenv("SERGIO_ID");
    final String KEYCLOAK_DISPLAY_NAME_11 = "sergio";
    final String KEYCLOAK_EMAIL_11 = "sergio@email.com";
    final String KEYCLOAK_ID_12 = System.getenv("CAESAR_ID");
    final String KEYCLOAK_DISPLAY_NAME_12 = "caesar";
    final String KEYCLOAK_EMAIL_12 = "caesar@email.com";
    final String KEYCLOAK_ID_13 = System.getenv("CRISTI_ID");
    final String KEYCLOAK_DISPLAY_NAME_13 = "cristi";
    final String KEYCLOAK_EMAIL_13 = "cristi@email.com";
    public final String MOCK_GAME_1_ID = new ObjectId().toString();
    public final String MOCK_GAME_2_ID = new ObjectId().toString();
    public final String MOCK_GAME_3_ID = "mock-game-3-id";
    public final String MOCK_GAME_6_ID = "mock-game-6-id";
    public static final String DAVE_AS_MC_1_ID = "dave-mc-gamerole-1-id";
    public static final String DAVE_AS_MC_2_ID = "dave-mc-gamerole-2-id";
    public static final String DAVE_AS_MC_3_ID = "dave-mc-gamerole-3-id";
    public static final String DAVE_AS_PLAYER_ID = new ObjectId().toString();
    public static final String SARA_AS_PLAYER_ID = new ObjectId().toString();
    public static final String JOHN_AS_PLAYER_ID = new ObjectId().toString();
    public static final String MAYA_AS_PLAYER_ID = new ObjectId().toString();
    public static final String AHMAD_AS_PLAYER_ID = new ObjectId().toString();
    public static final String TAKESHI_AS_PLAYER_ID = new ObjectId().toString();
    public static final String SARA_AS_PLAYER_ID_2 = "sara-player-gamerole-2-id";
    public static final String JOHN_AS_PLAYER_ID_2 = "john-player-gamerole-2-id";
    public static final String MAYA_AS_PLAYER_ID_2 = "maya-player-gamerole-2-id";
    public static final String AHMAD_AS_PLAYER_ID_2 = "ahmad-player-gamerole-2-id";
    public static final String TAKESHI_AS_PLAYER_ID_2 = "takeshi-player-gamerole-2-id";
    public static final String MARAMA_AS_PLAYER_ID_2 = "marama-player-gamerole-2-id";
    public static final String OLAYINKA_AS_PLAYER_ID_2 = "olayinka-player-gamerole-2-id";
    public static final String WILMER_AS_PLAYER_ID_2 = "wilmer-player-gamerole-2-id";
    public static final String IVETTE_AS_PLAYER_ID_2 = "ivette-player-gamerole-2-id";
    public static final String SERGIO_AS_PLAYER_ID_2 = "sergio-player-gamerole-2-id";
    public static final String CAESAR_AS_PLAYER_ID_2 = "caesar-player-gamerole-2-id";
    public static final String CRISTI_AS_PLAYER_ID_2 = "cristi-player-gamerole-2-id";

    @Autowired
    UserRepository userRepository;

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
                          UserService userService) {
        this.gameService = gameService;
        this.gameRoleService = gameRoleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        loadMockData();

        log.info("Game count: " + gameRepository.count());
        log.info("GameRole count: " + gameRoleRepository.count());
        log.info("User count: " + userRepository.count());
    }

    private void loadMockData() {
        List<User> existingUsers = userRepository.findAll();
        if (existingUsers.isEmpty()) {

            // -------------------------------------- Set up mock Users -------------------------------------- //
            User mockUser1 = User.builder()
                    .id((KEYCLOAK_ID_1))
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

            User mockUser6 = User.builder()
                    .id(KEYCLOAK_ID_6)
                    .displayName(KEYCLOAK_DISPLAY_NAME_6)
                    .email(KEYCLOAK_EMAIL_6).build();

            User mockUser7 = User.builder()
                    .id(KEYCLOAK_ID_7)
                    .displayName(KEYCLOAK_DISPLAY_NAME_7)
                    .email(KEYCLOAK_EMAIL_7).build();

            User mockUser8 = User.builder()
                    .id(KEYCLOAK_ID_8)
                    .displayName(KEYCLOAK_DISPLAY_NAME_8)
                    .email(KEYCLOAK_EMAIL_8).build();

            User mockUser9 = User.builder()
                    .id(KEYCLOAK_ID_9)
                    .displayName(KEYCLOAK_DISPLAY_NAME_9)
                    .email(KEYCLOAK_EMAIL_9).build();

            User mockUser10 = User.builder()
                    .id(KEYCLOAK_ID_10)
                    .displayName(KEYCLOAK_DISPLAY_NAME_10)
                    .email(KEYCLOAK_EMAIL_10).build();

            User mockUser11 = User.builder()
                    .id(KEYCLOAK_ID_11)
                    .displayName(KEYCLOAK_DISPLAY_NAME_11)
                    .email(KEYCLOAK_EMAIL_11).build();

            User mockUser12 = User.builder()
                    .id(KEYCLOAK_ID_12)
                    .displayName(KEYCLOAK_DISPLAY_NAME_12)
                    .email(KEYCLOAK_EMAIL_12).build();

            User mockUser13 = User.builder()
                    .id(KEYCLOAK_ID_13)
                    .displayName(KEYCLOAK_DISPLAY_NAME_13)
                    .email(KEYCLOAK_EMAIL_13).build();

            // ------------------------------ Set up mock Game 1 with Game RoleType ----------------------------- //
            Game mockGame1 = Game.builder()
                    .id(MOCK_GAME_1_ID)
                    .name("Mock Game 1")
                    .commsApp("Zoom")
                    .commsUrl("https://zoom.com/somethingsomething?something=something&somethingelse=somethingelse")
                    .hasFinishedPreGame(false)
                    .build();

            GameRole daveAsMC = GameRole.builder().id(DAVE_AS_MC_1_ID).role(RoleType.MC).build();
            GameRole sarahAsPlayer = GameRole.builder().id(SARA_AS_PLAYER_ID).role(RoleType.PLAYER).build();
            GameRole johnAsPlayer = GameRole.builder().id(JOHN_AS_PLAYER_ID).role(RoleType.PLAYER).build();
            GameRole mayaAsPlayer = GameRole.builder().id(MAYA_AS_PLAYER_ID).role(RoleType.PLAYER).build();
            GameRole ahmadAsPlayer = GameRole.builder().id(AHMAD_AS_PLAYER_ID).role(RoleType.PLAYER).build();
            GameRole takeshiAsPlayer = GameRole.builder().id(TAKESHI_AS_PLAYER_ID).role(RoleType.PLAYER).build();

            mockGame1.getGameRoles().add(daveAsMC);
            mockGame1.getGameRoles().add(sarahAsPlayer);
            mockGame1.getGameRoles().add(johnAsPlayer);
            mockGame1.getGameRoles().add(mayaAsPlayer);
            mockGame1.getGameRoles().add(ahmadAsPlayer);
            mockGame1.getGameRoles().add(takeshiAsPlayer);
            mockGame1.setMc(mockUser1);
            mockGame1.getPlayers().add(mockUser2);
            mockGame1.getPlayers().add(mockUser3);
            mockGame1.getPlayers().add(mockUser4);
            mockGame1.getPlayers().add(mockUser5);
            mockGame1.getPlayers().add(mockUser6);
            gameService.save(mockGame1);

            mockUser1.getGameRoles().add(daveAsMC);

            mockUser2.getGameRoles().add(sarahAsPlayer);
            mockUser3.getGameRoles().add(johnAsPlayer);
            mockUser4.getGameRoles().add(mayaAsPlayer);
            mockUser5.getGameRoles().add(ahmadAsPlayer);
            mockUser6.getGameRoles().add(takeshiAsPlayer);

            daveAsMC.setGameId(mockGame1.getId());
            daveAsMC.setGameName(mockGame1.getName());
            daveAsMC.setUserId(mockUser1.getId());
            sarahAsPlayer.setGameId(mockGame1.getId());
            sarahAsPlayer.setGameName(mockGame1.getName());
            sarahAsPlayer.setUserId(mockUser2.getId());
            johnAsPlayer.setGameId(mockGame1.getId());
            johnAsPlayer.setGameName(mockGame1.getName());
            johnAsPlayer.setUserId(mockUser3.getId());
            mayaAsPlayer.setGameId(mockGame1.getId());
            mayaAsPlayer.setGameName(mockGame1.getName());
            mayaAsPlayer.setUserId(mockUser4.getId());
            ahmadAsPlayer.setGameId(mockGame1.getId());
            ahmadAsPlayer.setGameName(mockGame1.getName());
            ahmadAsPlayer.setUserId(mockUser5.getId());
            takeshiAsPlayer.setGameId(mockGame1.getId());
            takeshiAsPlayer.setGameName(mockGame1.getName());
            takeshiAsPlayer.setUserId(mockUser6.getId());
            gameRoleService.saveAll(List.of(daveAsMC, sarahAsPlayer, johnAsPlayer,
                    mayaAsPlayer, ahmadAsPlayer, takeshiAsPlayer));

            // ------------------------------ Set up mock Game 2 with Game RoleType ----------------------------- //
            Game mockGame2 = Game.builder()
                    .id(MOCK_GAME_2_ID)
                    .name("Mock Game 2")
                    .commsApp("Discord")
                    .commsUrl("https://discord.com/something")
                    .hasFinishedPreGame(false)
                    .build();

            GameRole daveAsPlayer = GameRole.builder().id(DAVE_AS_PLAYER_ID).role(RoleType.PLAYER).build();
            GameRole sarahAsMC = GameRole.builder().id(new ObjectId().toString()).role(RoleType.MC).build();

            mockGame2.getGameRoles().add(daveAsPlayer);
            mockGame2.getGameRoles().add(sarahAsMC);
            mockGame2.setMc(mockUser2);
            mockGame2.getPlayers().add(mockUser1);
            gameService.save(mockGame2);

            mockUser1.getGameRoles().add(daveAsPlayer);
            mockUser2.getGameRoles().add(sarahAsMC);
            


            daveAsPlayer.setUserId(mockUser1.getId());
            daveAsPlayer.setGameId(mockGame2.getId());
            daveAsPlayer.setGameName(mockGame2.getName());
            sarahAsMC.setGameId(mockGame2.getId());
            sarahAsMC.setGameName(mockGame2.getName());
            sarahAsMC.setUserId(mockUser2.getId());
            gameRoleService.saveAll(List.of(daveAsPlayer, sarahAsMC));

            // ------------------------------ Set up mock Game 3 with invitations ----------------------------- //
            Game mockGame3 = Game.builder()
                    .id(MOCK_GAME_3_ID)
                    .name("Mock Game 3")
                    .commsApp("Zoom")
                    .commsUrl("https://www.zoom.com/meeting-id")
                    .hasFinishedPreGame(false)
                    .invitees(List.of(mockUser3.getEmail(), mockUser4.getEmail(), mockUser5.getEmail()))
                    .mc(mockUser1)
                    .build();

            GameRole daveAsMC2 = GameRole.builder().id(DAVE_AS_MC_2_ID).role(RoleType.MC).build();
            daveAsMC2.setUserId(mockUser1.getId());
            daveAsMC2.setGameId(mockGame3.getId());
            daveAsMC2.setGameName(mockGame3.getName());
            mockGame3.getGameRoles().add(daveAsMC2);
            

            gameService.save(mockGame3);
            gameRoleService.save(daveAsMC2);

            // ------------------------------ Set up mock Game 4 ----------------------------- //
            // Mock Game 4 is created by a Cypress CreatingGame test

            // ------------------------------ Set up mock Game 5 ----------------------------- //
            // Mock Game 5 is created by a Cypress CreatingGame test

            // ------------------------------ Set up mock Game 6 ----------------------------- //
            // Mock Game 6 is a big game with lots of players.
            // It's used to test the character creation process for each PlaybookType.
            // So most of the players in the game have no characters,
            // but there is one with a complete character so that the HxForm can be completed by other players

            Game mockGame6 = Game.builder()
                    .id(MOCK_GAME_6_ID)
                    .name("Mock Game 6")
                    .hasFinishedPreGame(false)
                    .mc(mockUser1)
                    .build();

            GameRole daveAsMC3 = GameRole.builder().id(DAVE_AS_MC_3_ID).role(RoleType.MC).build();
            daveAsMC2.setUserId(mockUser1.getId());
            daveAsMC2.setGameId(mockGame6.getId());
            daveAsMC2.setGameName(mockGame6.getName());
            mockGame3.getGameRoles().add(daveAsMC3);
            mockUser1.getGameRoles().add(daveAsMC3);

            GameRole sarahAsPlayer2 = GameRole.builder().id(SARA_AS_PLAYER_ID_2).role(RoleType.PLAYER).build();
            sarahAsPlayer2.setUserId(mockUser2.getId());
            sarahAsPlayer2.setGameId(mockGame6.getId());
            sarahAsPlayer2.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(sarahAsPlayer2);
            mockGame6.getPlayers().add(mockUser2);
            mockUser2.getGameRoles().add(sarahAsPlayer2);
            
            GameRole johnAsPlayer2 = GameRole.builder().id(JOHN_AS_PLAYER_ID_2).role(RoleType.PLAYER).build();
            johnAsPlayer2.setUserId(mockUser3.getId());
            johnAsPlayer2.setGameId(mockGame6.getId());
            johnAsPlayer2.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(johnAsPlayer2);
            mockGame6.getPlayers().add(mockUser3);
            mockUser3.getGameRoles().add(johnAsPlayer2);
            
            GameRole mayaAsPlayer2 = GameRole.builder().id(MAYA_AS_PLAYER_ID_2).role(RoleType.PLAYER).build();
            mayaAsPlayer2.setUserId(mockUser4.getId());
            mayaAsPlayer2.setGameId(mockGame6.getId());
            mayaAsPlayer2.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(mayaAsPlayer2);
            mockGame6.getPlayers().add(mockUser4);
            mockUser4.getGameRoles().add(mayaAsPlayer2);
            
            GameRole ahmadAsPlayer2 = GameRole.builder().id(AHMAD_AS_PLAYER_ID_2).role(RoleType.PLAYER).build();
            ahmadAsPlayer2.setUserId(mockUser5.getId());
            ahmadAsPlayer2.setGameId(mockGame6.getId());
            ahmadAsPlayer2.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(ahmadAsPlayer2);
            mockGame6.getPlayers().add(mockUser5);
            mockUser5.getGameRoles().add(ahmadAsPlayer2);
            
            GameRole takeshiAsPlayer2 = GameRole.builder().id(TAKESHI_AS_PLAYER_ID_2).role(RoleType.PLAYER).build();
            takeshiAsPlayer2.setUserId(mockUser6.getId());
            takeshiAsPlayer2.setGameId(mockGame6.getId());
            takeshiAsPlayer2.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(takeshiAsPlayer2);
            mockGame6.getPlayers().add(mockUser6);
            mockUser6.getGameRoles().add(takeshiAsPlayer2);
            
            GameRole maramaAsPlayer2 = GameRole.builder().id(MARAMA_AS_PLAYER_ID_2).role(RoleType.PLAYER).build();
            maramaAsPlayer2.setUserId(mockUser7.getId());
            maramaAsPlayer2.setGameId(mockGame6.getId());
            maramaAsPlayer2.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(maramaAsPlayer2);
            mockGame6.getPlayers().add(mockUser7);
            mockUser7.getGameRoles().add(maramaAsPlayer2);
            
            GameRole olayinkaAsPlayer2 = GameRole.builder().id(OLAYINKA_AS_PLAYER_ID_2).role(RoleType.PLAYER).build();
            olayinkaAsPlayer2.setUserId(mockUser8.getId());
            olayinkaAsPlayer2.setGameId(mockGame6.getId());
            olayinkaAsPlayer2.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(olayinkaAsPlayer2);
            mockGame6.getPlayers().add(mockUser8);
            mockUser8.getGameRoles().add(olayinkaAsPlayer2);
            
            GameRole wilmerAsPlayer2 = GameRole.builder().id(WILMER_AS_PLAYER_ID_2).role(RoleType.PLAYER).build();
            wilmerAsPlayer2.setUserId(mockUser9.getId());
            wilmerAsPlayer2.setGameId(mockGame6.getId());
            wilmerAsPlayer2.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(wilmerAsPlayer2);
            mockGame6.getPlayers().add(mockUser9);
            mockUser9.getGameRoles().add(wilmerAsPlayer2);

            GameRole ivetteAsPlayer2 = GameRole.builder().id(IVETTE_AS_PLAYER_ID_2).role(RoleType.PLAYER).build();
            ivetteAsPlayer2.setUserId(mockUser10.getId());
            ivetteAsPlayer2.setGameId(mockGame6.getId());
            ivetteAsPlayer2.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(ivetteAsPlayer2);
            mockGame6.getPlayers().add(mockUser10);
            mockUser10.getGameRoles().add(ivetteAsPlayer2);
            
            GameRole sergioAsPlayer2 = GameRole.builder().id(SERGIO_AS_PLAYER_ID_2).role(RoleType.PLAYER).build();
            sergioAsPlayer2.setUserId(mockUser11.getId());
            sergioAsPlayer2.setGameId(mockGame6.getId());
            sergioAsPlayer2.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(sergioAsPlayer2);
            mockGame6.getPlayers().add(mockUser11);
            mockUser11.getGameRoles().add(sergioAsPlayer2);
            
            GameRole caesarAsPlayer2 = GameRole.builder().id(CAESAR_AS_PLAYER_ID_2).role(RoleType.PLAYER).build();
            caesarAsPlayer2.setUserId(mockUser12.getId());
            caesarAsPlayer2.setGameId(mockGame6.getId());
            caesarAsPlayer2.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(caesarAsPlayer2);
            mockGame6.getPlayers().add(mockUser12);
            mockUser12.getGameRoles().add(caesarAsPlayer2);
            
            GameRole cristiAsPlayer2 = GameRole.builder().id(CRISTI_AS_PLAYER_ID_2).role(RoleType.PLAYER).build();
            cristiAsPlayer2.setUserId(mockUser13.getId());
            cristiAsPlayer2.setGameId(mockGame6.getId());
            cristiAsPlayer2.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(cristiAsPlayer2);
            mockGame6.getPlayers().add(mockUser13);
            mockUser13.getGameRoles().add(cristiAsPlayer2);

            gameService.save(mockGame6);
            gameRoleService.saveAll(List.of(
                    daveAsMC3,
                    sarahAsPlayer2,
                    johnAsPlayer2,
                    mayaAsPlayer2,
                    ahmadAsPlayer2,
                    takeshiAsPlayer2,
                    maramaAsPlayer2,
                    olayinkaAsPlayer2,
                    wilmerAsPlayer2,
                    ivetteAsPlayer2,
                    sergioAsPlayer2,
                    caesarAsPlayer2,
                    cristiAsPlayer2
            ));
            
            // ----------------------------- Saving it all ----------------------------------- //

            userService.saveAll(List.of(
                    mockUser1, 
                    mockUser2, 
                    mockUser3, 
                    mockUser4, 
                    mockUser5, 
                    mockUser6,
                    mockUser7,
                    mockUser8,
                    mockUser9,
                    mockUser10,
                    mockUser11,
                    mockUser12,
                    mockUser13
            ));

        }




    }
}
