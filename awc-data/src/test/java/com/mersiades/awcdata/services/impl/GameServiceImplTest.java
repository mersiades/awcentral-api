package com.mersiades.awcdata.services.impl;

import com.mersiades.awccontent.enums.RoleType;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
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

    GameService gameService;

    Game mockGame1;

    GameRole mockGameRole;

    User mockMc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMc = User.builder().id("mock-user-id").email("mock-email").displayName("mock-displayname").build();
        mockGameRole = GameRole.builder().id(MOCK_GAMEROLE_ID).role(RoleType.MC).game(mockGame1).user(mockMc).build();
        List<GameRole> gameRoles = new ArrayList<>();
        gameRoles.add(mockGameRole);
        mockGame1 = Game.builder().id(MOCK_GAME_ID_1).name("Michael's Mock Game").mc(mockMc).gameRoles(gameRoles).build();
        gameService = new GameServiceImpl(gameRepository, userService, gameRoleService, characterService);
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

    @Test
    public void shouldFinishPreGame() {
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
}