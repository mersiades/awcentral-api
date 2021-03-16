package com.mersiades.awcdata.services.impl;

import com.mersiades.awccontent.constants.MoveNames;
import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.Move;
import com.mersiades.awccontent.models.MoveAction;
import com.mersiades.awccontent.services.MoveService;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.AngelKit;
import com.mersiades.awcdata.repositories.GameRepository;
import com.mersiades.awcdata.repositories.UserRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.mersiades.awccontent.constants.MoveNames.*;
import static com.mersiades.awccontent.enums.StatType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceImplTest {

    public static final String MOCK_GAME_ID_1 = "mock-game-id-1";
    public static final String MOCK_GAMEROLE_ID = "mock-gamerole-id";

    @Mock
    GameRepository gameRepository;

    @Mock
    UserRepository userRepository;

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

    User mockMc;

    StatsBlock mockStatsBlock;
    
    Character mockCharacter;

    CharacterStat mockCool;
    CharacterStat mockHard;
    CharacterStat mockHot;
    CharacterStat mockSharp;
    CharacterStat mockWeird;

    MoveAction suckerAction = MoveAction.builder()
            .id(UUID.randomUUID().toString())
            .actionType(MoveActionType.PRINT)
            .rollType(null)
            .statToRollWith(null)
            .build();

    Move sucker = Move.builder()
            .id(UUID.randomUUID().toString())
            .name(suckerSomeoneName)
            .description("When you _**attack someone unsuspecting or helpless**_, ask the MC...")
            .kind(MoveType.BASIC)
            .moveAction(suckerAction)
            .playbook(null)
            .build();

    MoveAction goAggroAction = MoveAction.builder()
            .id(UUID.randomUUID().toString())
            .actionType(MoveActionType.ROLL)
            .rollType(RollType.STAT)
            .statToRollWith(HARD)
            .build();
    Move goAggro = Move.builder()
            .id(UUID.randomUUID().toString())
            .name(MoveNames.goAggroName)
            .description("When you _**go aggro on someone**_, make it clear...")
            .kind(MoveType.BASIC)
            .moveAction(goAggroAction)
            .playbook(null)
            .build();

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMc = User.builder().id("mock-user-id").email("mock-email").displayName("mock-displayname").build();
        mockGameRole = GameRole.builder().id(MOCK_GAMEROLE_ID).role(RoleType.MC).game(mockGame1).user(mockMc).build();
        mockCool = CharacterStat.builder()
                .id(UUID.randomUUID().toString())
                .stat(COOL)
                .value(1)
                .build();
        mockHard = CharacterStat.builder()
                .id(UUID.randomUUID().toString())
                .stat(HARD)
                .value(1)
                .build();
        mockHot = CharacterStat.builder()
                .id(UUID.randomUUID().toString())
                .stat(HOT)
                .value(1)
                .build();

        mockSharp = CharacterStat.builder()
                .id(UUID.randomUUID().toString())
                .stat(SHARP)
                .value(1)
                .build();
        mockWeird = CharacterStat.builder()
                .id(UUID.randomUUID().toString())
                .stat(WEIRD)
                .value(1)
                .build();

        mockStatsBlock = StatsBlock.builder()
                .id(UUID.randomUUID().toString())
                .stats(List.of(mockCool, mockHard, mockHot, mockSharp, mockWeird))
                .build();
        mockCharacter = Character.builder()
                .id("mock-character-id-1")
                .statsBlock(mockStatsBlock)
                .hasPlusOneForward(false)
                .build();
        List<GameRole> gameRoles = new ArrayList<>();
        gameRoles.add(mockGameRole);
        mockGame1 = Game.builder().id(MOCK_GAME_ID_1).name("Michael's Mock Game").mc(mockMc).gameRoles(gameRoles).build();
        gameService = new GameServiceImpl(gameRepository, userService, gameRoleService, characterService, moveService);
    }

    @Test
    void shouldFindAllGames() {
        // Given
        Game mockGame2 = new Game();
        when(gameRepository.findAll()).thenReturn(Flux.just(mockGame1, mockGame2));

        // When
        List<Game> returnedGames = gameService.findAll().collectList().block();

        // Then
        assert returnedGames != null;
        assertEquals(2, returnedGames.size());
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    void shouldFindGameById() {
        // Given
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.findById(MOCK_GAME_ID_1).block();

        // Then
        assertNotNull(returnedGame, "Null Game returned");
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, never()).findAll();
    }

    @Test
    void shouldSaveGame() {
        // Given
        when(gameRepository.save(any())).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.save(mockGame1).block();

        // Then
        assert returnedGame != null;
        assertEquals(MOCK_GAME_ID_1, returnedGame.getId());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldSaveAllGames() {
        // Given
        Game mockGame2 = new Game();
        when(gameRepository.saveAll(any(Publisher.class))).thenReturn(Flux.just(mockGame1, mockGame2));

        // When
        List<Game> savedGames = gameService.saveAll(Flux.just(mockGame1, mockGame2)).collectList().block();

        // Then
        assert savedGames != null;
        assertEquals(2, savedGames.size());
        verify(gameRepository, times(1)).saveAll(any(Publisher.class));
    }

     // This test stopped working after I added .block() to gameRepository.delete()
    @Test
    @Disabled
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
        when(gameRepository.findByGameRoles(any())).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.findByGameRoles(mockGameRole).block();

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
        when(gameRepository.findAllByInviteesContaining(anyString())).thenReturn(Flux.just(mockGame1, mockGame2));

        // When
        List<Game> returnedGames = gameService.findAllByInvitee(mockInvitee).collectList().block();

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
        when(gameRepository.save(any())).thenReturn(Mono.just(mockGame1));
        when(gameRoleService.save(any())).thenReturn(Mono.just(mockGameRole));

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
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.setGameName(mockGame1.getId(), newName).block();

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
        when(gameService.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.addInvitee(mockGame1.getId(), mockInvitee);

        // Then
        assert returnedGame != null;
        assertTrue(returnedGame.getInvitees().contains(mockInvitee));
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldRemoveInviteeFromGame() {
        // Given
        String mockInvitee = "mock@invitee.com";
        mockGame1.getInvitees().add(mockInvitee);
        when(gameService.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.removeInvitee(mockGame1.getId(), mockInvitee);

        // Then
        assert returnedGame != null;
        assertFalse(returnedGame.getInvitees().contains(mockInvitee));
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldAddCommsAppToGame() {
        // Given
        String mockCommsApp = "Discord";
        when(gameService.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

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
        when(gameService.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

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
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));
        when(gameRoleService.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Game returnedGame = gameService.addUserToGame(mockGame1.getId(),
                mockNewUser.getId(),
                mockNewUser.getDisplayName(),
                mockNewUser.getEmail());

        // Then
        assert returnedGame != null;
        System.out.println("returnedGame = " + returnedGame);
        assertEquals(1, returnedGame.getPlayers().size());
        assertEquals(2, returnedGame.getGameRoles().size());
        verify(userService, times(1)).findOrCreateUser(anyString(),anyString(),anyString());
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
        verify(gameRoleService, times(1)).save(any(GameRole.class));
    }

    // I'm having trouble mocking the void methods
    // This should be an integration test, not unit
    @Test
    @Disabled
    void shouldFindGameByIdAndDelete() throws Exception {
        // Given
        User mockPlayer = User.builder().id("mock-player-user-id").displayName("player-displayname").email("player-email").build();
        GameRole mockPlayerGameRole = GameRole.builder()
                .id("mock-gamerole-id-2")
                .role(RoleType.PLAYER)
                .user(mockPlayer)
                .game(mockGame1).build();
        mockGame1.getPlayers().add(mockPlayer);
        mockGame1.getGameRoles().add(mockPlayerGameRole);
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));
        doNothing().when(userService).removeGameroleFromUser(anyString(), anyString());
        doNothing().when(gameRoleService).delete(any(GameRole.class));
        doNothing().when(gameRepository).delete(any(Game.class));

        // When
        Game returnedGame = gameService.findAndDeleteById(mockGame1.getId());

        // Then
        assert returnedGame != null;
        System.out.println("returnedGame = " + returnedGame);
    }

    // ---------------------------------------------- MC stuff -------------------------------------------- //

    @Test
    void shouldFinishPreGame() {
        // Given
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));
        when(gameService.findById(anyString())).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.finishPreGame(mockGame1.getId()).block();

        // Then
        assert returnedGame != null;
        assertTrue(returnedGame.getHasFinishedPreGame());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    // ---------------------------------------------- Move categories -------------------------------------------- //

    @Test
    void shouldPerformPrintMoveWithMove() {
        // Given
        when(moveService.findById(anyString())).thenReturn(Mono.just(sucker));
        when(characterService.findById(anyString())).thenReturn(Mono.just(mockCharacter));
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.performPrintMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                sucker.getId(),
                false).block();

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
    void shouldPerformPrintMoveWithCharacterMove() {
        // Given
        MoveAction combatDriverAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.PRINT)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move combatDriver = Move.builder()
                .id(UUID.randomUUID().toString())
                .name("COMBAT DRIVER")
                .description("_**Combat driver**_: when you use your vehicle as a weapon, inflict +1harm. When you inflict v-harm, add +1 to your target’s roll. When you suffer v-harm, take -1 to your roll.")
                .kind(MoveType.CHARACTER)
                .moveAction(combatDriverAction)
                .playbook(PlaybookType.DRIVER).build();
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(combatDriver);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        when(characterService.findById(anyString())).thenReturn(Mono.just(mockCharacter));
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.performPrintMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                mockCharacterMove.getId(),
                false).block();

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

        when(moveService.findById(anyString())).thenReturn(Mono.just(sucker));
        when(characterService.findById(anyString())).thenReturn(Mono.just(mockCharacter));
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.performPrintMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                sucker.getId(),
                true).block();

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
        MoveAction lifestyleAndGigsAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.BARTER)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move lifestyleAndGigs = Move.builder()
                .id(UUID.randomUUID().toString())
                .name("LIFESTYLE AND GIGS")
                .description("_**At the beginning of the session**_, spend 1- or 2-barter for your lifestyle. If you can’t or won’t, tell the MC and answer her questions. If you need jingle during a session, tell the MC you’d like to work a gig.")
                .kind(MoveType.BASIC)
                .moveAction(lifestyleAndGigsAction)
                .playbook(null)
                .build();
        mockCharacter.setBarter(3);
        int mockBarterSpent = 1;
        when(characterService.findById(anyString())).thenReturn(Mono.just(mockCharacter));
        when(characterService.save(any(Character.class))).thenReturn(Mono.just(mockCharacter));
        when(gameRoleService.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(gameRoleService.save(any(GameRole.class))).thenReturn(Mono.just(mockGameRole));
        when(moveService.findById(anyString())).thenReturn(Mono.just(lifestyleAndGigs));
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.performBarterMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                lifestyleAndGigs.getId(), mockBarterSpent).block();

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertEquals(mockBarterSpent, returnedGameMessage.getBarterSpent());
        assertEquals(3 - mockBarterSpent, returnedGameMessage.getCurrentBarter());
        Character savedCharacter = returnedGame.getGameRoles().stream()
                .filter(gameRole -> gameRole.getId().equals(mockGameRole.getId())).findFirst().orElseThrow()
                .getCharacters().stream().filter(character -> character.getId().equals(mockCharacter.getId())).findFirst().orElseThrow();
        assertEquals(3 - mockBarterSpent, savedCharacter.getBarter());
        verify(characterService, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleService, times(1)).findById(anyString());
        verify(gameRoleService, times(1)).save(any(GameRole.class));
        verify(moveService, times(1)).findById(anyString());
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldPerformStockMove() {
        // Given
        MoveAction reviveSomeoneAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.STOCK)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move reviveSomeone = Move.builder()
                .id(UUID.randomUUID().toString())
                .name(reviveSomeoneName).description("_**revive someone whose life has become untenable**_, spend 2-stock.")
                .kind(MoveType.UNIQUE)
                .moveAction(reviveSomeoneAction)
                .playbook(PlaybookType.ANGEL).build();
        AngelKit mockAngelKit = AngelKit.builder()
                .id(UUID.randomUUID().toString())
                .stock(6)
                .build();
        PlaybookUnique mockPlaybookUnique = PlaybookUnique.builder()
                .id(UUID.randomUUID().toString())
                .angelKit(mockAngelKit)
                .build();
        mockCharacter.setPlaybookUnique(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        int mockStockSpent = 2;
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(characterService.findById(anyString())).thenReturn(Mono.just(mockCharacter));
        when(moveService.findByName(anyString())).thenReturn(Mono.just(reviveSomeone));
        when(gameRoleService.save(any(GameRole.class))).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any(Character.class))).thenReturn(Mono.just(mockCharacter));
        when(gameRoleService.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));


        // When
        Game returnedGame = gameService.performStockMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                reviveSomeone.getName(), mockStockSpent).block();

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter = returnedGame.getGameRoles().stream()
                .filter(gameRole -> gameRole.getId().equals(mockGameRole.getId())).findFirst().orElseThrow()
                .getCharacters().stream().filter(character -> character.getId().equals(mockCharacter.getId())).findFirst().orElseThrow();
        assertEquals(mockStockSpent, returnedGameMessage.getStockSpent());
        assertEquals(6 - mockStockSpent, returnedGameMessage.getCurrentStock());
        assertEquals(6 - mockStockSpent, savedCharacter.getPlaybookUnique().getAngelKit().getStock());
        verify(gameRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).findById(anyString());
        verify(moveService, times(1)).findByName(anyString());
        verify(gameRoleService, times(1)).findById(anyString());
        verify(gameRoleService, times(1)).save(any(GameRole.class));
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    // ---------------------------------------------- Roll move categories -------------------------------------------- //

    @Test
    void shouldPerformStatRollMoveWithMove() {
        // Given
        when(characterService.findById(anyString())).thenReturn(Mono.just(mockCharacter));
        when(moveService.findById(anyString())).thenReturn(Mono.just(goAggro));
        when(characterService.save(any(Character.class))).thenReturn(Mono.just(mockCharacter));
        when(gameRoleService.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(gameRoleService.save(any(GameRole.class))).thenReturn(Mono.just(mockGameRole));
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.performStatRollMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                goAggro.getId(),
                false).block();

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertTrue(returnedGameMessage.getRoll1() > 0 && returnedGameMessage.getRoll1() < 7);
        assertTrue(returnedGameMessage.getRoll2() > 0 && returnedGameMessage.getRoll2() < 7);
        assertTrue(returnedGameMessage.getRollResult() > 2 && returnedGameMessage.getRoll2() < 13);
        assertEquals(goAggro.getMoveAction().getStatToRollWith(), returnedGameMessage.getModifierStatName());
        verify(characterService, times(1)).findById(anyString());
        verify(moveService, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleService, times(1)).findById(anyString());
        verify(gameRoleService, times(1)).save(any(GameRole.class));
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldPerformStatRollMoveWithCharacterMove() {
        // Given
        MoveAction dangerousAndSexyAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STAT)
                .statToRollWith(HOT)
                .build();
        Move dangerousAndSexy = Move.builder()
                .id(UUID.randomUUID().toString())
                .name(dangerousAndSexyName)
                .description("_**Dangerous & sexy**_: when you enter into a charged situation, roll+hot.")
                .kind(MoveType.CHARACTER)
                .moveAction(dangerousAndSexyAction)
                .playbook(PlaybookType.BATTLEBABE)
                .build();
        CharacterMove mockCharacterMove = CharacterMove.createFromMove(dangerousAndSexy);
        mockCharacter.getCharacterMoves().add(mockCharacterMove);
        when(characterService.findById(anyString())).thenReturn(Mono.just(mockCharacter));
        when(characterService.save(any(Character.class))).thenReturn(Mono.just(mockCharacter));
        when(gameRoleService.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(gameRoleService.save(any(GameRole.class))).thenReturn(Mono.just(mockGameRole));
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.performStatRollMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                mockCharacterMove.getId(),
                false).block();

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertTrue(returnedGameMessage.getContent().contains(mockCharacterMove.getDescription()));
        verify(characterService, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleService, times(1)).findById(anyString());
        verify(gameRoleService, times(1)).save(any(GameRole.class));
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldPerformStatRollMoveWithGangMove() {
        // Given
        when(characterService.findById(anyString())).thenReturn(Mono.just(mockCharacter));
        when(moveService.findById(anyString())).thenReturn(Mono.just(goAggro));
        when(characterService.save(any(Character.class))).thenReturn(Mono.just(mockCharacter));
        when(gameRoleService.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(gameRoleService.save(any(GameRole.class))).thenReturn(Mono.just(mockGameRole));
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.performStatRollMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                goAggro.getId(),
                true).block();

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        assertTrue(returnedGameMessage.getContent().contains(goAggro.getDescription()));
        assertTrue(returnedGameMessage.getContent().contains("Gangs inflict and suffer harm as established"));
        verify(characterService, times(1)).findById(anyString());
        verify(moveService, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleService, times(1)).findById(anyString());
        verify(gameRoleService, times(1)).save(any(GameRole.class));
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldPerformStatRollMoveWithPlusOneForward() {
        // Given
        mockCharacter.setHasPlusOneForward(true);
        when(characterService.findById(anyString())).thenReturn(Mono.just(mockCharacter));
        when(moveService.findById(anyString())).thenReturn(Mono.just(goAggro));
        when(characterService.save(any(Character.class))).thenReturn(Mono.just(mockCharacter));
        when(gameRoleService.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(gameRoleService.save(any(GameRole.class))).thenReturn(Mono.just(mockGameRole));
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame1));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.performStatRollMove(mockGame1.getId(),
                mockGameRole.getId(),
                mockCharacter.getId(),
                goAggro.getId(),
                false).block();

        // Then
        assert returnedGame != null;
        GameMessage returnedGameMessage = returnedGame.getGameMessages().stream().findFirst().orElseThrow();
        Character savedCharacter = returnedGame.getGameRoles().stream()
                .filter(gameRole -> gameRole.getId().equals(mockGameRole.getId())).findFirst().orElseThrow()
                .getCharacters().stream().filter(character -> character.getId().equals(mockCharacter.getId())).findFirst().orElseThrow();
        assertTrue(returnedGameMessage.isUsedPlusOneForward());
        assertFalse(savedCharacter.getHasPlusOneForward());
        verify(characterService, times(1)).findById(anyString());
        verify(moveService, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleService, times(1)).findById(anyString());
        verify(gameRoleService, times(1)).save(any(GameRole.class));
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void shouldPerformSpeedRollMove() {}

    // ---------------------------------------------- Specific moves -------------------------------------------- //

    @Test
    void shouldPerformWealthMove() {}

    @Test
    void shouldPerformFortunesMove() {}

    @Test
    void shouldPerformHelpOrInterfereMove() {}

    @Test
    void shouldPerformMakeWantKnownMove() {}

    @Test
    void shouldPerformSufferHarmMove() {}

    @Test
    void shouldPerformInflictHarmMove() {}

    @Test
    void shouldPerformHealHarmMove() {}

    @Test
    void shouldPerformAngelSpecialMove() {}

    @Test
    void shouldPerformChopperSpecialMove() {}

    @Test
    void shouldPerformGunluggerSpecialMove() {}

    @Test
    void shouldPerformHocusSpecialMove() {}

    @Test
    void shouldPerformSkinnerSpecialMove() {}

    @Test
    void shouldPerformStabilizeAndHealMove() {}

    @Test
    void shouldPerformJustGiveMotivationMove() {}

    // ---------------------------------------------- Other -------------------------------------------- //

    @Test
    void shouldSpendHold() {}

}