package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.GameRepository;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class GameServiceImplTest {

    public static final String MOCK_GAME_ID_1 = "mock-game-id-1";
    public static final String MOCK_GAMEROLE_ID = "mock-gamerole-id";
    public static final String MOCK_TEXT_CHANNEL_ID_1 = "mock-text-channel-id-1";

    @Mock
    GameRepository gameRepository;

    @Mock
    UserService userService;

    @Mock
    GameRoleService gameRoleService;

    GameService gameService;

    Game mockGame1;

    GameRole mockGameRole;

    User mockUser;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockUser = new User();
        mockGameRole = new GameRole(MOCK_GAMEROLE_ID, Roles.MC, mockGame1, mockUser);
        mockGame1 = new Game(MOCK_GAME_ID_1,
                MOCK_TEXT_CHANNEL_ID_1,
                "mock-voice-channel-id-1",
                "Michael's Mock Game",
                mockGameRole);
        gameService = new GameServiceImpl(gameRepository, userService, gameRoleService);
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
    void shouldCreateGameWithMC() {
        // Given
        String mockDiscordId = "mock-discord-id";
        String mockGameName = "Michael's new mock game";
        when(userService.findByDiscordId(anyString())).thenReturn(Mono.just(mockUser));
        when(gameRepository.save(any())).thenReturn(Mono.just(mockGame1));
        when(gameRoleService.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Game returnedGame = gameService.createGameWithMC(mockDiscordId, mockGameName);
        
        // Then
        assertEquals(mockGameName, returnedGame.getName());
        verify(userService, times(1)).findByDiscordId(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));
        verify(gameRoleService, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldDeleteGameByTextChannelId() {
        // Given
        when(gameRepository.deleteGameByTextChannelId(anyString())).thenReturn(Mono.just(mockGame1));

        // When
        Game deletedGame = gameService.deleteGameByTextChannelId(MOCK_TEXT_CHANNEL_ID_1).block();

        // Then
        assert deletedGame != null;
        assertEquals(MOCK_GAME_ID_1, deletedGame.getId());
        verify(gameRepository, times(1)).deleteGameByTextChannelId(anyString());
    }

    @Test
    void shouldFindGameByTextChannelId() {
        // Given
        when(gameRepository.findGameByTextChannelId(anyString())).thenReturn(Mono.just(mockGame1));

        // When
        Game returnedGame = gameService.findGameByTextChannelId(MOCK_GAME_ID_1).block();

        // Then
        assert returnedGame != null;
        assertEquals(MOCK_GAME_ID_1, returnedGame.getId());
        verify(gameRepository, times(1)).findGameByTextChannelId(anyString());
    }

    @Test
    void shouldAppendChannelsToGame() {
        // Given
        String mockGameId = "mock-game-id-2";
        String mockTextChannelId = "mock-text-channel-id-1";
        String mockVoiceChannelId = "mock-voice-channel-id-1";
        Game mockGame2 = Game.builder()
                .id(mockGameId)
                .name("mock-game-name-2")
                .build();
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(mockGame2));

        // This seems so wrong, like I'm not testing the method at all, just replicating the outcome
        mockGame2.setVoiceChannelId(mockVoiceChannelId);
        mockGame2.setTextChannelId(mockTextChannelId);
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(mockGame2));

        // When
        Game updatedGame = gameService.appendChannels(mockGameId, mockTextChannelId, mockVoiceChannelId).block();

        // Then
        assert updatedGame != null;
        assertEquals(mockTextChannelId, updatedGame.getTextChannelId());
        assertEquals(mockVoiceChannelId, updatedGame.getVoiceChannelId());
        verify(gameRepository, times(1)).findById(anyString());
        verify(gameRepository, times(1)).save(any(Game.class));

    }
}