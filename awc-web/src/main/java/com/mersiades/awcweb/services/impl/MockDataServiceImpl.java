package com.mersiades.awcweb.services.impl;

import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.models.uniquecreators.GangCreator;
import com.mersiades.awccontent.services.*;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.AngelKit;
import com.mersiades.awcdata.models.uniques.BrainerGear;
import com.mersiades.awcdata.models.uniques.CustomWeapons;
import com.mersiades.awcdata.models.uniques.Gang;
import com.mersiades.awcdata.repositories.CharacterRepository;
import com.mersiades.awcdata.repositories.UserRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import com.mersiades.awcweb.services.MockDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mersiades.awccontent.content.PlaybookCreatorsContent.brainerGearCreator;

@Profile({"dev", "cypress"})
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
    private final CharacterRepository characterRepository;

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

    @Override
    public void loadMockData() {
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
                .build();

        PlaybookUniques chopperUnique = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(UniqueType.GANG)
                .gang(gang)
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

        // -------------------------------- Create Maya's Brainer ----------------------------------- //
        Character mockCharacter3 = Character.builder()
                .name("Smith")
                .playbook(PlaybookType.BRAINER)
                .looks(brainerLooks)
                .gear(List.of("Sharp kitchen knife", "Wireless radio"))
                .statsBlock(brainerStatsBlock)
                .barter(2)
                .playbookUniques(brainerUnique)
                .characterMoves(characterMoves3)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(false)
                .vehicleCount(0)
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
                .vehicleCount(1)
                .battleVehicleCount(0)
                .vehicles(List.of(chopperBike))
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

        // ----------------------- Add Characters to players and save ----------------------------- //

        saveCharacter(saraAsPlayer, harm, mockCharacter1);
        saveCharacter(johnAsPlayer, harm, mockCharacter2);
        saveCharacter(mayaAsPlayer, harm, mockCharacter3);
        saveCharacter(ahmadAsPlayer, harm, mockCharacter4);
        saveCharacter(takeshiAsPlayer, harm, mockCharacter5);

        // Add Sara's character to her other GameRole as well
        saveCharacter(saraAsPlayer2, harm, mockCharacter1_2);
    }

    @Override
    public void loadHx() {
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

        Character doc = saraAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert doc != null;
        Character scarlet = johnAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert scarlet != null;
        Character smith = mayaAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert smith != null;
        Character nee = ahmadAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert nee != null;
        Character dog = takeshiAsPlayer.getCharacters().stream().findFirst().orElseThrow();
        assert dog != null;

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

        // ------------------------------ Save to db --------------------------------- //
        characterService.saveAll(List.of(doc, scarlet, smith, nee, dog));
        gameRoleService.saveAll(List.of(saraAsPlayer, johnAsPlayer, mayaAsPlayer, ahmadAsPlayer, takeshiAsPlayer));
    }

    @Override
    public void loadThreats() {
        GameRole daveAsMC = gameRoleService.findAllByUserId(KEYCLOAK_ID_1)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).findFirst().orElseThrow();
        assert daveAsMC != null;
        GameRole saraAsMC = gameRoleService.findAllByUserId(KEYCLOAK_ID_2)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).findFirst().orElseThrow();
        assert saraAsMC != null;
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
        saraAsMC.getThreats().add(mockThreat3);
        saraAsMC.getThreats().add(mockThreat4);
        gameRoleService.saveAll(List.of(daveAsMC, saraAsMC));
    }

    @Override
    public void loadNpcs() {
        GameRole daveAsMC = gameRoleService.findAllByUserId(KEYCLOAK_ID_1)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).findFirst().orElseThrow();
        assert daveAsMC != null;
        GameRole saraAsMC = gameRoleService.findAllByUserId(KEYCLOAK_ID_2)
                .stream().filter(gameRole -> gameRole.getRole().equals(RoleType.MC)).findFirst().orElseThrow();
        assert saraAsMC != null;
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
        saraAsMC.getNpcs().add(mockNpc3);
        saraAsMC.getNpcs().add(mockNpc4);
        gameRoleService.saveAll(List.of(saraAsMC, daveAsMC));
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
