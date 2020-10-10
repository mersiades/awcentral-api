package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameMapServiceTest {

    GameMapService gameMapService;
    UserService userService;
    GameRoleService gameRoleService;

    final Long gameId = 1L;

    final String textChannelId = "741573502452105236";

    final String textChannelId2 = "741573503710527498";

    final String voiceChannelId = "823458920374529070";

    final String voiceChannelId2 = "123876129847590347";

    final String name = "Mock game";

    final String name2 = "Mock game 2";

    @BeforeEach
    void setUp() {
        gameMapService = new GameMapService(userService, gameRoleService);
        gameMapService.save(new Game(gameId, textChannelId, voiceChannelId, name));
    }

    @Test
    void findAll() {
        Set<Game> games = gameMapService.findAll();

        assertEquals(1, games.size());
    }

    @Test
    void findById() {
        Game game = gameMapService.findById(gameId);

        assertEquals(gameId, game.getId());
    }

    @Test
    void saveWithGivenId() {
        Long id = 2L;
        Game game2 = new Game(id, textChannelId2, voiceChannelId2, name2);

        Game savedGame = gameMapService.save(game2);

        assertEquals(id, savedGame.getId());
    }

    @Test
    void saveWithNoIdGiven() {

        Game savedGame = gameMapService.save(new Game());

        assertNotNull(savedGame);
        assertNotNull(savedGame.getId());
    }

    @Test
    void delete() {
        gameMapService.delete(gameMapService.findById(gameId));

        assertEquals(0, gameMapService.findAll().size());
    }

    @Test
    void deleteById() {
        gameMapService.deleteById(gameId);

        assertEquals(0, gameMapService.findAll().size());
    }
}