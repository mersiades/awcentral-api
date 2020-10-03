package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.GameRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameRoleJpaServiceTest {

    @Mock
    GameRoleRepository gameRoleRepository;

    @InjectMocks
    GameRoleJpaService service;

    GameRole mockGameRole;

    Game mockGame;

    User mockUser;

    @BeforeEach
    void setUp() {
        mockGame = new Game(2L, "823458920374529070", "123876129847590347", "Mock Game");
        mockUser = new User(3L);
        mockGameRole = new GameRole(1L, Roles.MC, mockGame, mockUser);
    }

    @Test
    void findAll() {
        Set<GameRole> returnGameRoleSet = new HashSet<>();
        returnGameRoleSet.add(new GameRole(4L, Roles.PLAYER, mockGame, mockUser));
        returnGameRoleSet.add(new GameRole(5L, Roles.MC, mockGame, mockUser));

        when(gameRoleRepository.findAll()).thenReturn(returnGameRoleSet);

        Set<GameRole> gameRoles = service.findAll();

        assertNotNull(gameRoles);
        assertEquals(2, gameRoles.size());
    }

    @Test
    void findById() {
        when(gameRoleRepository.findById(any())).thenReturn(Optional.of(mockGameRole));

        GameRole gameRole = service.findById(1L);

        assertNotNull(gameRole);
    }

    @Test
    void save() {
        when(gameRoleRepository.save(any())).thenReturn(mockGameRole);

        GameRole gameRole = service.save(mockGameRole);

        assertNotNull(gameRole);
        verify(gameRoleRepository).save(any());
    }

    @Test
    void delete() {
        gameRoleRepository.delete(mockGameRole);

        verify(gameRoleRepository).delete(any());
    }

    @Test
    void deleteById() {
        gameRoleRepository.deleteById(1L);

        verify(gameRoleRepository).deleteById(any());
    }
}