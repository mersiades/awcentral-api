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
@Profile("dev")
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
    public final String MOCK_GAME_1_ID = new ObjectId().toString();
    public final String MOCK_GAME_2_ID = new ObjectId().toString();
    public static final String DAVE_AS_PLAYER_ID = new ObjectId().toString();
    public static final String SARA_AS_PLAYER_ID = new ObjectId().toString();
    public static final String JOHN_AS_PLAYER_ID = new ObjectId().toString();
    public static final String MAYA_AS_PLAYER_ID = new ObjectId().toString();
    public static final String AHMAD_AS_PLAYER_ID = new ObjectId().toString();
    public static final String TAKESHI_AS_PLAYER_ID = new ObjectId().toString();

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

            GameRole daveAsPlayer = GameRole.builder().id(new ObjectId().toString()).role(RoleType.PLAYER).build();
            GameRole sarahAsMC = GameRole.builder().id(new ObjectId().toString()).role(RoleType.MC).build();

            mockGame2.getGameRoles().add(daveAsPlayer);
            mockGame2.getGameRoles().add(sarahAsMC);
            mockGame2.setMc(mockUser2);
            mockGame2.getPlayers().add(mockUser1);
            gameService.save(mockGame2);

            mockUser1.getGameRoles().add(daveAsPlayer);
            mockUser2.getGameRoles().add(sarahAsMC);
            userService.saveAll(List.of(mockUser1, mockUser2, mockUser3, mockUser4, mockUser5, mockUser6));


            daveAsPlayer.setUserId(mockUser1.getId());
            daveAsPlayer.setGameId(mockGame2.getId());
            daveAsPlayer.setGameName(mockGame2.getName());
            sarahAsMC.setGameId(mockGame2.getId());
            sarahAsMC.setGameName(mockGame2.getName());
            sarahAsMC.setUserId(mockUser2.getId());
            gameRoleService.saveAll(List.of(daveAsPlayer, sarahAsMC));

        }


    }
}
