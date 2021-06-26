package com.mersiades.awcdata.services.impl;

import com.mersiades.awccontent.enums.RoleType;
import com.mersiades.awccontent.enums.StatType;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.services.MoveService;
import com.mersiades.awcdata.enums.ScriptChangeType;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.AngelKit;
import com.mersiades.awcdata.models.uniques.Followers;
import com.mersiades.awcdata.models.uniques.Holding;
import com.mersiades.awcdata.repositories.GameRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.mersiades.awccontent.content.MovesContent.*;
import static com.mersiades.awccontent.enums.StatType.*;
import static com.mersiades.awcdata.enums.ScriptChangeType.*;
import static com.mersiades.awcdata.services.impl.GameServiceImpl.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceImplTest {

    public static final String MOCK_GAME_ID_1 = "mock-game-id-1";
    public static final String MOCK_GAMEROLE_ID = "mock-gamerole-id";

    @Mock
    GameRepository gameRepository;

    @Mock
    UserService userService;

    @Mock
    GameRoleService gameRoleService;

    @Mock
    CharacterService characterService;

    @Mock
    MoveService moveService;

    GameService gameService;

    Game mockGame1;

    GameRole mockGameRole;

    GameRole mockGameRole2;

    User mockMc;
    User mockPlayer;

    StatsBlock mockStatsBlock;

    HxStat mockHxStat1;

    HxStat mockHxStat2;

    CharacterHarm mockHarm;

    Character mockCharacter;

    Character mockCharacter2;

    CharacterStat mockCool;
    CharacterStat mockHard;
    CharacterStat mockHot;
    CharacterStat mockSharp;
    CharacterStat mockWeird;

    @BeforeEach
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
        mockMc = User.builder().id("mock-user-id").email("mock-email").displayName("mock-displayname").build();
        mockPlayer = User.builder().id("mock-user-id-2").email("mock-email-2").displayName("mock-displayname-2").build();

        mockCool = CharacterStat.builder()
                .id(new ObjectId().toString())
                .stat(COOL)
                .value(1)
                .isHighlighted(false)
                .build();
        mockHard = CharacterStat.builder()
                .id(new ObjectId().toString())
                .stat(HARD)
                .value(1)
                .isHighlighted(false)
                .build();
        mockHot = CharacterStat.builder()
                .id(new ObjectId().toString())
                .stat(HOT)
                .value(1)
                .isHighlighted(false)
                .build();

        mockSharp = CharacterStat.builder()
                .id(new ObjectId().toString())
                .stat(SHARP)
                .value(1)
                .isHighlighted(false)
                .build();
        mockWeird = CharacterStat.builder()
                .id(new ObjectId().toString())
                .stat(WEIRD)
                .value(1)
                .isHighlighted(false)
                .build();

        mockStatsBlock = StatsBlock.builder()
                .id(new ObjectId().toString())
                .stats(List.of(mockCool, mockHard, mockHot, mockSharp, mockWeird))
                .build();

        mockHarm = CharacterHarm.builder()
                .id(new ObjectId().toString())
                .value(0)
                .build();

        mockCharacter = Character.builder()
                .id("mock-character-id-1")
                .name("mock-character-name-1")
                .statsBlock(mockStatsBlock)
                .hasPlusOneForward(false)
                .harm(mockHarm)
                .experience(0)
                .build();

        mockCharacter2 = Character.builder()
                .id("mock-character-id-2")
                .name("mock-character-name-2")
                .harm(mockHarm)
                .experience(0)
                .build();

        mockHxStat2 = HxStat.builder()
                .id(new ObjectId().toString())
                .hxValue(1)
                .characterName(mockCharacter.getName())
                .characterId(mockCharacter.getId())
                .build();

        mockHxStat1 = HxStat.builder()
                .id(new ObjectId().toString())
                .hxValue(1)
                .characterName(mockCharacter2.getName())
                .characterId(mockCharacter2.getId())
                .build();

        mockCharacter.setHxBlock(List.of(mockHxStat1));
        mockCharacter2.setHxBlock(List.of(mockHxStat2));
        mockGameRole = GameRole.builder().id(MOCK_GAMEROLE_ID)
                .role(RoleType.MC)
                .gameId(MOCK_GAME_ID_1)
                .gameName("Michael's Mock Game")
                .userId(mockMc.getId()).build();
        mockGameRole2 = GameRole.builder()
                .id("mock-game-role-id-2")
                .role(RoleType.PLAYER)
                .gameId(MOCK_GAME_ID_1)
                .gameName("Michael's Mock Game")
                .characters(List.of(mockCharacter2))
                .userId(mockPlayer.getId())
                .build();

        List<GameRole> gameRoles = new ArrayList<>();
        gameRoles.add(mockGameRole);
        gameRoles.add(mockGameRole2);
        mockGame1 = Game.builder().id(MOCK_GAME_ID_1)
                .name("Michael's Mock Game")
                .mc(mockMc)
                .hasFinishedPreGame(false)
                .showFirstSession(false)
                .gameRoles(gameRoles).build();
        gameService = new GameServiceImpl(gameRepository, userService, gameRoleService, characterService, moveService);
    }

    @Test
    void shouldFindAllGames() {
        // Given
        Game mockGame2 = new Game();
        when(gameRepository.findAll()).thenReturn(List.of(mockGame1, mockGame2));

        // When
        List<Game> returnedGames = gameService.findAll();

        // Then
        assert returnedGames != null;
        assertEquals(2, returnedGames.size());
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    void shouldFindGameById() {
        // Given
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));

        // When
        Game returnedGame = gameService.findById(MOCK_GAME_ID_1);

        // Then
        assertNotNull(returnedGame, "Null Game returned");
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, never()).findAll();
    }

    @Test
    void shouldSaveGame() {
        // Given
        when(gameRepository.save(any())).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.save(mockGame1);

        // Then
        assert returnedGame != null;
        assertEquals(MOCK_GAME_ID_1, returnedGame.getId());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldSaveAllGames() {
        // Given
        Game mockGame2 = new Game();
        when(gameRepository.saveAll(anyIterable())).thenReturn(List.of(mockGame1, mockGame2));

        // When
        List<Game> savedGames = gameService.saveAll(List.of(mockGame1, mockGame2));

        // Then
        assert savedGames != null;
        assertEquals(2, savedGames.size());
        verify(gameRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteGame() {
        // When
        gameService.delete(mockGame1);

        // Then
        verify(gameRepository, times(1)).delete(any(Game.class));
    }

    @Test
    void shouldDeleteGameById() {
        // When
        gameService.deleteById(MOCK_GAME_ID_1);

        // Then
        verify(gameRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldFindGameByGameRoles() {
        // Given
        when(gameRepository.findByGameRoles(any())).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.findByGameRoles(mockGameRole);

        // Then
        assert returnedGame != null;
        assertEquals(MOCK_GAMEROLE_ID, returnedGame.getGameRoles().stream().findFirst().orElseThrow().getId());
        verify(gameRepository, times(1)).findByGameRoles(any(GameRole.class));
    }

    @Test
    void shouldFindAndDeleteById() {


    }

    @Test
    void shouldFindAllGamesForInvitee() {
        // Given
        String mockInvitee = "mock@invitee.com";
        Game mockGame2 = Game.builder().invitees(Collections.singletonList(mockInvitee)).build();
        mockGame1.getInvitees().add(mockInvitee);
        when(gameRepository.findAllByInviteesContaining(anyString())).thenReturn(List.of(mockGame1, mockGame2));

        // When
        List<Game> returnedGames = gameService.findAllByInvitee(mockInvitee);

        // Then
        assert returnedGames != null;
        assertEquals(2, returnedGames.size());
        verify(gameRepository, times(1)).findAllByInviteesContaining(anyString());
    }

    // ---------------------------------------------- Game-related -------------------------------------------- //

    @Test
    void shouldCreateGameForMc() throws Exception {
        // Given
        String mockGameName = "mock-game-name";
        when(userService.findOrCreateUser(anyString(), anyString(), anyString())).thenReturn(mockMc);
        when(gameRepository.save(any())).thenReturn(mockGame1);
        when(gameRoleService.save(any())).thenReturn(mockGameRole);

        // When
        Game returnedGame = gameService.createGameWithMC(mockMc.getId(),
                mockMc.getDisplayName(),
                mockMc.getEmail(),
                mockGameName);

        // Then
        assert returnedGame != null;
        assertEquals(mockGameName, returnedGame.getName());
        assertEquals(mockMc.getId(), returnedGame.getMc().getId());
        verify(userService, times(1)).findOrCreateUser(anyString(),anyString(),anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
        verify(gameRoleService, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldSetGameName() {
        // Given
        String newName = "New Game Name";
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.setGameName(mockGame1.getId(), newName);

        // Then
        assert returnedGame != null;
        assertEquals(mockGame1.getName(), returnedGame.getName());
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));

    }

    @Test
    void shouldAddInviteeToGame() {
        // Given
        String mockInvitee = "mock@invitee.com";
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.addInvitee(mockGame1.getId(), mockInvitee);

        // Then
        assert returnedGame != null;
        assertTrue(returnedGame.getInvitees().contains(mockInvitee));
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldNOTAddInvitee_alreadyAPlayer() {
        // Given
        String mockInvitee = "mock@invitee.com";
        User player = User.builder()
                .id("user-id")
                .email(mockInvitee)
                .displayName(mockInvitee)
                .build();
        mockGame1.getPlayers().add(player);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.addInvitee(mockGame1.getId(), mockInvitee);

        // Then
        assert returnedGame != null;
        assertFalse(returnedGame.getInvitees().contains(mockInvitee));
        verify(gameRepository, times(0)).save(any(Game.class));
    }

    @Test
    void shouldNOTAddInvitee_alreadyInvited() {
        // Given
        String mockInvitee = "mock@invitee.com";
        mockGame1.getInvitees().add(mockInvitee);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.addInvitee(mockGame1.getId(), mockInvitee);

        // Then
        assert returnedGame != null;
        assertTrue(returnedGame.getInvitees().contains(mockInvitee));
        verify(gameRepository, times(0)).save(any(Game.class));
    }

    @Test
    void shouldNOTAddInvitee_inviteeIsMC() {
        // Given
        String mockInvitee = "mock@invitee.com";
        User mc = User.builder()
                .id("user-id")
                .email(mockInvitee)
                .displayName(mockInvitee)
                .build();
        mockGame1.setMc(mc);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.addInvitee(mockGame1.getId(), mockInvitee);

        // Then
        assert returnedGame != null;
        assertFalse(returnedGame.getInvitees().contains(mockInvitee));
        verify(gameRepository, times(0)).save(any(Game.class));
    }

    @Test
    void shouldRemoveInviteeFromGame() {
        // Given
        String mockInvitee = "mock@invitee.com";
        mockGame1.getInvitees().add(mockInvitee);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.removeInvitee(mockGame1.getId(), mockInvitee);

        // Then
        assert returnedGame != null;
        assertFalse(returnedGame.getInvitees().contains(mockInvitee));
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldRemovePlayerFromGame() {
        // Given
        mockPlayer.getGameRoles().add(mockGameRole2);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);
        when(userService.findById(anyString())).thenReturn(mockPlayer);
        when(userService.save(any(User.class))).thenReturn(mockPlayer);
        doNothing().when(gameRoleService).delete(any(GameRole.class));

        // When
        Game returnedGame = gameService.removePlayer(mockGame1.getId(), mockPlayer.getId());

        // Then
        assert returnedGame != null;
        assertFalse(returnedGame.getPlayers().contains(mockPlayer));
        assertFalse(returnedGame.getGameRoles().contains(mockGameRole2));
        verify(gameRepository, times(1)).findById(anyString());
        verify(userService, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
        verify(userService, times(1)).save(any(User.class));
        verify(gameRoleService, times(1)).delete(any(GameRole.class));
    }

    @Test
    void shouldAddCommsAppToGame() {
        // Given
        String mockCommsApp = "Discord";
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.addCommsApp(mockGame1.getId(), mockCommsApp);

        // Then
        assert returnedGame != null;
        assertEquals(returnedGame.getCommsApp(), mockCommsApp);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldAddCommsUrlToGame() {
        // Given
        String mockCommsUrl = "https://mock-url.com";
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.addCommsUrl(mockGame1.getId(), mockCommsUrl);

        // Then
        assert returnedGame != null;
        assertEquals(returnedGame.getCommsUrl(), mockCommsUrl);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    // This should be an integration test, not unit
    @Test
    void shouldAddNewUserToGame() throws Exception {
        // Given
        User mockNewUser = User.builder().id("mock-new-user-id").displayName("new-displayname").email("new-email").build();
        mockGame1.getInvitees().add("mock-invitee-email");
        when(userService.findOrCreateUser(anyString(), anyString(), anyString())).thenReturn(mockNewUser);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);
        when(gameRoleService.save(any())).thenReturn(mockGameRole);

        // When
        Game returnedGame = gameService.addUserToGame(mockGame1.getId(),
                mockNewUser.getId(),
                mockNewUser.getDisplayName(),
                mockNewUser.getEmail());

        // Then
        assert returnedGame != null;
        assertEquals(1, returnedGame.getPlayers().size());
        assertEquals(3, returnedGame.getGameRoles().size());
        verify(userService, times(1)).findOrCreateUser(anyString(),anyString(),anyString());
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
        verify(gameRoleService, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldNotAddNewUserToGame_alreadyAsPlayer() throws Exception {
        // Given
        mockGame1.getPlayers().add(mockPlayer);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(userService.findOrCreateUser(anyString(), anyString(), anyString())).thenReturn(mockPlayer);

        // When
        Game returnedGame = gameService.addUserToGame(mockGame1.getId(),
                mockPlayer.getId(),
                mockPlayer.getDisplayName(),
                mockPlayer.getEmail());

        // Then
        assert returnedGame != null;
        assertEquals(2, returnedGame.getGameRoles().size());
        assertEquals(1, returnedGame.getPlayers().size());

        verify(userService, times(1)).findOrCreateUser(anyString(),anyString(),anyString());
        verify(gameRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldFindGameByIdAndDelete() {
        // Given
        User mockPlayer = User.builder().id("mock-player-user-id").displayName("player-displayname").email("player-email").build();
        GameRole mockPlayerGameRole = GameRole.builder()
                .id("mock-gamerole-id-2")
                .role(RoleType.PLAYER)
                .userId(mockPlayer.getId())
                .gameId(mockGame1.getId()).build();
        mockGame1.getPlayers().add(mockPlayer);
        mockGame1.getGameRoles().add(mockPlayerGameRole);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        doNothing().when(userService).removeGameroleFromUser(anyString(), anyString());
        doNothing().when(gameRoleService).delete(any(GameRole.class));
        doNothing().when(gameRepository).delete(any(Game.class));

        // When
        Game returnedGame = gameService.findAndDeleteById(mockGame1.getId());

        // Then
        assert returnedGame != null;
    }

    // ---------------------------------------------- MC stuff -------------------------------------------- //

    @Test
    void shouldFinishPreGame() {
        // Given
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));

        // When
        Game returnedGame = gameService.finishPreGame(mockGame1.getId());

        // Then
        assert returnedGame != null;
        assertTrue(returnedGame.getHasFinishedPreGame());
        assertTrue(returnedGame.getShowFirstSession());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldCloseFirstSession() {
        // Given
        mockGame1.setShowFirstSession(true);
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));

        // When
        Game returnedGame = gameService.closeFirstSession(mockGame1.getId());

        // Then
        assert returnedGame != null;
        assertFalse(returnedGame.getShowFirstSession());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    // ---------------------------------------------- Move categories -------------------------------------------- //

    @Test
    void shouldPerformPrintMove_withMove() {
        // Given
        when(moveService.findById(anyString())).thenReturn(sucker);
        when(characterService.findById(anyString())).thenReturn(mockCharacter);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.performPrintMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                sucker.getId(),
                false);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertTrue(returnedGameMessage.getContent().contains(sucker.getDescription()));
        verify(moveService, times(1)).findById(anyString());
        verify(characterService, times(1)).findById(anyString());
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldPerformPrintMove_withCharacterMove() {
        // Given
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(combatDriver);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        when(characterService.findById(anyString())).thenReturn(mockCharacter);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.performPrintMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                mockCharacterMove.getId(),
                false);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertTrue(returnedGameMessage.getContent().contains(mockCharacterMove.getDescription()));
        verify(characterService, times(1)).findById(anyString());
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldPerformPrintMoveWithGangMove() {
        // Given

        when(moveService.findById(anyString())).thenReturn(sucker);
        when(characterService.findById(anyString())).thenReturn(mockCharacter);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.performPrintMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                sucker.getId(),
                true);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertTrue(returnedGameMessage.getContent().contains(sucker.getDescription()));
        assertTrue(returnedGameMessage.getContent().contains("Gangs inflict and suffer harm as established"));
        verify(moveService, times(1)).findById(anyString());
        verify(characterService, times(1)).findById(anyString());
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldPerformBarterMove() {
        // Given
        mockCharacter.setBarter(3);
        int mockBarterSpent = 1;
        setUpMockServicesWithByMoveId(lifestyleAndGigs);


        // When
        Game returnedGame = gameService.performBarterMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                lifestyleAndGigs.getId(), mockBarterSpent);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());
        assertEquals(mockBarterSpent, returnedGameMessage.getBarterSpent());
        assertEquals(3 - mockBarterSpent, returnedGameMessage.getCurrentBarter());
        assertEquals(3 - mockBarterSpent, savedCharacter.getBarter());
        verify(moveService, times(1)).findById(anyString());
        verifyMockServices();
    }

    @Test
    void shouldPerformStockMove() {
        // Given
        AngelKit mockAngelKit = AngelKit.builder()
                .id(new ObjectId().toString())
                .stock(6)
                .build();
        PlaybookUniques mockPlaybookUnique = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .angelKit(mockAngelKit)
                .build();
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        int mockStockSpent = 2;
        setUpMockServices();
        when(moveService.findByName(anyString())).thenReturn(reviveSomeone);


        // When
        Game returnedGame = gameService.performStockMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                reviveSomeone.getName(), mockStockSpent);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());
        assertEquals(mockStockSpent, returnedGameMessage.getStockSpent());
//        assertEquals(6 - mockStockSpent, returnedGameMessage.getCurrentStock()); // Failing, but working properly
        assertEquals(6 - mockStockSpent, savedCharacter.getPlaybookUniques().getAngelKit().getStock());
        verify(moveService, times(1)).findByName(anyString());
        verifyMockServices();
    }

    // ---------------------------------------------- Roll move categories -------------------------------------------- //

    @Test
    void shouldPerformStatRollMoveWithMove() {
        // Given
        setUpMockServicesWithByMoveId(goAggro);

        // When
        Game returnedGame = gameService.performStatRollMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                goAggro.getId(),
                false);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertTrue(returnedGameMessage.getRoll1() > 0 && returnedGameMessage.getRoll1() < 7);
        assertTrue(returnedGameMessage.getRoll2() > 0 && returnedGameMessage.getRoll2() < 7);
        assertTrue(returnedGameMessage.getRollResult() > 2 && returnedGameMessage.getRoll2() < 13);
        assertEquals(goAggro.getMoveAction().getStatToRollWith(), returnedGameMessage.getModifierStatName());
        verify(moveService, times(1)).findById(anyString());
        verifyMockServices();
    }

    @Test
    void shouldPerformStatRollMoveWithCharacterMove() {
        // Given
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(dangerousAndSexy);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        setUpMockServices();

        // When
        Game returnedGame = gameService.performStatRollMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                mockCharacterMove.getId(),
                false);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertTrue(returnedGameMessage.getContent().contains(mockCharacterMove.getDescription()));
        verifyMockServices();
    }

    @Test
    void shouldPerformStatRollMoveWithGangMove() {
        // Given
        setUpMockServicesWithByMoveId(goAggro);

        // When
        Game returnedGame = gameService.performStatRollMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                goAggro.getId(),
                true);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertTrue(returnedGameMessage.getContent().contains(goAggro.getDescription()));
        assertTrue(returnedGameMessage.getContent().contains("Gangs inflict and suffer harm as established"));
        verify(moveService, times(1)).findById(anyString());
        verifyMockServices();
    }

    @Test
    void shouldPerformStatRollMoveWithPlusOneForward() {
        // Given
        mockCharacter.setHasPlusOneForward(true);
        setUpMockServicesWithByMoveId(goAggro);

        // When
        Game returnedGame = gameService.performStatRollMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                goAggro.getId(),
                false);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());
        assertTrue(returnedGameMessage.isUsedPlusOneForward());
        assertFalse(savedCharacter.getHasPlusOneForward());
        verifyMockServices();
    }

    @Test
    void shouldPerformSpeedRollMove() {
        // Given
        int mockModifier = 2;

        setUpMockServicesWithByMoveId(outdistanceAnotherVehicleMove);

        // When
        Game returnedGame = gameService.performSpeedRollMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                outdistanceAnotherVehicleMove.getId(),
                mockModifier);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertEquals(mockModifier, returnedGameMessage.getAdditionalModifierValue());
        assertEquals("REL. SPEED", returnedGameMessage.getAdditionalModifierName());
        verify(moveService, times(1)).findById(anyString());
        verifyMockServices();
    }

    // ---------------------------------------------- Specific moves -------------------------------------------- //

    @Test
    void shouldPerformWealthMove() {
        // Given
        int mockSurplus = 2;
        Holding mockHolding = Holding.builder()
                .id(new ObjectId().toString())
                .surplus(mockSurplus)
                .barter(0)
                .build();
        PlaybookUniques mockPlaybookUnique = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .holding(mockHolding)
                .build();
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(wealth);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        setUpMockServices();

        // When
        Game returnedGame = gameService.performWealthMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId());

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());
        assertTrue(returnedGameMessage.getContent().contains(wealth.getDescription()));
        if (returnedGameMessage.getRollResult() > 6) {
            assertEquals(mockSurplus, savedCharacter.getPlaybookUniques().getHolding().getBarter());
        } else {
            assertEquals(0, savedCharacter.getPlaybookUniques().getHolding().getBarter());
        }
        verifyMockServices();
    }

    @Test
    void shouldPerformFortunesMove() {
        // Given
        int mockSurplus = 2;
        Followers mockFollowers = Followers.builder()
                .id(new ObjectId().toString())
                .surplusBarter(mockSurplus)
                .barter(0)
                .build();
        PlaybookUniques mockPlaybookUnique = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .followers(mockFollowers)
                .build();
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(fortunes);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        setUpMockServices();

        // When
        Game returnedGame = gameService.performFortunesMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId());

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());

        assertTrue(returnedGameMessage.getContent().contains(fortunes.getDescription()));
        if (returnedGameMessage.getRollResult() > 6) {
            assertEquals(mockSurplus, savedCharacter.getPlaybookUniques().getFollowers().getBarter());
        } else {
            assertEquals(0, savedCharacter.getPlaybookUniques().getFollowers().getBarter());
        }
        verifyMockServices();
    }

    @Test
    void shouldPerformHelpOrInterfereMove() {
        // Given
        String mockCharacter2Id = "mock-character-2-id";
        int mockHxValue = 2;
        HxStat mockHxStat = HxStat.builder()
                .id(new ObjectId().toString())
                .characterId(mockCharacter2Id)
                .characterName("mock-character-2-name")
                .hxValue(mockHxValue)
                .build();
        mockCharacter.setHxBlock(List.of(mockHxStat));
        setUpMockServicesWithByMoveId(helpOrInterfere);

        // When
        Game returnedGame = gameService.performHelpOrInterfereMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                helpOrInterfere.getId(),
                mockCharacter2Id);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertEquals(mockHxValue, returnedGameMessage.getRollModifier());
        assertEquals(StatType.HX, returnedGameMessage.getModifierStatName());
        assertTrue(returnedGameMessage.getContent().contains(helpOrInterfere.getDescription()));
        verify(moveService, times(1)).findById(anyString());
        verifyMockServices();
    }

    @Test
    void shouldPerformMakeWantKnownMove() {
        // Given
        int mockBarterSpent = 1;
        mockCharacter.setBarter(3);
        when(characterService.findById(anyString())).thenReturn(mockCharacter);
        when(moveService.findById(anyString())).thenReturn(makeWantKnown);
        when(characterService.save(any(Character.class))).thenReturn(mockCharacter);
        when(gameRoleService.findById(anyString())).thenReturn(mockGameRole);
        when(gameRoleService.save(any(GameRole.class))).thenReturn(mockGameRole);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.performMakeWantKnownMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                makeWantKnown.getId(),
                mockBarterSpent);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter = returnedGame.getGameRoles().stream()
                .filter(gameRole -> gameRole.getId().equals(mockGameRole.getId())).findFirst().orElseThrow()
                .getCharacters().stream().filter(character -> character.getId().equals(mockCharacter.getId())).findFirst().orElseThrow();
        assertEquals(mockBarterSpent, returnedGameMessage.getBarterSpent());
        assertEquals(3 - mockBarterSpent, savedCharacter.getBarter());
        assertTrue(returnedGameMessage.getContent().contains(makeWantKnown.getDescription()));
        verify(characterService, times(1)).findById(anyString());
        verify(moveService, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleService, times(1)).findById(anyString());
        verify(gameRoleService, times(1)).save(any(GameRole.class));
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldPerformSufferHarmMove() {
        // Given
        int mockHarmSuffered = 2;
        setUpMockServicesWithByMoveId(sufferHarm);

        // When
        Game returnedGame = gameService.performSufferHarmMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                sufferHarm.getId(),
                mockHarmSuffered);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());
        assertEquals(mockHarmSuffered, returnedGameMessage.getHarmSuffered());
        assertEquals(mockHarmSuffered, savedCharacter.getHarm().getValue());
        assertTrue(returnedGameMessage.getContent().contains(sufferHarm.getDescription()));
        verify(moveService, times(1)).findById(anyString());
        verifyMockServices();
    }

    @Test
    void performSufferVHarmMove() {
        // Given
        int mockVHarmSuffered = 2;
        mockGameRole.getCharacters().add(mockCharacter);
        when(moveService.findByName(anyString())).thenReturn(sufferVHarm);
        when(gameRoleService.findById(anyString())).thenReturn(mockGameRole);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.performSufferVHarmMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                mockVHarmSuffered);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertEquals(mockVHarmSuffered, returnedGameMessage.getHarmSuffered());
        assertTrue(returnedGameMessage.getContent().contains(sufferVHarm.getDescription()));
        verify(moveService, times(1)).findByName(anyString());
        verify(gameRoleService, times(1)).findById(anyString());
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldPerformInflictHarmMove() {
        // Given
        int mockHarmInflicted = 1;
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRoleService.findById(mockGameRole2.getId())).thenReturn(mockGameRole2);
        when(characterService.findById(mockCharacter.getId())).thenReturn(mockCharacter);
        when(moveService.findByName(anyString())).thenReturn(inflictHarmMove);
        when(characterService.save(mockCharacter2)).thenReturn(mockCharacter2);
        when(gameRoleService.save(mockGameRole2)).thenReturn(mockGameRole2);
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.performInflictHarmMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockGameRole2.getId(),
                mockCharacter.getId(),
                mockCharacter2.getId(),
                mockHarmInflicted);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter = getSavedCharacter(returnedGame, mockGameRole2.getId(), mockCharacter2.getId());
        assertNotNull(returnedGameMessage);
        assertEquals(mockHarmInflicted, savedCharacter.getHarm().getValue());
        assertEquals(1 + mockHarmInflicted, savedCharacter.getHxBlock().stream()
                .filter(hxStat -> hxStat.getCharacterId().equals(mockCharacter.getId())).findFirst().orElseThrow().getHxValue());
        verify(gameRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).findById(anyString());
        verify(moveService, times(1)).findByName(anyString());
        verify(gameRoleService, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleService, times(1)).save(any(GameRole.class));
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldPerformHealHarmMove() {
        // Given
        int mockHarmHealed = 1;

        mockHarm.setValue(3);
        mockCharacter2.setHarm(mockHarm);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServicesWithOtherCharacter();
        when(moveService.findByName(anyString())).thenReturn(healPcHarm);

        // When
        Game returnedGame = gameService.performHealHarmMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockGameRole2.getId(),
                mockCharacter.getId(),
                mockCharacter2.getId(),
                mockHarmHealed);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter1 = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());
        Character savedCharacter2 = getSavedCharacter(returnedGame, mockGameRole2.getId(), mockCharacter2.getId());
        assertNotNull(returnedGameMessage);
        assertEquals(3 - mockHarmHealed, savedCharacter2.getHarm().getValue());
        assertEquals(1 + mockHarmHealed, savedCharacter1.getHxBlock().stream()
                .filter(hxStat -> hxStat.getCharacterId().equals(mockCharacter2.getId())).findFirst().orElseThrow().getHxValue());
        verifyMockServicesWithOtherCharacter();
    }

    @Test
    void shouldPerformAngelSpecialMove() {
        // Given
        int mockInitialHxValue = 1;
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(angelSpecial);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServicesWithOtherCharacter();

        // When
        Game returnedGame = gameService.performAngelSpecialMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockGameRole2.getId(),
                mockCharacter.getId(),
                mockCharacter2.getId());

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter1 = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());
        Character savedCharacter2 = getSavedCharacter(returnedGame, mockGameRole2.getId(), mockCharacter2.getId());
        HxStat character1HxStat = getMatchingHxStat(savedCharacter1, savedCharacter2.getId());
        HxStat character2HxStat = getMatchingHxStat(savedCharacter2, savedCharacter1.getId());

        assertNotNull(returnedGameMessage);
        assertEquals(mockInitialHxValue + 1, character2HxStat.getHxValue());
        assertEquals(3, character1HxStat.getHxValue());
        verifyMockServicesWithOtherCharacter();

    }

    @Test
    void shouldPerformChopperSpecialMove() {
        // Given
        int mockHxChange = 1;
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(chopperSpecial);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServicesWithOtherCharacter();

        // When
        Game returnedGame = gameService.performChopperSpecialMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockGameRole2.getId(),
                mockCharacter.getId(),
                mockCharacter2.getId(),
                mockHxChange);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter1 = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());
        Character savedCharacter2 = getSavedCharacter(returnedGame, mockGameRole2.getId(), mockCharacter2.getId());
        HxStat character1HxStat = getMatchingHxStat(savedCharacter1, savedCharacter2.getId());
        HxStat character2HxStat = getMatchingHxStat(savedCharacter2, savedCharacter1.getId());

        assertNotNull(returnedGameMessage);
        assertEquals(1 + mockHxChange, character1HxStat.getHxValue());
        assertEquals(3, character2HxStat.getHxValue());
        verifyMockServicesWithOtherCharacter();
    }

    @Test
    void shouldPerformGunluggerSpecialMove() {
        // Given
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(gunluggerSpecial);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServicesWithOtherCharacter();

        // When
        Game returnedGame = gameService.performGunluggerSpecialMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockGameRole2.getId(),
                mockCharacter.getId(),
                mockCharacter2.getId(),
                true);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter1 = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());
        Character savedCharacter2 = getSavedCharacter(returnedGame, mockGameRole2.getId(), mockCharacter2.getId());

        assertNotNull(returnedGameMessage);
        assertTrue(savedCharacter1.getHasPlusOneForward());
        assertTrue(savedCharacter2.getHasPlusOneForward());
        verifyMockServicesWithOtherCharacter();
    }

    @Test
    void shouldPerformGunluggerSpecialMoveButNotAddPlus1Forward() {
        // Given
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(gunluggerSpecial);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServicesWithOtherCharacter();

        // When
        Game returnedGame = gameService.performGunluggerSpecialMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockGameRole2.getId(),
                mockCharacter.getId(),
                mockCharacter2.getId(),
                false);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter1 = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());
        Character savedCharacter2 = getSavedCharacter(returnedGame, mockGameRole2.getId(), mockCharacter2.getId());

        assertNotNull(returnedGameMessage);
        assertTrue(savedCharacter1.getHasPlusOneForward());
        assertNull(savedCharacter2.getHasPlusOneForward());
        verifyMockServicesWithOtherCharacter();
    }

    @Test
    void shouldPerformHocusSpecialMove() {
        // Unable to mock saveAll()
    }

    @Test
    void shouldPerformSkinnerSpecialMove() {
        // Unable to mock saveAll()
    }

    @Test
    void shouldPerformStabilizeAndHealMove() {
        // Given
        int mockStockSpent = 2;
        int startingStock = 6;
        AngelKit mockAngelKit = AngelKit.builder()
                .id(new ObjectId().toString())
                .stock(startingStock)
                .build();
        PlaybookUniques mockPlaybookUnique = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .angelKit(mockAngelKit)
                .build();
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        when(moveService.findByName(anyString())).thenReturn(stabilizeAndHeal);
        when(characterService.save(any(Character.class))).thenReturn(mockCharacter);
        when(gameRoleService.findById(anyString())).thenReturn(mockGameRole);
        when(gameRoleService.save(any(GameRole.class))).thenReturn(mockGameRole);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.performStabilizeAndHealMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                mockStockSpent);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());

        assertTrue(returnedGameMessage.getContent().contains(stabilizeAndHeal.getDescription()));
        assertEquals(startingStock - mockStockSpent, savedCharacter.getPlaybookUniques().getAngelKit().getStock());
        verify(moveService, times(1)).findByName(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleService, times(1)).findById(anyString());
        verify(gameRoleService, times(1)).save(any(GameRole.class));
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldPerformJustGiveMotivationMoveOnNPC() {
        // Given
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(justGiveMotive);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        setUpMockServices();

        // When
        Game returnedGame = gameService.performJustGiveMotivationMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(), null);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();

        assertTrue(returnedGameMessage.getContent().contains(justGiveMotive.getDescription()));
        assertEquals(HARD, returnedGameMessage.getModifierStatName());
        verifyMockServices();
    }

    @Test
    void shouldPerformJustGiveMotivationMoveOnPC() {
        // Given
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(justGiveMotive);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        setUpMockServices();

        // When
        Game returnedGame = gameService.performJustGiveMotivationMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(), mockCharacter2.getId());

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();

        assertTrue(returnedGameMessage.getContent().contains(justGiveMotive.getDescription()));
        assertEquals(HX, returnedGameMessage.getModifierStatName());
        verifyMockServices();
    }

    // ---------------------------------------------- Other -------------------------------------------- //

    @Test
    void shouldSpendHold() {
        // Given
        Hold mockHold = Hold.builder()
                .id("mock-hold-id")
                .moveName("MOCK MOVE")
                .moveDescription("Mock move description")
                .build();
        mockCharacter.getHolds().add(mockHold);
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(justGiveMotive);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        setUpMockServices();

        // When
        Game returnedGame = gameService.spendHold(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(), mockHold);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter = getSavedCharacter(returnedGame, mockGameRole.getId(), mockCharacter.getId());

        assertTrue(returnedGameMessage.getTitle().contains("SPENDS A HOLD"));
        assertEquals(0, savedCharacter.getHolds().size());
        verifyMockServices();
    }

    @Test
    void shouldRewindScript_withComment() {
        String mockComment = "I'd like to go back to that bit about...";
        GameMessage returnedGameMessage = checkChangeScript(
                REWIND,
                mockComment,
                SCRIPT_CHANGE_REWIND_TITLE,
                SCRIPT_CHANGE_REWIND_CONTENT);
        assertTrue(returnedGameMessage.getContent().contains(mockComment));
    }

    @Test
    void shouldFastForwardScript() {
        checkChangeScript(FAST_FORWARD, null, SCRIPT_CHANGE_FAST_FORWARD_TITLE, SCRIPT_CHANGE_FAST_FORWARD_CONTENT);
    }

    @Test
    void shouldPauseScript() {
        checkChangeScript(PAUSE, null, SCRIPT_CHANGE_PAUSE_TITLE, SCRIPT_CHANGE_PAUSE_CONTENT);
    }

    @Test
    void shouldChangeScript_frameByFrame() {
        checkChangeScript(FRAME_BY_FRAME, null, SCRIPT_CHANGE_FRAME_TITLE, SCRIPT_CHANGE_FRAME_CONTENT);
    }

    @Test
    void shouldResumeScript() {
        checkChangeScript(RESUME, null, SCRIPT_CHANGE_RESUME_TITLE, SCRIPT_CHANGE_RESUME_CONTENT);
    }

    @Test
    void shouldReplayScript() {
        checkChangeScript(INSTANT_REPLAY, null, SCRIPT_CHANGE_REPLAY_TITLE, SCRIPT_CHANGE_REPLAY_CONTENT);
    }

    private Character getSavedCharacter(Game game, String gameRoleId, String characterId) {
        return game.getGameRoles().stream()
                .filter(gameRole -> gameRole.getId().equals(gameRoleId)).findFirst().orElseThrow()
                .getCharacters().stream()
                .filter(character -> character.getId().equals(characterId)).findFirst().orElseThrow();
    }

    private GameMessage checkChangeScript(
            ScriptChangeType scriptChangeType,
            String comment,
            String expectedTitle,
            String expectedContent) {
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);

        // When
        Game returnedGame = gameService.changeScript(mockGame1.getId(), scriptChangeType, comment);

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();

        assertTrue(returnedGameMessage.getTitle().contains(expectedTitle));
        assertTrue(returnedGameMessage.getContent().contains(expectedContent));
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));

        return returnedGameMessage;
    }

    private void setUpMockServicesWithByMoveId(Move move) {
        when(characterService.findById(anyString())).thenReturn(mockCharacter);
        when(characterService.save(any(Character.class))).thenReturn(mockCharacter);
        when(gameRoleService.findById(anyString())).thenReturn(mockGameRole);
        when(gameRoleService.save(any(GameRole.class))).thenReturn(mockGameRole);
        when(moveService.findById(anyString())).thenReturn(move);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);
    }

    private void setupMockServicesWithOtherCharacter() {
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRoleService.findById(mockGameRole.getId())).thenReturn(mockGameRole);
        when(characterService.saveAll(List.of(mockCharacter, mockCharacter2))).thenReturn(List.of(mockCharacter, mockCharacter2));
        when(gameRoleService.saveAll(List.of(mockGameRole, mockGameRole2))).thenReturn(List.of(mockGameRole, mockGameRole2));
        when(gameRoleService.findById(mockGameRole2.getId())).thenReturn(mockGameRole2);
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);
    }

    private void setUpMockServices() {
        when(characterService.findById(anyString())).thenReturn(mockCharacter);
        when(characterService.save(any(Character.class))).thenReturn(mockCharacter);
        when(gameRoleService.findById(anyString())).thenReturn(mockGameRole);
        when(gameRoleService.save(any(GameRole.class))).thenReturn(mockGameRole);
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame1);
    }

    private void verifyMockServices() {
        verify(characterService, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleService, times(1)).findById(anyString());
        verify(gameRoleService, times(1)).save(any(GameRole.class));
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    private void verifyMockServicesWithOtherCharacter() {
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRoleService, times(2)).findById(anyString());
        verify(characterService, times(1)).saveAll(List.of(mockCharacter, mockCharacter2));
        verify(gameRoleService, times(1)).saveAll(List.of(mockGameRole, mockGameRole2));
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    private HxStat getMatchingHxStat(Character character, String otherCharacterId) {
        return character.getHxBlock().stream()
                .filter(hxStat -> hxStat.getCharacterId().equals(otherCharacterId)).findFirst().orElseThrow();
    }
}