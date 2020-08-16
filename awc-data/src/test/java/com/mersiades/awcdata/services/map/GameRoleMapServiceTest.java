package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameRoleMapServiceTest {

    GameRoleMapService gameRoleMapService;

    final Long gameRoleId = 1L;

    Game game = new Game();

    User user = new User();

    @BeforeEach
    void setUp() {
        gameRoleMapService = new GameRoleMapService();
        gameRoleMapService.save(new GameRole(1L, Roles.MC, game, user));
    }

    @Test
    void findAll() {
        Set<GameRole> gameRoles = gameRoleMapService.findAll();

        assertEquals(1, gameRoles.size());
    }

    @Test
    void findById() {
        GameRole gameRole = gameRoleMapService.findById(gameRoleId);

        assertEquals(gameRoleId, gameRole.getId());
    }

    @Test
    void saveWithGivenId() {
        Long id = 2L;
        User user2 = new User();
        GameRole gameRole2 = new GameRole(id, Roles.PLAYER, game, user2);

        GameRole savedGameRole = gameRoleMapService.save(gameRole2);

        assertEquals(id, savedGameRole.getId());
    }

    @Test
    void saveWithNoIdGiven() {

        GameRole savedGameRole = gameRoleMapService.save(new GameRole());

        assertNotNull(savedGameRole);
        assertNotNull(savedGameRole.getId());
    }

    @Test
    void delete() {
        gameRoleMapService.delete(gameRoleMapService.findById(gameRoleId));

        assertEquals(0, gameRoleMapService.findAll().size());
    }

    @Test
    void deleteById() {
        gameRoleMapService.deleteById(gameRoleId);

        assertEquals(0, gameRoleMapService.findAll().size());
    }
}