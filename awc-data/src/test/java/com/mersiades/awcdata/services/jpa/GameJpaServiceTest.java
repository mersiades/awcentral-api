package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.repositories.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameJpaServiceTest {

    @Mock
    GameRepository gameRepository;

    Game returnedGame;

    @InjectMocks
    GameJpaService service;

    @BeforeEach
    void setUp() {
        returnedGame = new Game("gameId01", "823458920374529070", "123876129847590347", "Mock Game");
    }

    @Test
    void findAll() {
        Set<Game> returnedGames = new HashSet<>();
        returnedGames.add(new Game("gameId02", "741573502452105236", "741573503710527498", "Another Mock Game"));
        returnedGames.add(returnedGame);

        when(gameRepository.findAll()).thenReturn(returnedGames);

        Set<Game> games = service.findAll();

        assertNotNull(games);
        assertEquals(2, games.size());
    }

    @Test
    void findById() {
        when(gameRepository.findById(any())).thenReturn(Optional.of(returnedGame));

        Game game = service.findById("gameId01");

        assertEquals(game.getId(), returnedGame.getId());
    }

    @Test
    void findByIdNotFound() {
        when(gameRepository.findById(any())).thenReturn(Optional.empty());

        Game game = service.findById("gameId01");

        assertNull(game);
    }

    @Test
    void save() {
        Game gameToSave = new Game("gameId03", "741573502452105236", "741573503710527498", "Save Me");

        when(gameRepository.save(any())).thenReturn(gameToSave);

        Game savedGame = service.save(gameToSave);

        assertNotNull(savedGame);
        assertEquals(gameToSave.getId(), savedGame.getId());
        verify(gameRepository).save(any());
    }

    @Test
    void delete() {
        gameRepository.delete(returnedGame);

        verify(gameRepository).delete(any());
    }

    @Test
    void deleteById() {
        gameRepository.deleteById("gameId01");

        verify(gameRepository).deleteById(any());
    }
}