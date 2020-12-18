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
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    GameService gameService;

    Game mockGame1;

    GameRole mockGameRole;

    User mockUser;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockUser = new User();
        mockGameRole = GameRole.builder().id(MOCK_GAMEROLE_ID).role(Roles.MC).game(mockGame1).user(mockUser).build();
        mockGame1 = Game.builder().id(MOCK_GAME_ID_1).name("Michael's Mock Game").gameRoles(List.of(mockGameRole)).build();
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


}