package com.mersiades.awcweb.services.impl;

import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.models.uniquecreators.GangCreator;
import com.mersiades.awccontent.services.*;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.*;
import com.mersiades.awcdata.repositories.UserRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import com.mersiades.awcweb.services.MockDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mersiades.awccontent.constants.MoveNames.*;
import static com.mersiades.awccontent.content.PlaybookCreatorsContent.*;
import static com.mersiades.awccontent.content.PlaybookCreatorsContent.followersCreator;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockDataServiceImpl implements MockDataService {

    private final GameService gameService;
    private final GameRoleService gameRoleService;
    private final UserService userService;
    private final LookService lookService;
    private final CharacterService characterService;
    private final StatsOptionService statsOptionService;
    private final MoveService moveService;
    private final PlaybookCreatorService playbookCreatorService;
    private final VehicleCreatorService vehicleCreatorService;
    private final ThreatCreatorService threatCreatorService;
    private final UserRepository userRepository;

    final String AUTH0_ID_1 = System.getenv("DAVE_ID");
    final String AUTH0_DISPLAY_NAME_1 = "dave";
    final String AUTH0_EMAIL_1 = "dave@email.com";
    final String AUTH0_ID_2 = System.getenv("SARA_ID");
    final String AUTH0_DISPLAY_NAME_2 = "sara";
    final String AUTH0_EMAIL_2 = "sara@email.com";
    final String AUTH0_ID_3 = System.getenv("JOHN_ID");
    final String AUTH0_DISPLAY_NAME_3 = "john";
    final String AUTH0_EMAIL_3 = "john@email.com";
    final String AUTH0_ID_4 = System.getenv("MAYA_ID");
    final String AUTH0_DISPLAY_NAME_4 = "maya";
    final String AUTH0_EMAIL_4 = "maya@email.com";
    final String AUTH0_ID_5 = System.getenv("AHMAD_ID");
    final String AUTH0_DISPLAY_NAME_5 = "ahmad";
    final String AUTH0_EMAIL_5 = "ahmad@email.com";
    final String AUTH0_ID_6 = System.getenv("TAKESHI_ID");
    final String AUTH0_DISPLAY_NAME_6 = "takeshi";
    final String AUTH0_EMAIL_6 = "takeshi@email.com";
    final String AUTH0_ID_7 = System.getenv("MARAMA_ID");
    final String AUTH0_DISPLAY_NAME_7 = "marama";
    final String AUTH0_EMAIL_7 = "marama@email.com";
    final String AUTH0_ID_8 = System.getenv("OLAYINKA_ID");
    final String AUTH0_DISPLAY_NAME_8 = "olayinka";
    final String AUTH0_EMAIL_8 = "olayinka@email.com";
    final String AUTH0_ID_9 = System.getenv("WILMER_ID");
    final String AUTH0_DISPLAY_NAME_9 = "wilmer";
    final String AUTH0_EMAIL_9 = "wilmer@email.com";
    final String AUTH0_ID_10 = System.getenv("IVETTE_ID");
    final String AUTH0_DISPLAY_NAME_10 = "ivette";
    final String AUTH0_EMAIL_10 = "ivette@email.com";
    final String AUTH0_ID_11 = System.getenv("SERGIO_ID");
    final String AUTH0_DISPLAY_NAME_11 = "sergio";
    final String AUTH0_EMAIL_11 = "sergio@email.com";
    final String AUTH0_ID_12 = System.getenv("CAESAR_ID");
    final String AUTH0_DISPLAY_NAME_12 = "caesar";
    final String AUTH0_EMAIL_12 = "caesar@email.com";
    final String AUTH0_ID_13 = System.getenv("CRISTI_ID");
    final String AUTH0_DISPLAY_NAME_13 = "cristi";
    final String AUTH0_EMAIL_13 = "cristi@email.com";
    public final String MOCK_GAME_1_ID = "mock-game-1-id";
    public final String MOCK_GAME_2_ID = "mock-game-2-id";
    public final String MOCK_GAME_3_ID = "mock-game-3-id";
    public final String MOCK_GAME_6_ID = "mock-game-6-id";
    public final String MOCK_GAME_7_ID = "mock-game-7-id";
    public static final String DAVE_AS_MC_1_ID = "dave-mc-gamerole-1-id";
    public static final String DAVE_AS_MC_2_ID = "dave-mc-gamerole-2-id";
    public static final String DAVE_AS_MC_3_ID = "dave-mc-gamerole-3-id";
    public static final String DAVE_AS_MC_4_ID = "dave-mc-gamerole-4-id";
    public static final String DAVE_AS_PLAYER_ID = new ObjectId().toString();
    public static final String SARA_AS_PLAYER_ID = "sara-player-gamerole-1-id";
    public static final String JOHN_AS_PLAYER_ID = "john-player-gamerole-1-id";
    public static final String MAYA_AS_PLAYER_ID = "maya-player-gamerole-1-id";
    public static final String AHMAD_AS_PLAYER_ID = "ahmad-player-gamerole-1-id";
    public static final String TAKESHI_AS_PLAYER_ID = "takeshi-player-gamerole-1-id";
    public static final String SARA_AS_PLAYER_ID_2 = "sara-player-gamerole-2-id";
    public static final String JOHN_AS_PLAYER_ID_2 = "john-player-gamerole-2-id";
    public static final String MAYA_AS_PLAYER_ID_2 = "maya-player-gamerole-2-id";
    public static final String AHMAD_AS_PLAYER_ID_2 = "ahmad-player-gamerole-2-id";
    public static final String TAKESHI_AS_PLAYER_ID_2 = "takeshi-player-gamerole-2-id";
    public static final String MARAMA_AS_PLAYER_ID_1 = "marama-player-gamerole-1-id";
    public static final String MARAMA_AS_PLAYER_ID_2 = "marama-player-gamerole-2-id";
    public static final String OLAYINKA_AS_PLAYER_ID_2 = "olayinka-player-gamerole-2-id";
    public static final String WILMER_AS_PLAYER_ID_1 = "wilmer-player-gamerole-1-id";
    public static final String WILMER_AS_PLAYER_ID_2 = "wilmer-player-gamerole-2-id";
    public static final String IVETTE_AS_PLAYER_ID_1 = "ivette-player-gamerole-1-id";
    public static final String IVETTE_AS_PLAYER_ID_2 = "ivette-player-gamerole-2-id";
    public static final String SERGIO_AS_PLAYER_ID_2 = "sergio-player-gamerole-2-id";
    public static final String CAESAR_AS_PLAYER_ID_2 = "caesar-player-gamerole-2-id";
    public static final String CRISTI_AS_PLAYER_ID_2 = "cristi-player-gamerole-2-id";
    public static final String SARA_AS_PLAYER_ID_3 = "sara-player-gamerole-3-id";
    public static final String JOHN_AS_PLAYER_ID_3 = "john-player-gamerole-3-id";
    public static final String AHMAD_AS_PLAYER_ID_3 = "ahmad-player-gamerole-3-id";
    public static final String TAKESHI_AS_PLAYER_ID_3 = "takeshi-player-gamerole-3-id";
    public static final String MARAMA_AS_PLAYER_ID_3 = "marama-player-gamerole-3-id";
    public static final String WILMER_AS_PLAYER_ID_3 = "wilmer-player-gamerole-3-id";
    public static final String IVETTE_AS_PLAYER_ID_3 = "ivette-player-gamerole-3-id";

    @Override
    public void loadMockData() {
        List<User> existingUsers = userRepository.findAll();
        if (existingUsers.isEmpty()) {

            // -------------------------------------- Set up mock Users -------------------------------------- //
            User mockUser1 = User.builder()
                    .id((AUTH0_ID_1))
                    .displayName(AUTH0_DISPLAY_NAME_1)
                    .email(AUTH0_EMAIL_1).build();

            User mockUser2 = User.builder()
                    .id(AUTH0_ID_2)
                    .displayName(AUTH0_DISPLAY_NAME_2)
                    .email(AUTH0_EMAIL_2).build();

            User mockUser3 = User.builder()
                    .id(AUTH0_ID_3)
                    .displayName(AUTH0_DISPLAY_NAME_3)
                    .email(AUTH0_EMAIL_3).build();

            User mockUser4 = User.builder()
                    .id(AUTH0_ID_4)
                    .displayName(AUTH0_DISPLAY_NAME_4)
                    .email(AUTH0_EMAIL_4).build();

            User mockUser5 = User.builder()
                    .id(AUTH0_ID_5)
                    .displayName(AUTH0_DISPLAY_NAME_5)
                    .email(AUTH0_EMAIL_5).build();

            User mockUser6 = User.builder()
                    .id(AUTH0_ID_6)
                    .displayName(AUTH0_DISPLAY_NAME_6)
                    .email(AUTH0_EMAIL_6).build();

            User mockUser7 = User.builder()
                    .id(AUTH0_ID_7)
                    .displayName(AUTH0_DISPLAY_NAME_7)
                    .email(AUTH0_EMAIL_7).build();

            User mockUser8 = User.builder()
                    .id(AUTH0_ID_8)
                    .displayName(AUTH0_DISPLAY_NAME_8)
                    .email(AUTH0_EMAIL_8).build();

            User mockUser9 = User.builder()
                    .id(AUTH0_ID_9)
                    .displayName(AUTH0_DISPLAY_NAME_9)
                    .email(AUTH0_EMAIL_9).build();

            User mockUser10 = User.builder()
                    .id(AUTH0_ID_10)
                    .displayName(AUTH0_DISPLAY_NAME_10)
                    .email(AUTH0_EMAIL_10).build();

            User mockUser11 = User.builder()
                    .id(AUTH0_ID_11)
                    .displayName(AUTH0_DISPLAY_NAME_11)
                    .email(AUTH0_EMAIL_11).build();

            User mockUser12 = User.builder()
                    .id(AUTH0_ID_12)
                    .displayName(AUTH0_DISPLAY_NAME_12)
                    .email(AUTH0_EMAIL_12).build();

            User mockUser13 = User.builder()
                    .id(AUTH0_ID_13)
                    .displayName(AUTH0_DISPLAY_NAME_13)
                    .email(AUTH0_EMAIL_13).build();

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
            GameRole maramaAsPlayer = GameRole.builder().id(MARAMA_AS_PLAYER_ID_1).role(RoleType.PLAYER).build();
            GameRole wilmerAsPlayer = GameRole.builder().id(WILMER_AS_PLAYER_ID_1).role(RoleType.PLAYER).build();
            GameRole ivetteAsPlayer = GameRole.builder().id(IVETTE_AS_PLAYER_ID_1).role(RoleType.PLAYER).build();

            mockGame1.getGameRoles().add(daveAsMC);
            mockGame1.getGameRoles().add(sarahAsPlayer);
            mockGame1.getGameRoles().add(johnAsPlayer);
            mockGame1.getGameRoles().add(mayaAsPlayer);
            mockGame1.getGameRoles().add(ahmadAsPlayer);
            mockGame1.getGameRoles().add(takeshiAsPlayer);
            mockGame1.getGameRoles().add(maramaAsPlayer);
            mockGame1.getGameRoles().add(wilmerAsPlayer);
            mockGame1.getGameRoles().add(ivetteAsPlayer);
            mockGame1.setMc(mockUser1);
            mockGame1.getPlayers().add(mockUser2);
            mockGame1.getPlayers().add(mockUser3);
            mockGame1.getPlayers().add(mockUser4);
            mockGame1.getPlayers().add(mockUser5);
            mockGame1.getPlayers().add(mockUser6);
            mockGame1.getPlayers().add(mockUser7);
            mockGame1.getPlayers().add(mockUser9);
            mockGame1.getPlayers().add(mockUser10);
            gameService.save(mockGame1);

            mockUser1.getGameRoles().add(daveAsMC);

            mockUser2.getGameRoles().add(sarahAsPlayer);
            mockUser3.getGameRoles().add(johnAsPlayer);
            mockUser4.getGameRoles().add(mayaAsPlayer);
            mockUser5.getGameRoles().add(ahmadAsPlayer);
            mockUser6.getGameRoles().add(takeshiAsPlayer);
            mockUser7.getGameRoles().add(maramaAsPlayer);
            mockUser9.getGameRoles().add(wilmerAsPlayer);
            mockUser10.getGameRoles().add(ivetteAsPlayer);


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
            maramaAsPlayer.setGameId(mockGame1.getId());
            maramaAsPlayer.setGameName(mockGame1.getName());
            maramaAsPlayer.setUserId(mockUser7.getId());
            wilmerAsPlayer.setGameId(mockGame1.getId());
            wilmerAsPlayer.setGameName(mockGame1.getName());
            wilmerAsPlayer.setUserId(mockUser9.getId());
            ivetteAsPlayer.setGameId(mockGame1.getId());
            ivetteAsPlayer.setGameName(mockGame1.getName());
            ivetteAsPlayer.setUserId(mockUser10.getId());
            gameRoleService.saveAll(List.of(daveAsMC, sarahAsPlayer, johnAsPlayer,
                    mayaAsPlayer, ahmadAsPlayer, takeshiAsPlayer, maramaAsPlayer,
                    wilmerAsPlayer, ivetteAsPlayer));

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
            daveAsMC3.setUserId(mockUser1.getId());
            daveAsMC3.setGameId(mockGame6.getId());
            daveAsMC3.setGameName(mockGame6.getName());
            mockGame6.getGameRoles().add(daveAsMC3);
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

            // ------------------------------ Set up mock Game 7 ----------------------------- //
            // Mock Game 7 is mid-game, meaning that all characters have been created.
            // Intended for testing playbook panels, moves etc
            Game mockGame7 = Game.builder()
                    .id(MOCK_GAME_7_ID)
                    .name("Mock Game 7")
                    .commsApp("Zoom")
                    .commsUrl("https://zoom.com/somethingsomething?something=something&somethingelse=somethingelse")
                    .hasFinishedPreGame(true)
                    .showFirstSession(false)
                    .mc(mockUser1)
                    .build();

            GameRole daveAsMC4 = GameRole.builder().id(DAVE_AS_MC_4_ID).role(RoleType.MC).build();
            daveAsMC4.setUserId(mockUser1.getId());
            daveAsMC4.setGameId(mockGame7.getId());
            daveAsMC4.setGameName(mockGame7.getName());
            mockGame7.getGameRoles().add(daveAsMC4);
            mockUser1.getGameRoles().add(daveAsMC4);

            // Sara -> Angel
            GameRole sarahAsPlayer3 = GameRole.builder().id(SARA_AS_PLAYER_ID_3).role(RoleType.PLAYER).build();
            sarahAsPlayer3.setUserId(mockUser2.getId());
            sarahAsPlayer3.setGameId(mockGame7.getId());
            sarahAsPlayer3.setGameName(mockGame7.getName());
            mockGame7.getGameRoles().add(sarahAsPlayer3);
            mockGame7.getPlayers().add(mockUser2);
            mockUser2.getGameRoles().add(sarahAsPlayer3);

            // John -> Battlebabe
            GameRole johnAsPlayer3 = GameRole.builder().id(JOHN_AS_PLAYER_ID_3).role(RoleType.PLAYER).build();
            johnAsPlayer3.setUserId(mockUser3.getId());
            johnAsPlayer3.setGameId(mockGame7.getId());
            johnAsPlayer3.setGameName(mockGame7.getName());
            mockGame7.getGameRoles().add(johnAsPlayer3);
            mockGame7.getPlayers().add(mockUser3);
            mockUser3.getGameRoles().add(johnAsPlayer3);

            // Takeshi -> Chopper
            GameRole takeshiAsPlayer3 = GameRole.builder().id(TAKESHI_AS_PLAYER_ID_3).role(RoleType.PLAYER).build();
            takeshiAsPlayer3.setUserId(mockUser6.getId());
            takeshiAsPlayer3.setGameId(mockGame7.getId());
            takeshiAsPlayer3.setGameName(mockGame7.getName());
            mockGame7.getGameRoles().add(takeshiAsPlayer3);
            mockGame7.getPlayers().add(mockUser6);
            mockUser6.getGameRoles().add(takeshiAsPlayer3);

            // Ahmad -> Driver
            GameRole ahmadAsPlayer3 = GameRole.builder().id(AHMAD_AS_PLAYER_ID_3).role(RoleType.PLAYER).build();
            ahmadAsPlayer3.setUserId(mockUser5.getId());
            ahmadAsPlayer3.setGameId(mockGame7.getId());
            ahmadAsPlayer3.setGameName(mockGame7.getName());
            mockGame7.getGameRoles().add(ahmadAsPlayer3);
            mockGame7.getPlayers().add(mockUser5);
            mockUser5.getGameRoles().add(ahmadAsPlayer3);

            // Marama -> Gunlugger
            GameRole maramaAsPlayer3 = GameRole.builder().id(MARAMA_AS_PLAYER_ID_3).role(RoleType.PLAYER).build();
            maramaAsPlayer3.setUserId(mockUser7.getId());
            maramaAsPlayer3.setGameId(mockGame7.getId());
            maramaAsPlayer3.setGameName(mockGame7.getName());
            mockGame7.getGameRoles().add(maramaAsPlayer3);
            mockGame7.getPlayers().add(mockUser7);
            mockUser7.getGameRoles().add(maramaAsPlayer3);

            // Wilmer -> Hocus
            GameRole wilmerAsPlayer3 = GameRole.builder().id(WILMER_AS_PLAYER_ID_3).role(RoleType.PLAYER).build();
            wilmerAsPlayer3.setUserId(mockUser9.getId());
            wilmerAsPlayer3.setGameId(mockGame7.getId());
            wilmerAsPlayer3.setGameName(mockGame7.getName());
            mockGame7.getGameRoles().add(wilmerAsPlayer3);
            mockGame7.getPlayers().add(mockUser9);
            mockUser9.getGameRoles().add(wilmerAsPlayer3);

            // Ivette -> Maestro'D
            GameRole ivetteAsPlayer3 = GameRole.builder().id(IVETTE_AS_PLAYER_ID_3).role(RoleType.PLAYER).build();
            ivetteAsPlayer3.setUserId(mockUser10.getId());
            ivetteAsPlayer3.setGameId(mockGame7.getId());
            ivetteAsPlayer3.setGameName(mockGame7.getName());
            mockGame7.getGameRoles().add(ivetteAsPlayer3);
            mockGame7.getPlayers().add(mockUser10);
            mockUser10.getGameRoles().add(ivetteAsPlayer3);

            gameService.save(mockGame7);
            gameRoleService.saveAll(List.of(
                    daveAsMC4,
                    sarahAsPlayer3,
                    johnAsPlayer3,
                    ahmadAsPlayer3,
                    takeshiAsPlayer3,
                    maramaAsPlayer3,
                    wilmerAsPlayer3,
                    ivetteAsPlayer3
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

    @Override
    public void loadMockCharacters() {

        // For Game 1
        GameRole saraAsPlayer = gameRoleService.findById(SARA_AS_PLAYER_ID);
        assert saraAsPlayer != null;
        GameRole saraAsPlayer2 = gameRoleService.findById(SARA_AS_PLAYER_ID_2);
        assert saraAsPlayer2 != null;
        GameRole johnAsPlayer = gameRoleService.findById(JOHN_AS_PLAYER_ID);
        assert johnAsPlayer != null;
        GameRole mayaAsPlayer = gameRoleService.findById(MAYA_AS_PLAYER_ID);
        assert mayaAsPlayer != null;
        GameRole ahmadAsPlayer = gameRoleService.findById(AHMAD_AS_PLAYER_ID);
        assert ahmadAsPlayer != null;
        GameRole takeshiAsPlayer = gameRoleService.findById(TAKESHI_AS_PLAYER_ID);
        assert takeshiAsPlayer != null;
        GameRole maramaAsPlayer = gameRoleService.findById(MARAMA_AS_PLAYER_ID_1);
        assert maramaAsPlayer != null;
        GameRole wilmerAsPlayer = gameRoleService.findById(WILMER_AS_PLAYER_ID_1);
        assert wilmerAsPlayer != null;
        GameRole ivetteAsPlayer = gameRoleService.findById(IVETTE_AS_PLAYER_ID_1);
        assert ivetteAsPlayer != null;

        // For Game 6
        GameRole mayaAsPlayer2 = gameRoleService.findById(MAYA_AS_PLAYER_ID_2);
        assert mayaAsPlayer2 != null;
        GameRole ahmadAsPlayer2 = gameRoleService.findById(AHMAD_AS_PLAYER_ID_2);
        assert ahmadAsPlayer2 != null;
        GameRole takeshiAsPlayer2 = gameRoleService.findById(TAKESHI_AS_PLAYER_ID_2);
        assert takeshiAsPlayer2 != null;
        GameRole maramaAsPlayer2 = gameRoleService.findById(MARAMA_AS_PLAYER_ID_2);
        assert maramaAsPlayer2 != null;
        GameRole olayinkaAsPlayer = gameRoleService.findById(OLAYINKA_AS_PLAYER_ID_2);
        assert olayinkaAsPlayer != null;
        GameRole wilmerAsPlayer2 = gameRoleService.findById(WILMER_AS_PLAYER_ID_2);
        assert wilmerAsPlayer2 != null;
        GameRole ivetteAsPlayer2 = gameRoleService.findById(IVETTE_AS_PLAYER_ID_2);
        assert ivetteAsPlayer2 != null;
        GameRole sergioAsPlayer = gameRoleService.findById(SERGIO_AS_PLAYER_ID_2);
        assert sergioAsPlayer != null;
        GameRole caesarAsPlayer = gameRoleService.findById(CAESAR_AS_PLAYER_ID_2);
        assert caesarAsPlayer != null;

        // For Game 7
        GameRole saraAsPlayer3 = gameRoleService.findById(SARA_AS_PLAYER_ID_3);
        assert saraAsPlayer3 != null;
        GameRole johnAsPlayer3 = gameRoleService.findById(JOHN_AS_PLAYER_ID_3);
        assert johnAsPlayer3 != null;
        GameRole ahmadAsPlayer3 = gameRoleService.findById(AHMAD_AS_PLAYER_ID_3);
        assert ahmadAsPlayer3 != null;
        GameRole takeshiAsPlayer3 = gameRoleService.findById(TAKESHI_AS_PLAYER_ID_3);
        assert takeshiAsPlayer3 != null;
        GameRole maramaAsPlayer3 = gameRoleService.findById(MARAMA_AS_PLAYER_ID_3);
        assert maramaAsPlayer3 != null;
        GameRole wilmerAsPlayer3 = gameRoleService.findById(WILMER_AS_PLAYER_ID_3);
        assert wilmerAsPlayer3 != null;
        GameRole ivetteAsPlayer3 = gameRoleService.findById(IVETTE_AS_PLAYER_ID_3);
        assert ivetteAsPlayer3 != null;

        CharacterHarm harm = CharacterHarm.builder()
                .hasChangedPlaybook(false)
                .hasComeBackHard(false)
                .hasComeBackWeird(false)
                .hasDied(false)
                .isStabilized(false)
                .value(0)
                .build();

        VehicleCreator vehicleCreator = vehicleCreatorService.findAll().get(0);
        assert vehicleCreator != null;

        // -------------------------------- Set up Sara's Angel ----------------------------------- //
        PlaybookCreator playbookCreatorAngel = playbookCreatorService.findByPlaybookType(PlaybookType.ANGEL);
        List<Look> angelLooks = getLooks(PlaybookType.ANGEL);

        StatsBlock angelStatsBlock1 = getStatsBlock(PlaybookType.ANGEL);

        List<Move> angelKitMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.UNIQUE);

        AngelKit angelKit = AngelKit.builder().id(new ObjectId().toString())
                .hasSupplier(false)
                .angelKitMoves(angelKitMoves)
                .stock(2).build();

        PlaybookUniques angelUnique = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.ANGEL_KIT)
                .angelKit(angelKit)
                .build();

        List<Move> angelMoves = moveService.findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals("SIXTH SENSE") ||
                        move.getName().equals("HEALING TOUCH")).collect(Collectors.toList());

        List<Move> angelDefaultMoves = moveService.
                findAllByPlaybookAndKind(PlaybookType.ANGEL, MoveType.DEFAULT_CHARACTER);
        assert angelDefaultMoves != null;

        List<CharacterMove> characterMoves = createAndMergeCharacterMoves(angelMoves, angelDefaultMoves);

        // -------------------------------- Set up John's Battlebabe ----------------------------------- //
        PlaybookCreator playbookCreatorBattlebabe = playbookCreatorService.findByPlaybookType(PlaybookType.BATTLEBABE);
        List<Look> battlebabeLooks = getLooks(PlaybookType.BATTLEBABE);

        StatsBlock battlebabeStatsBlock = getStatsBlock(PlaybookType.BATTLEBABE);

        CustomWeapons customWeapons = CustomWeapons.builder()
                .id(new ObjectId().toString())
                .weapons(List.of("antique rifle (2-harm, load, valuable", "Ornate staff (1-harm, valuable)"))
                .build();

        PlaybookUniques battlebabeUnique = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.CUSTOM_WEAPONS)
                .customWeapons(customWeapons)
                .build();

        List<Move> battlebabeMoves = moveService.findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals("DANGEROUS & SEXY") ||
                        move.getName().equals("PERFECT INSTINCTS")).collect(Collectors.toList());

        List<Move> battlebabeDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.BATTLEBABE, MoveType.DEFAULT_CHARACTER);
        assert battlebabeDefaultMoves != null;

        List<CharacterMove> characterMoves2 = createAndMergeCharacterMoves(battlebabeMoves, battlebabeDefaultMoves);

        // -------------------------------- Set up Maya's Brainer ----------------------------------- //
        PlaybookCreator playbookCreatorBrainer = playbookCreatorService.findByPlaybookType(PlaybookType.BRAINER);
        List<Look> brainerLooks = getLooks(PlaybookType.BRAINER);

        StatsBlock brainerStatsBlock = getStatsBlock(PlaybookType.BRAINER);

        BrainerGear brainerGear = BrainerGear.builder()
                .id(new ObjectId().toString())
                .brainerGear(List.of(brainerGearCreator.getGear().get(0), brainerGearCreator.getGear().get(1)))
                .build();

        PlaybookUniques brainerUnique = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.BRAINER_GEAR)
                .brainerGear(brainerGear)
                .build();

        BrainerGear brainerGearDefault = BrainerGear.builder()
                .id(new ObjectId().toString())
                .uniqueType(UniqueType.BRAINER_GEAR)
                .allowedItemsCount(brainerGearCreator.getDefaultItemCount())
                .build();

        PlaybookUniques brainerUniqueDefault = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.BRAINER_GEAR)
                .brainerGear(brainerGearDefault)
                .build();

        List<Move> brainerMoves = moveService.findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals("DEEP BRAIN SCAN") ||
                        move.getName().equals("PRETERNATURAL BRAIN ATTUNEMENT")).collect(Collectors.toList());

        List<Move> brainerDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.DEFAULT_CHARACTER);
        assert brainerDefaultMoves != null;

        List<CharacterMove> characterMoves3 = createAndMergeCharacterMoves(brainerMoves, brainerDefaultMoves);

        // -------------------------------- Set up Takeshi's's Chopper ----------------------------------- //
        PlaybookCreator playbookCreatorChopper = playbookCreatorService.findByPlaybookType(PlaybookType.CHOPPER);
        GangCreator gangCreator = playbookCreatorChopper.getPlaybookUniqueCreator().getGangCreator();

        GangOption gangOption1 = gangCreator.getStrengths().get(0);
        GangOption gangOption2 = gangCreator.getStrengths().get(1);
        GangOption gangOption3 = gangCreator.getWeaknesses().get(0);

        List<Look> chopperLooks = getLooks(PlaybookType.CHOPPER);

        StatsBlock chopperStatsBlock = getStatsBlock(PlaybookType.CHOPPER);

        Vehicle chopperBike = Vehicle.builder()
                .id(new ObjectId().toString())
                .vehicleType(VehicleType.BIKE)
                .name("Honda")
                // BIKE frame
                .vehicleFrame(vehicleCreator.getBikeCreator().getFrame())
                // +1handling
                .battleOptions(List.of(vehicleCreator.getBikeCreator().getBattleOptions().get(1)))
                .massive(0)
                .speed(0)
                .armor(0)
                .handling(1)
                .strengths(List.of("fast", "responsive"))
                .looks(List.of("flashy", "luxe"))
                .weaknesses(List.of("lazy", "sloppy"))
                .build();

        Gang gang = Gang.builder()
                .id(new ObjectId().toString())
                .size(GangSize.MEDIUM)
                .harm(3)
                .armor(1)
                .tags(List.of("savage", "vulnerable: breakdown"))
                .strengths(List.of(gangOption1, gangOption2))
                .weaknesses(List.of(gangOption3))
                .allowedStrengths(playbookCreatorChopper.getPlaybookUniqueCreator().getGangCreator().getStrengthChoiceCount())
                .build();

        Gang gangDefault = Gang.builder()
                .id(new ObjectId().toString())
                .uniqueType(UniqueType.GANG)
                .allowedStrengths(gangCreator.getStrengthChoiceCount())
                .size(gangCreator.getDefaultSize())
                .harm(gangCreator.getDefaultHarm())
                .armor(gangCreator.getDefaultArmor())
                .tags(gangCreator.getDefaultTags())
                .build();

        PlaybookUniques chopperUnique = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.GANG)
                .gang(gang)
                .build();

        PlaybookUniques chopperUniqueDefault = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.GANG)
                .gang(gangDefault)
                .build();

        List<Move> chopperDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.CHOPPER, MoveType.DEFAULT_CHARACTER);
        assert chopperDefaultMoves != null;

        List<CharacterMove> characterMoves5 = new ArrayList<>();
        chopperDefaultMoves.forEach(move -> characterMoves5.add(CharacterMove.createFromMove(move)));

        // -------------------------------- Set up Ahmad's Driver ----------------------------------- //
        List<Look> driverLooks = getLooks(PlaybookType.DRIVER);

        StatsBlock driverStatsBlock = getStatsBlock(PlaybookType.DRIVER);

        List<Move> driverMoves = moveService.findAllByPlaybookAndKind(PlaybookType.DRIVER, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals("EYE ON THE DOOR") ||
                        move.getName().equals("COLLECTOR")).collect(Collectors.toList());

        List<Move> driverDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.DRIVER, MoveType.DEFAULT_CHARACTER);
        assert driverDefaultMoves != null;

        List<CharacterMove> characterMoves4 = createAndMergeCharacterMoves(driverMoves, driverDefaultMoves);

        PlaybookCreator playbookCreatorDriver = playbookCreatorService.findByPlaybookType(PlaybookType.DRIVER);
        assert playbookCreatorDriver != null;


        Vehicle car1 = Vehicle.builder()
                .id(new ObjectId().toString())
                .vehicleType(VehicleType.CAR)
                .name("Unnamed vehicle")
                // SMALL frame
                .vehicleFrame(vehicleCreator.getCarCreator().getFrames().get(1))
                // +1speed, +1armor
                .battleOptions(List.of(
                        vehicleCreator.getCarCreator().getBattleOptions().get(0),
                        vehicleCreator.getCarCreator().getBattleOptions().get(3)))
                .massive(1)
                .speed(1)
                .armor(1)
                .handling(0)
                .strengths(List.of("fast"))
                .looks(List.of("quirky", "vintage"))
                .weaknesses(List.of("loud"))
                .build();

        Vehicle car2 = Vehicle.builder()
                .id(new ObjectId().toString())
                .vehicleType(VehicleType.CAR)
                .name("Bess")
                // LARGE frame
                .vehicleFrame(vehicleCreator.getCarCreator().getFrames().get(3))
                // +1massive, +1armor
                .battleOptions(List.of(
                        vehicleCreator.getCarCreator().getBattleOptions().get(2),
                        vehicleCreator.getCarCreator().getBattleOptions().get(3)))
                .massive(4)
                .speed(0)
                .armor(1)
                .handling(0)
                .strengths(List.of("rugged", "aggressive"))
                .weaknesses(List.of("cramped", "picky"))
                .looks(List.of("sleek", "powerful"))
                .build();

        Vehicle bike = Vehicle.builder()
                .id(new ObjectId().toString())
                .vehicleType(VehicleType.BIKE)
                .name("Ducati Monster")
                // BIKE frame
                .vehicleFrame(vehicleCreator.getBikeCreator().getFrame())
                // +1speed
                .battleOptions(List.of(vehicleCreator.getBikeCreator().getBattleOptions().get(0)))
                .massive(0)
                .speed(1)
                .armor(0)
                .handling(0)
                .strengths(List.of("tight", "huge"))
                .looks(List.of("massively-chopped", "fat-ass"))
                .weaknesses(List.of("bucking", "unreliable"))
                .build();

        // -------------------------------- Set up Marama's Gunlugger ------------------------------ //
        PlaybookCreator playbookCreatorGunlugger = playbookCreatorService.findByPlaybookType(PlaybookType.GUNLUGGER);

        List<Look> gunluggerLooks = getLooks(PlaybookType.GUNLUGGER);

        StatsBlock gunluggerStatsBlock = getStatsBlock(PlaybookType.GUNLUGGER);

        Weapons weaponsDefault = Weapons.builder()
                .id(new ObjectId().toString())
                .uniqueType(UniqueType.WEAPONS)
                .build();

        PlaybookUniques gunluggerUniqueDefault = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.WEAPONS)
                .weapons(weaponsDefault)
                .build();

        Weapons weapons = Weapons.builder()
                .id(new ObjectId().toString())
                .uniqueType(UniqueType.WEAPONS)
                .weapons(List.of(
                        playbookCreatorGunlugger.getPlaybookUniqueCreator().getWeaponsCreator().getBigFuckOffGuns().get(0),
                        playbookCreatorGunlugger.getPlaybookUniqueCreator().getWeaponsCreator().getSeriousGuns().get(0),
                        playbookCreatorGunlugger.getPlaybookUniqueCreator().getWeaponsCreator().getSeriousGuns().get(1),
                        playbookCreatorGunlugger.getPlaybookUniqueCreator().getWeaponsCreator().getBackupWeapons().get(0)
                ))
                .build();

        PlaybookUniques gunluggerUnique = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.WEAPONS)
                .weapons(weapons)
                .build();

        List<Move> gunluggerMoves = moveService.findAllByPlaybookAndKind(PlaybookType.GUNLUGGER, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals(battleHardenedName) ||
                        move.getName().equals(fuckThisShitName)).collect(Collectors.toList());

        List<Move> gunluggerDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.GUNLUGGER, MoveType.DEFAULT_CHARACTER);
        assert gunluggerDefaultMoves != null;

        List<CharacterMove> characterMoves6 = createAndMergeCharacterMoves(gunluggerMoves, gunluggerDefaultMoves);

        // -------------------------------- Set up Olayinka's Hardholder ------------------------------ //
        PlaybookCreator playbookCreatorHardholder = playbookCreatorService.findByPlaybookType(PlaybookType.HARDHOLDER);
        List<Look> hardholderLooks = getLooks(PlaybookType.HARDHOLDER);

        StatsBlock hardholderStatsBlock = getStatsBlock(PlaybookType.HARDHOLDER);

        Holding holdingDefault = Holding.builder()
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

        PlaybookUniques hardholderUniqueDefault = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.HOLDING)
                .holding(holdingDefault)
                .build();

        List<Move> hardholderDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.HARDHOLDER, MoveType.DEFAULT_CHARACTER);
        assert hardholderDefaultMoves != null;

        List<CharacterMove> characterMoves7 = new ArrayList<>();
        hardholderDefaultMoves.forEach(move -> characterMoves7.add(CharacterMove.createFromMove(move)));

        // -------------------------------- Set up Wilmer's Hocus ------------------------------ //
        PlaybookCreator playbookCreatorHocus = playbookCreatorService.findByPlaybookType(PlaybookType.HOCUS);

        List<Look> hocusLooks = getLooks(PlaybookType.HOCUS);

        StatsBlock hocusStatsBlock = getStatsBlock(PlaybookType.HOCUS);

        Followers followersDefault = Followers.builder()
                .id(new ObjectId().toString())
                .uniqueType(UniqueType.FOLLOWERS)
                .followers(followersCreator.getDefaultNumberOfFollowers())
                .surplusBarter(followersCreator.getDefaultSurplusBarterCount())
                .fortune(followersCreator.getDefaultFortune())
                .wants(followersCreator.getDefaultWants())
                .surplus(followersCreator.getDefaultSurplus())
                .strengthsCount(followersCreator.getDefaultStrengthsCount())
                .weaknessesCount(followersCreator.getDefaultWeaknessesCount())
                .build();

        PlaybookUniques hocusUniqueDefault = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.FOLLOWERS)
                .followers(followersDefault)
                .build();

        Followers followers = Followers.builder()
                .id(new ObjectId().toString())
                .uniqueType(UniqueType.FOLLOWERS)
                .followers(10)
                .surplusBarter(followersCreator.getDefaultSurplusBarterCount())
                .fortune(2)
                .wants(List.of("hungry", "judgement"))
                .surplus(followersCreator.getDefaultSurplus())
                .strengthsCount(followersCreator.getDefaultStrengthsCount())
                .weaknessesCount(followersCreator.getDefaultWeaknessesCount())
                .selectedStrengths(List.of(followersOption1, followersOption2))
                .selectedWeaknesses(List.of(followersOption8, followersOption9))
                .description("Your cult is about 10 followers. When you travel, they travel with you.")
                .barter(2)
                .travelOption("travel with you")
                .characterization("your cult")
                .build();

        PlaybookUniques hocusUnique = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.FOLLOWERS)
                .followers(followers)
                .build();

        List<Move> hocusMoves = moveService.findAllByPlaybookAndKind(PlaybookType.HOCUS, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals(frenzyName) ||
                        move.getName().equals(charismaticName)).collect(Collectors.toList());

        List<Move> hocusDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.HOCUS, MoveType.DEFAULT_CHARACTER);
        assert hocusDefaultMoves != null;

        List<CharacterMove> characterMoves8 = createAndMergeCharacterMoves(hocusMoves, hocusDefaultMoves);

        // -------------------------------- Set up Ivette's Maestro D' ------------------------------ //
        PlaybookCreator playbookCreatorMaestroD = playbookCreatorService.findByPlaybookType(PlaybookType.MAESTRO_D);

        List<Look> maestroDLooks = getLooks(PlaybookType.MAESTRO_D);

        StatsBlock maestroDStatsBlock = getStatsBlock(PlaybookType.MAESTRO_D);

        Establishment establishmentDefault = Establishment.builder()
                .id(new ObjectId().toString())
                .uniqueType(UniqueType.ESTABLISHMENT)
                .securitiesCount(establishmentCreator.getDefaultSecuritiesCount())
                .regulars(establishmentCreator.getRegularsNames())
                .interestedParties(establishmentCreator.getInterestedPartyNames())
                .build();

        PlaybookUniques maestroUniqueDefault = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.ESTABLISHMENT)
                .establishment(establishmentDefault)
                .build();

        CastCrew castCrew1 = CastCrew.builder()
                .id(new ObjectId().toString())
                .name("crewey 1")
                .description("cool dude")
                .build();

        Establishment establishment = Establishment.builder()
                .id(new ObjectId().toString())
                .uniqueType(UniqueType.ESTABLISHMENT)
                .mainAttraction("luxury food")
                .bestRegular("Lamprey")
                .worstRegular("Toyota")
                .wantsItGone("Gams")
                .wantsInOnIt("Been")
                .oweForIt("Rolfball")
                .sideAttractions(List.of("music", "spectacle"))
                .atmospheres(List.of("bustle", "velvet", "fantasy"))
                .securitiesCount(establishmentCreator.getDefaultSecuritiesCount())
                .regulars(establishmentCreator.getRegularsNames())
                .interestedParties(establishmentCreator.getInterestedPartyNames())
                .securityOptions(List.of(securityOption4, securityOption3))
                .castAndCrew(List.of(castCrew1))
                .build();

        PlaybookUniques maestroUnique = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.ESTABLISHMENT)
                .establishment(establishment)
                .build();

        List<Move> maestroDMoves = moveService.findAllByPlaybookAndKind(PlaybookType.MAESTRO_D, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals(callThisHotName) ||
                        move.getName().equals(justGiveMotiveName)).collect(Collectors.toList());

        List<Move> maestroDDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.MAESTRO_D, MoveType.DEFAULT_CHARACTER);
        assert maestroDDefaultMoves != null;

        List<CharacterMove> characterMoves9 = createAndMergeCharacterMoves(maestroDMoves, maestroDDefaultMoves);

        // -------------------------------- Set up Sergio's Savvyhead ------------------------------ //
        PlaybookCreator playbookCreatorSavvyhead = playbookCreatorService.findByPlaybookType(PlaybookType.SAVVYHEAD);

        List<Look> savvyheadLooks = getLooks(PlaybookType.SAVVYHEAD);

        StatsBlock savvyheadDStatsBlock = getStatsBlock(PlaybookType.SAVVYHEAD);

        Workspace workspaceDefault = Workspace.builder()
                .id(new ObjectId().toString())
                .uniqueType(UniqueType.WORKSPACE)
                .projectInstructions(workspaceCreator.getProjectInstructions())
                .workspaceInstructions(workspaceCreator.getWorkspaceInstructions())
                .itemsCount(workspaceCreator.getDefaultItemsCount())
                .build();

        PlaybookUniques savvyheadUniqueDefault = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.WORKSPACE)
                .workspace(workspaceDefault)
                .build();

        List<Move> savvyheadMoves = moveService.findAllByPlaybookAndKind(PlaybookType.SAVVYHEAD, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals(thingsSpeakName) ||
                        move.getName().equals(bonefeelName)).collect(Collectors.toList());

        List<Move> savvyheadDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.SAVVYHEAD, MoveType.DEFAULT_CHARACTER);
        assert savvyheadDefaultMoves != null;

        List<CharacterMove> characterMoves10 = createAndMergeCharacterMoves(savvyheadMoves, savvyheadDefaultMoves);

        // -------------------------------- Set up Caesar's Skinner ------------------------------ //
        PlaybookCreator playbookCreatorSkinner = playbookCreatorService.findByPlaybookType(PlaybookType.SKINNER);

        List<Look> skinnerLooks = getLooks(PlaybookType.SKINNER);

        StatsBlock skinnerStatsBlock = getStatsBlock(PlaybookType.SKINNER);

        SkinnerGear skinnerGearDefault = SkinnerGear.builder()
                .id(new ObjectId().toString())
                .uniqueType(UniqueType.SKINNER_GEAR)
                .build();

        PlaybookUniques skinnerUniqueDefault = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.SKINNER_GEAR)
                .skinnerGear(skinnerGearDefault)
                .build();

        List<Move> skinnerMoves = moveService.findAllByPlaybookAndKind(PlaybookType.SKINNER, MoveType.CHARACTER)
                .stream().filter(move -> move.getName().equals(lostName) ||
                        move.getName().equals(artfulName)).collect(Collectors.toList());

        List<Move> skinnerDefaultMoves = moveService
                .findAllByPlaybookAndKind(PlaybookType.SKINNER, MoveType.DEFAULT_CHARACTER);
        assert skinnerDefaultMoves != null;

        List<CharacterMove> characterMoves11 = createAndMergeCharacterMoves(skinnerMoves, skinnerDefaultMoves);

        // -------------------------------- Create Sara's Angel ----------------------------------- //
        Character mockCharacter1 = Character.builder()
                .name("Doc")
                .playbook(PlaybookType.ANGEL)
                .looks(angelLooks)
                .gear(List.of("Shotgun", "Rusty screwdriver"))
                .statsBlock(angelStatsBlock1)
                .barter(2)
                .playbookUniques(angelUnique)
                .characterMoves(characterMoves)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(true)
                .vehicleCount(0)
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorAngel.getMoveChoiceCount())
                .build();

        // Complete Angel, for Game 6 and Game 7, so another Character can assign Hx during test
        Character mockCharacter1_2 = Character.builder()
                .name("Doc")
                .playbook(PlaybookType.ANGEL)
                .looks(angelLooks)
                .gear(List.of("Shotgun", "Rusty screwdriver"))
                .statsBlock(angelStatsBlock1)
                .barter(2)
                .playbookUniques(angelUnique)
                .characterMoves(characterMoves)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(true)
                .vehicleCount(0)
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorAngel.getMoveChoiceCount())
                .build();

        // -------------------------------- Create John's Battlebabe ----------------------------------- //
        Character mockCharacter2 = Character.builder()
                .name("Scarlet")
                .playbook(PlaybookType.BATTLEBABE)
                .looks(battlebabeLooks)
                .gear(List.of("Black leather boots", "Broken motorcycle helmet"))
                .statsBlock(battlebabeStatsBlock)
                .barter(2)
                .playbookUniques(battlebabeUnique)
                .characterMoves(characterMoves2)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(false)
                .vehicleCount(0)
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorBattlebabe.getMoveChoiceCount())
                .build();

        // For game 6 testing, John creates a Battlebabe from scratch

        // -------------------------------- Create Maya's Brainer ----------------------------------- //
        Character mockCharacter3 = Character.builder()
                .name("Smith")
                .playbook(PlaybookType.BRAINER)
                .looks(brainerLooks)
                .gear(List.of("Sharp kitchen knife", "Wireless radio"))
                .statsBlock(brainerStatsBlock)
                .barter(playbookCreatorBrainer.getGearInstructions().getStartingBarter())
                .playbookUniques(brainerUnique)
                .characterMoves(characterMoves3)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorBrainer.getDefaultVehicleCount())
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorBrainer.getMoveChoiceCount())
                .build();

        // Without Brainer Gear, for Game 6 testing
        Character mockCharacter3_2 = Character.builder()
                .id("maya-player-gamerole-2-brainer-character-id")
                .name("Smith")
                .playbook(PlaybookType.BRAINER)
                .looks(brainerLooks)
                .gear(List.of("Sharp kitchen knife", "Wireless radio"))
                .statsBlock(brainerStatsBlock)
                .playbookUniques(brainerUniqueDefault)
                .characterMoves(characterMoves3)
                .barter(playbookCreatorBrainer.getGearInstructions().getStartingBarter())
                .hasCompletedCharacterCreation(false)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorBrainer.getDefaultVehicleCount())
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorBrainer.getMoveChoiceCount())
                .build();

        // -------------------------------- Create Takeshi's Chopper ----------------------------------- //
        Character mockCharacter5 = Character.builder()
                .name("Dog")
                .playbook(PlaybookType.CHOPPER)
                .looks(chopperLooks)
                .gear(List.of("magnum (3-harm close reload loud)", "machete (3-harm hand messy"))
                .statsBlock(chopperStatsBlock)
                .barter(2)
                .playbookUniques(chopperUnique)
                .characterMoves(characterMoves5)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorChopper.getDefaultVehicleCount())
                .battleVehicleCount(0)
                .vehicles(List.of(chopperBike))
                .allowedPlaybookMoves(playbookCreatorChopper.getMoveChoiceCount())
                .build();

        // With default Gang, for Game 6 testing
        Character mockCharacter5_2 = Character.builder()
                .id("takeshi-player-gamerole-2-chopper-character-id")
                .name("Dog")
                .playbook(PlaybookType.CHOPPER)
                .looks(chopperLooks)
                .gear(List.of("magnum (3-harm close reload loud)", "machete (3-harm hand messy"))
                .statsBlock(chopperStatsBlock)
                .barter(2)
                .playbookUniques(chopperUniqueDefault)
                .characterMoves(characterMoves5)
                .hasCompletedCharacterCreation(false)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorChopper.getDefaultVehicleCount())
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorChopper.getMoveChoiceCount())
                .build();

        // -------------------------------- Create Ahmad's Driver ----------------------------------- //
        Character mockCharacter4 = Character.builder()
                .name("Phoenix")
                .playbook(PlaybookType.DRIVER)
                .looks(driverLooks)
                .gear(List.of("Leather jacket", ".38 revolver"))
                .statsBlock(driverStatsBlock)
                .barter(4)
                .characterMoves(characterMoves4)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(false)
                .vehicleCount(3)
                .battleVehicleCount(0)
                .vehicles(List.of(car1, car2, bike))
                .allowedPlaybookMoves(playbookCreatorDriver.getMoveChoiceCount())
                .build();

        // With no vehicles, for Game 6 testing
        Character mockCharacter4_2 = Character.builder()
                .id("ahmad-player-gamerole-2-driver-character-id")
                .name("Phoenix")
                .playbook(PlaybookType.DRIVER)
                .looks(driverLooks)
                .gear(List.of("Leather jacket", ".38 revolver"))
                .statsBlock(driverStatsBlock)
                .barter(4)
                .characterMoves(characterMoves4)
                .hasCompletedCharacterCreation(false)
                .hasPlusOneForward(false)
                .vehicleCount(3)
                .battleVehicleCount(0)
                .vehicles(new ArrayList<>())
                .allowedPlaybookMoves(playbookCreatorDriver.getMoveChoiceCount())
                .build();

        // ----------------------------- Create Mayama's Gunlugger -------------------------------- //
        // With Weapons, for Game 1 testing
        Character mockCharacter6 = Character.builder()
                .id("mayama-player-gamerole-2-gunlugger-character-id")
                .name("Batty")
                .playbook(PlaybookType.GUNLUGGER)
                .looks(gunluggerLooks)
                .gear(List.of("homemade chainmail"))
                .statsBlock(gunluggerStatsBlock)
                .barter(playbookCreatorGunlugger.getGearInstructions().getStartingBarter())
                .playbookUniques(gunluggerUnique)
                .characterMoves(characterMoves6)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorGunlugger.getDefaultVehicleCount())
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorGunlugger.getMoveChoiceCount())
                .build();

        // Without Weapons, for Game 6 testing
        Character mockCharacter6_2 = Character.builder()
                .id("mayama-player-gamerole-2-gunlugger-character-id")
                .name("Batty")
                .playbook(PlaybookType.GUNLUGGER)
                .looks(gunluggerLooks)
                .gear(List.of("homemade chainmail"))
                .statsBlock(gunluggerStatsBlock)
                .barter(playbookCreatorGunlugger.getGearInstructions().getStartingBarter())
                .playbookUniques(gunluggerUniqueDefault)
                .characterMoves(characterMoves6)
                .hasCompletedCharacterCreation(false)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorGunlugger.getDefaultVehicleCount())
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorGunlugger.getMoveChoiceCount())
                .build();

        // ----------------------------- Create Olayinka's Hardholder -------------------------------- //
        // Without Holding, for Game 6 testing
        Character mockCharacter7 = Character.builder()
                .id("olayinka-player-gamerole-2-hardholder-character-id")
                .name("Kobe")
                .playbook(PlaybookType.HARDHOLDER)
                .looks(hardholderLooks)
                .gear(List.of("9mm"))
                .statsBlock(hardholderStatsBlock)
                .barter(playbookCreatorHardholder.getGearInstructions().getStartingBarter())
                .playbookUniques(hardholderUniqueDefault)
                .characterMoves(characterMoves7)
                .hasCompletedCharacterCreation(false)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorHardholder.getDefaultVehicleCount())
                .battleVehicleCount(2)
                .allowedPlaybookMoves(playbookCreatorHardholder.getMoveChoiceCount())
                .build();

        // ----------------------------- Create Wilmer's Hocus -------------------------------- //
        // With Followers, for Game 1 testing
        Character mockCharacter8 = Character.builder()
                .id("wilmer-player-gamerole-2-hocus-character-id")
                .name("Vision")
                .playbook(PlaybookType.HOCUS)
                .looks(hocusLooks)
                .gear(List.of("tattered bible"))
                .statsBlock(hocusStatsBlock)
                .barter(playbookCreatorHocus.getGearInstructions().getStartingBarter())
                .playbookUniques(hocusUnique)
                .characterMoves(characterMoves8)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorHocus.getDefaultVehicleCount())
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorHocus.getMoveChoiceCount())
                .build();

        // Without Followers, for Game 6 testing
        Character mockCharacter8_2 = Character.builder()
                .id("wilmer-player-gamerole-2-hocus-character-id")
                .name("Vision")
                .playbook(PlaybookType.HOCUS)
                .looks(hocusLooks)
                .gear(List.of("tattered bible"))
                .statsBlock(hocusStatsBlock)
                .barter(playbookCreatorHocus.getGearInstructions().getStartingBarter())
                .playbookUniques(hocusUniqueDefault)
                .characterMoves(characterMoves8)
                .hasCompletedCharacterCreation(false)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorHocus.getDefaultVehicleCount())
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorHocus.getMoveChoiceCount())
                .build();

        // ----------------------------- Create Ivette's Maestro D' -------------------------------- //
        // With Establishment, for Game 1 testing
        Character mockCharacter9 = Character.builder()
                .id("ivette-player-gamerole-1-maestro-character-id")
                .name("Emmy")
                .playbook(PlaybookType.MAESTRO_D)
                .looks(maestroDLooks)
                .gear(List.of("rusty iron mic"))
                .statsBlock(maestroDStatsBlock)
                .barter(playbookCreatorMaestroD.getGearInstructions().getStartingBarter())
                .playbookUniques(maestroUnique)
                .characterMoves(characterMoves9)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorMaestroD.getDefaultVehicleCount())
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorMaestroD.getMoveChoiceCount())
                .build();

        // Without Establishment, for Game 6 testing
        Character mockCharacter9_2 = Character.builder()
                .id("ivette-player-gamerole-2-maestro-character-id")
                .name("Emmy")
                .playbook(PlaybookType.MAESTRO_D)
                .looks(maestroDLooks)
                .gear(List.of("rusty iron mic"))
                .statsBlock(maestroDStatsBlock)
                .barter(playbookCreatorMaestroD.getGearInstructions().getStartingBarter())
                .playbookUniques(maestroUniqueDefault)
                .characterMoves(characterMoves9)
                .hasCompletedCharacterCreation(false)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorMaestroD.getDefaultVehicleCount())
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorMaestroD.getMoveChoiceCount())
                .build();

        // ----------------------------- Create Sergio's Savvyhead -------------------------------- //
        // Without Workspace, for Game 6 testing
        Character mockCharacter10 = Character.builder()
                .id("sergio-player-gamerole-2-savvyhead-character-id")
                .name("Leah")
                .playbook(PlaybookType.SAVVYHEAD)
                .looks(savvyheadLooks)
                .gear(List.of("homemade crossbow"))
                .statsBlock(savvyheadDStatsBlock)
                .barter(playbookCreatorSavvyhead.getGearInstructions().getStartingBarter())
                .playbookUniques(savvyheadUniqueDefault)
                .characterMoves(characterMoves10)
                .hasCompletedCharacterCreation(false)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorSavvyhead.getDefaultVehicleCount())
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorSavvyhead.getMoveChoiceCount())
                .build();

        // ----------------------------- Create Caesar's Skinner -------------------------------- //
        // Without Skinner Gear, for Game 6 testing
        Character mockCharacter11 = Character.builder()
                .id("caesar-player-gamerole-2-skinner-character-id")
                .name("Venus")
                .playbook(PlaybookType.SKINNER)
                .looks(skinnerLooks)
                .gear(List.of("expensive red dress"))
                .statsBlock(skinnerStatsBlock)
                .barter(playbookCreatorSkinner.getGearInstructions().getStartingBarter())
                .playbookUniques(skinnerUniqueDefault)
                .characterMoves(characterMoves11)
                .hasCompletedCharacterCreation(false)
                .hasPlusOneForward(false)
                .vehicleCount(playbookCreatorSkinner.getDefaultVehicleCount())
                .battleVehicleCount(0)
                .allowedPlaybookMoves(playbookCreatorSkinner.getMoveChoiceCount())
                .build();

        // ----------------------- Add Characters to players and save ----------------------------- //

        // Game 1
        saveCharacter(saraAsPlayer, harm, mockCharacter1);
        saveCharacter(johnAsPlayer, harm, mockCharacter2);
        saveCharacter(mayaAsPlayer, harm, mockCharacter3);
        saveCharacter(ahmadAsPlayer, harm, mockCharacter4);
        saveCharacter(takeshiAsPlayer, harm, mockCharacter5);
        saveCharacter(maramaAsPlayer, harm, mockCharacter6);
        saveCharacter(wilmerAsPlayer, harm, mockCharacter8);
        saveCharacter(ivetteAsPlayer, harm, mockCharacter9);

        // Game 6
        // Add Sara's character to her other GameRole as well
        saveCharacter(saraAsPlayer2, harm, mockCharacter1_2);
        // John creates Battlebabe from scratch in test
        saveCharacter(mayaAsPlayer2, harm, mockCharacter3_2);
        saveCharacter(ahmadAsPlayer2, harm, mockCharacter4_2);
        saveCharacter(takeshiAsPlayer2, harm, mockCharacter5_2);
        saveCharacter(maramaAsPlayer2, harm, mockCharacter6_2);
        saveCharacter(olayinkaAsPlayer, harm, mockCharacter7);
        saveCharacter(wilmerAsPlayer2, harm, mockCharacter8_2);
        saveCharacter(ivetteAsPlayer2, harm, mockCharacter9_2);
        saveCharacter(sergioAsPlayer, harm, mockCharacter10);
        saveCharacter(caesarAsPlayer, harm, mockCharacter11);

        // Game 7
        saveCharacter(saraAsPlayer3, harm, mockCharacter1_2);
        saveCharacter(johnAsPlayer3, harm, mockCharacter2);
        saveCharacter(ahmadAsPlayer3, harm, mockCharacter4);
        saveCharacter(takeshiAsPlayer3, harm, mockCharacter5);
        saveCharacter(maramaAsPlayer3, harm, mockCharacter6);
        saveCharacter(wilmerAsPlayer3, harm, mockCharacter8);
        saveCharacter(ivetteAsPlayer3, harm, mockCharacter9);

    }

    @Override
    public void loadHx() {
        // Game 1
        GameRole saraAsPlayer = gameRoleService.findById(SARA_AS_PLAYER_ID);
        assert saraAsPlayer != null;
        GameRole johnAsPlayer = gameRoleService.findById(JOHN_AS_PLAYER_ID);
        assert johnAsPlayer != null;
        GameRole mayaAsPlayer = gameRoleService.findById(MAYA_AS_PLAYER_ID);
        assert mayaAsPlayer != null;
        GameRole ahmadAsPlayer = gameRoleService.findById(AHMAD_AS_PLAYER_ID);
        assert ahmadAsPlayer != null;
        GameRole takeshiAsPlayer = gameRoleService.findById(TAKESHI_AS_PLAYER_ID);
        assert takeshiAsPlayer != null;
        GameRole maramaAsPlayer = gameRoleService.findById(MARAMA_AS_PLAYER_ID_1);
        assert maramaAsPlayer != null;
        GameRole wilmerAsPlayer = gameRoleService.findById(WILMER_AS_PLAYER_ID_1);
        assert wilmerAsPlayer != null;
        GameRole ivetteAsPlayer = gameRoleService.findById(IVETTE_AS_PLAYER_ID_1);
        assert ivetteAsPlayer != null;

        // Game 7
        GameRole saraAsPlayer3 = gameRoleService.findById(SARA_AS_PLAYER_ID_3);
        assert saraAsPlayer3 != null;
        GameRole johnAsPlayer3 = gameRoleService.findById(JOHN_AS_PLAYER_ID_3);
        assert johnAsPlayer3 != null;
        GameRole ahmadAsPlayer3 = gameRoleService.findById(AHMAD_AS_PLAYER_ID_3);
        assert ahmadAsPlayer3 != null;
        GameRole takeshiAsPlayer3 = gameRoleService.findById(TAKESHI_AS_PLAYER_ID_3);
        assert takeshiAsPlayer3 != null;
        GameRole maramaAsPlayer3 = gameRoleService.findById(MARAMA_AS_PLAYER_ID_3);
        assert maramaAsPlayer3 != null;
        GameRole wilmerAsPlayer3 = gameRoleService.findById(WILMER_AS_PLAYER_ID_3);
        assert wilmerAsPlayer3 != null;
        GameRole ivetteAsPlayer3 = gameRoleService.findById(IVETTE_AS_PLAYER_ID_3);
        assert ivetteAsPlayer3 != null;

        // Game 1
        Character doc = saraAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        Character scarlet = johnAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        Character smith = mayaAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        Character nee = ahmadAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        Character dog = takeshiAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        Character batty = maramaAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        Character vision = wilmerAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        Character emmy = ivetteAsPlayer.getCharacters().stream().findFirst().orElseThrow();

        // Game 7
        Character docB = saraAsPlayer3.getCharacters().stream().findFirst().orElseThrow();
        Character scarletB = johnAsPlayer3.getCharacters().stream().findFirst().orElseThrow();
        Character neeB = ahmadAsPlayer3.getCharacters().stream().findFirst().orElseThrow();
        Character dogB = takeshiAsPlayer3.getCharacters().stream().findFirst().orElseThrow();
        Character battyB = maramaAsPlayer3.getCharacters().stream().findFirst().orElseThrow();
        Character visionB = wilmerAsPlayer3.getCharacters().stream().findFirst().orElseThrow();
        Character emmyB = ivetteAsPlayer3.getCharacters().stream().findFirst().orElseThrow();


        // ------------------------------ Doc's Hx --------------------------------- //
        HxStat doc1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(-2).build();

        HxStat doc2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(2).build();

        HxStat doc3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(3).build();

        HxStat doc4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(dog.getId()).characterName(dog.getName()).hxValue(1).build();
        doc.setHxBlock(List.of(doc1, doc2, doc3, doc4));
        docB.setHxBlock(List.of(doc1, doc2, doc3, doc4));

        // ------------------------------ Scarlet's Hx --------------------------------- //
        HxStat scarlet1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(-1).build();

        HxStat scarlet2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(3).build();

        HxStat scarlet3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(-1).build();

        HxStat scarlet4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(dog.getId()).characterName(dog.getName()).hxValue(0).build();

        scarlet.setHxBlock(List.of(scarlet1, scarlet2, scarlet3, scarlet4));
        scarletB.setHxBlock(List.of(scarlet1, scarlet2, scarlet3, scarlet4));

        // ------------------------------ Smith's Hx --------------------------------- //
        HxStat smith1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(2).build();

        HxStat smith2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(2).build();

        HxStat smith3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(3).build();

        HxStat smith4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(dog.getId()).characterName(dog.getName()).hxValue(1).build();

        smith.setHxBlock(List.of(smith1, smith2, smith3, smith4));

        // ------------------------------ Nee's Hx --------------------------------- //
        HxStat nee1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(-2).build();

        HxStat nee2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(2).build();

        HxStat nee3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(3).build();

        HxStat nee4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(dog.getId()).characterName(dog.getName()).hxValue(-1).build();

        nee.setHxBlock(List.of(nee1, nee2, nee3, nee4));
        neeB.setHxBlock(List.of(nee1, nee2, nee3, nee4));

        // ------------------------------ Dog's Hx --------------------------------- //
        HxStat dog1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(-2).build();

        HxStat dog2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(2).build();

        HxStat dog3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(3).build();

        HxStat dog4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(-1).build();

        dog.setHxBlock(List.of(dog1, dog2, dog3, dog4));
        dogB.setHxBlock(List.of(dog1, dog2, dog3, dog4));

        // ------------------------------ Batty's Hx --------------------------------- //
        HxStat batty1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(-1).build();

        HxStat batty2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(2).build();

        HxStat batty3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(2).build();

        HxStat batty4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(-1).build();

        batty.setHxBlock(List.of(batty1, batty2, batty3, batty4));
        battyB.setHxBlock(List.of(batty1, batty2, batty3, batty4));

        // ------------------------------ Vision's Hx --------------------------------- //
        HxStat vision1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(-1).build();

        HxStat vision2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(2).build();

        HxStat vision3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(2).build();

        HxStat vision4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(-1).build();

        vision.setHxBlock(List.of(vision1, vision2, vision3, vision4));
        visionB.setHxBlock(List.of(vision1, vision2, vision3, vision4));

        // ------------------------------ Emmy's Hx --------------------------------- //
        HxStat emmy1 = HxStat.builder().id(new ObjectId().toString())
                .characterId(doc.getId()).characterName(doc.getName()).hxValue(-1).build();

        HxStat emmy2 = HxStat.builder().id(new ObjectId().toString())
                .characterId(scarlet.getId()).characterName(scarlet.getName()).hxValue(2).build();

        HxStat emmy3 = HxStat.builder().id(new ObjectId().toString())
                .characterId(smith.getId()).characterName(smith.getName()).hxValue(2).build();

        HxStat emmy4 = HxStat.builder().id(new ObjectId().toString())
                .characterId(nee.getId()).characterName(nee.getName()).hxValue(-1).build();

        emmy.setHxBlock(List.of(emmy1, emmy2, emmy3, emmy4));
        emmyB.setHxBlock(List.of(emmy1, emmy2, emmy3, emmy4));

        // ------------------------------ Save to db --------------------------------- //
        characterService.saveAll(List.of(doc, scarlet, smith, nee, dog, batty, vision, emmy,
                docB, scarletB, neeB, dogB, battyB, visionB, emmyB));
        gameRoleService.saveAll(List.of(
                saraAsPlayer,
                johnAsPlayer,
                mayaAsPlayer,
                ahmadAsPlayer,
                takeshiAsPlayer,
                maramaAsPlayer,
                wilmerAsPlayer,
                ivetteAsPlayer,
                saraAsPlayer3,
                johnAsPlayer3,
                ahmadAsPlayer3,
                takeshiAsPlayer3,
                maramaAsPlayer3,
                wilmerAsPlayer3,
                ivetteAsPlayer3
        ));
    }

    @Override
    public void loadThreats() {
        GameRole daveAsMC = gameRoleService.findAllByUserId(AUTH0_ID_1)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).findFirst().orElseThrow();
        GameRole daveAsMC4 = gameRoleService.findAllByUserId(AUTH0_ID_1)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).filter(gameRole -> gameRole.getGameId().equals(MOCK_GAME_7_ID)).findFirst().orElseThrow();

        GameRole saraAsMC = gameRoleService.findAllByUserId(AUTH0_ID_2)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).findFirst().orElseThrow();

        ThreatCreator threatCreator = threatCreatorService.findAll().get(0);
        assert threatCreator != null;

        Threat mockThreat1 = Threat.builder()
                .id(new ObjectId().toString())
                .name("Tum Tum")
                .threatKind(ThreatType.WARLORD)
                .impulse(threatCreator.getThreats().stream()
                        .filter(threatCreatorContent -> threatCreatorContent.getThreatType().equals(ThreatType.WARLORD))
                        .findFirst().orElseThrow().getImpulses().get(0))
                .description("consectetur adipiscing elit. Cras semper augue est, vel consequat dolor volutpat in")
                .stakes("Maecenas vitae consequat justo, quis sollicitudin nulla. Phasellus pulvinar nunc eget mauris tristique, ut aliquam felis mattis. Nulla ultricies feugiat arcu non facilisis.")
                .build();
        Threat mockThreat2 = Threat.builder()
                .id(new ObjectId().toString())
                .name("Gnarly")
                .threatKind(ThreatType.GROTESQUE)
                .impulse(threatCreator.getThreats().stream()
                        .filter(threatCreatorContent -> threatCreatorContent.getThreatType().equals(ThreatType.GROTESQUE))
                        .findFirst().orElseThrow().getImpulses().get(0))
                .description("Maecenas tempus ac felis at sollicitudin. Etiam pulvinar, nibh eget fringilla pretium, sem sem ultricies augue, vitae condimentum enim nibh nec mi.")
                .build();
        Threat mockThreat3 = Threat.builder()
                .id(new ObjectId().toString())
                .name("Fleece")
                .threatKind(ThreatType.BRUTE)
                .impulse(threatCreator.getThreats().stream()
                        .filter(threatCreatorContent -> threatCreatorContent.getThreatType().equals(ThreatType.BRUTE))
                        .findFirst().orElseThrow().getImpulses().get(0))
                .description("Maecenas tempus ac felis at sollicitudin. Etiam pulvinar, nibh eget fringilla pretium, sem sem ultricies augue, vitae condimentum enim nibh nec mi.")
                .build();
        Threat mockThreat4 = Threat.builder()
                .id(new ObjectId().toString())
                .name("Wet Rot")
                .threatKind(ThreatType.AFFLICTION)
                .impulse(threatCreator.getThreats().stream()
                        .filter(threatCreatorContent -> threatCreatorContent.getThreatType().equals(ThreatType.AFFLICTION))
                        .findFirst().orElseThrow().getImpulses().get(0))
                .description("Maecenas tempus ac felis at sollicitudin. Etiam pulvinar, nibh eget fringilla pretium, sem sem ultricies augue, vitae condimentum enim nibh nec mi.")
                .build();


        daveAsMC.getThreats().add(mockThreat1);
        daveAsMC.getThreats().add(mockThreat2);
        daveAsMC4.getThreats().add(mockThreat1);
        daveAsMC4.getThreats().add(mockThreat2);
        saraAsMC.getThreats().add(mockThreat3);
        saraAsMC.getThreats().add(mockThreat4);
        gameRoleService.saveAll(List.of(daveAsMC, saraAsMC, daveAsMC4));
    }

    @Override
    public void loadNpcs() {
        GameRole daveAsMC = gameRoleService.findAllByUserId(AUTH0_ID_1)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).findFirst().orElseThrow();
        GameRole daveAsMC4 = gameRoleService.findAllByUserId(AUTH0_ID_1)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC))
                .filter(gameRole -> gameRole.getGameId().equals(MOCK_GAME_7_ID)).findFirst().orElseThrow();
        GameRole saraAsMC = gameRoleService.findAllByUserId(AUTH0_ID_2)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).findFirst().orElseThrow();

        ThreatCreator threatCreator = threatCreatorService.findAll().get(0);
        assert threatCreator != null;

        Npc mockNpc1 = Npc.builder()
                .id(new ObjectId().toString())
                .name("Vision")
                .description("Badass truck; driver").build();
        Npc mockNpc2 = Npc.builder()
                .id(new ObjectId().toString())
                .name("Nbeke").build();
        Npc mockNpc3 = Npc.builder()
                .id(new ObjectId().toString())
                .name("Batty")
                .description("Overly polite gun for hire").build();
        Npc mockNpc4 = Npc.builder()
                .id(new ObjectId().toString())
                .name("Farley").build();

        daveAsMC.getNpcs().add(mockNpc1);
        daveAsMC.getNpcs().add(mockNpc2);
        daveAsMC4.getNpcs().add(mockNpc1);
        daveAsMC4.getNpcs().add(mockNpc2);
        saraAsMC.getNpcs().add(mockNpc3);
        saraAsMC.getNpcs().add(mockNpc4);
        gameRoleService.saveAll(List.of(saraAsMC, daveAsMC, daveAsMC4));
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
        List<Look> looks = lookService.findAllByPlaybookType(playbookType);
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
        StatsOption statsOption = statsOptionService.findAllByPlaybookType(playbookType).get(0);
        assert statsOption != null;

        CharacterStat cool = CharacterStat.builder().id(new ObjectId().toString())
                .stat(StatType.COOL).value(statsOption.getCOOL()).isHighlighted(false).build();
        CharacterStat hard = CharacterStat.builder().id(new ObjectId().toString())
                .stat(StatType.HARD).value(statsOption.getHARD()).isHighlighted(true).build();
        CharacterStat hot = CharacterStat.builder().id(new ObjectId().toString())
                .stat(StatType.HOT).value(statsOption.getHOT()).isHighlighted(true).build();
        CharacterStat sharp = CharacterStat.builder().id(new ObjectId().toString())
                .stat(StatType.SHARP).value(statsOption.getSHARP()).isHighlighted(false).build();
        CharacterStat weird = CharacterStat.builder().id(new ObjectId().toString())
                .stat(StatType.WEIRD).value(statsOption.getWEIRD()).isHighlighted(false).build();

        return StatsBlock.builder().id(new ObjectId().toString())
                .statsOptionId(statsOption.getId())
                .stats(List.of(cool, hard, hot, sharp, weird))
                .build();
    }

    private void saveCharacter(GameRole gameRole, CharacterHarm harm, Character character) {
        if (gameRole.getCharacters().size() == 0) {
            harm.setId(new ObjectId().toString());
            character.setHarm(harm);
            character.setExperience(0);
            character.setAllowedOtherPlaybookMoves(0);
            gameRole.getCharacters().add(character);
            characterService.save(character);
            gameRoleService.save(gameRole);
        }
    }
}
