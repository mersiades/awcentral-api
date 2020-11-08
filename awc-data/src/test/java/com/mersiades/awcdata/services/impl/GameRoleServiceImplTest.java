package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.GameRoleReactiveRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameRoleServiceImplTest {

    public static final String MOCK_GAMEROLE_ID = "mock-gamerole-id";
    @Mock
    GameRoleReactiveRepository gameRoleReactiveRepository;

    @Mock
    CharacterService characterService;

    GameRoleService gameRoleService;

    GameRole mockGameRole;

    User mockUser;

    Character mockCharacter;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Game mockGame1 = new Game();
        mockUser = new User();
        mockCharacter = new Character();
        mockGameRole = new GameRole(MOCK_GAMEROLE_ID, Roles.MC, mockGame1, mockUser);
        gameRoleService = new GameRoleServiceImpl(gameRoleReactiveRepository, characterService);
    }


    @Test
    void shouldFindAllGameRoles() {
        // Given
        GameRole gameRole2 = new GameRole();
        when(gameRoleReactiveRepository.findAll()).thenReturn(Flux.just(mockGameRole, gameRole2));

        // When
        List<GameRole> gameRoles = gameRoleService.findAll().collectList().block();

        // Then
        assert gameRoles != null;
        assertEquals(2, gameRoles.size());
        verify(gameRoleReactiveRepository, times(1)).findAll();
    }

    @Test
    void shouldFindGameRoleById() {
        // Given
        when(gameRoleReactiveRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));

        // When
        GameRole gameRole = gameRoleService.findById(MOCK_GAMEROLE_ID).block();

        // Then
        assertNotNull(gameRole, "Null GameRole returned");
        verify(gameRoleReactiveRepository, times(1)).findById(anyString());
        verify(gameRoleReactiveRepository, never()).findAll();
    }

    @Test
    void shouldSaveGameRole() {
        // Given
        when(gameRoleReactiveRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        GameRole savedGameRole = gameRoleService.save(mockGameRole).block();

        // Then
        assert savedGameRole != null;
        assertEquals(MOCK_GAMEROLE_ID, savedGameRole.getId());
        verify(gameRoleReactiveRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldDeleteGameRole() {
        // When
        gameRoleService.delete(mockGameRole);

        // Then
        verify(gameRoleReactiveRepository, times(1)).delete(any(GameRole.class));
    }

    @Test
    void shouldDeleteGameRoleById() {
        // When
        gameRoleService.deleteById(MOCK_GAMEROLE_ID);

        // Then
        verify(gameRoleReactiveRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldFindAllGameRolesByUser() {
        // Given
        Game mockGame2 = new Game();
        GameRole mockGameRole2 = new GameRole("mock-gamerole-id2", Roles.MC, mockGame2, mockUser);
        when(gameRoleReactiveRepository.findAllByUser(any())).thenReturn(Flux.just(mockGameRole, mockGameRole2));

        // When
        List<GameRole> returnedGameRoles = gameRoleService.findAllByUser(mockUser).collectList().block();

        // Then
        assert returnedGameRoles != null;
        assertEquals(2, returnedGameRoles.size());
        verify(gameRoleReactiveRepository, times(1)).findAllByUser(any(User.class));
    }

    @Test
    void shouldAddNewCharacterToGameRole() {
        // Given
        when(gameRoleReactiveRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleReactiveRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService.addNewCharacter(MOCK_GAMEROLE_ID);

        // Then
        assertNotNull(returnedCharacter, "Null Character returned");
        verify(gameRoleReactiveRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleReactiveRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldSetCharacterPlaybook() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        when(gameRoleReactiveRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleReactiveRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService.setCharacterPlaybook(MOCK_GAMEROLE_ID, mockCharacter.getId(), Playbooks.BATTLEBABE);

        // Then
        assertEquals(Playbooks.BATTLEBABE, returnedCharacter.getPlaybook());
        verify(gameRoleReactiveRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleReactiveRepository, times(1)).save(any(GameRole.class));
    }
}