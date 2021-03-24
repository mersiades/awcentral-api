package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.UserRepository;
import com.mersiades.awcdata.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    public static final String MOCK_USER_ID_1 = "mock-user-id-1";
    public static final String DISCORD_USER_ID_1 = "696484065859076146";

    @Mock
    UserRepository userRepository;

    UserService userService;

    User mockUser1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockUser1 = User.builder()
                .id(MOCK_USER_ID_1)
                .email("mock-user-email")
                .displayName("mock-user-displayname")
                .build();

        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void shouldFindAllUsers() {
        // Given
        User mockUser2 = User.builder().build();
        when(userRepository.findAll()).thenReturn(List.of(mockUser1, mockUser2));

        // When
        List<User> returnedUsers = userService.findAll();

        // Then
        assert returnedUsers != null;
        assertEquals(2, returnedUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldFindUserById() {
        // Given
        when(userRepository.findById(anyString())).thenReturn(Optional.of(mockUser1));

        // When
        User returnedUser = userService.findById(MOCK_USER_ID_1);

        // Then
        assert returnedUser != null;
        assertEquals(MOCK_USER_ID_1, returnedUser.getId());
        verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveUser() {
        // Given
        when(userRepository.save(any(User.class))).thenReturn(mockUser1);

        // When
        User savedUser = userService.save(mockUser1);

        // Then
        assert savedUser != null;
        assertEquals(mockUser1.getId(), savedUser.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldSaveAllUsers() {
        // Given
        User mockUser2 = User.builder().build();
        when(userRepository.saveAll(anyIterable())).thenReturn(List.of(mockUser1, mockUser2));

        // When
        List<User> savedUsers = userService.saveAll(List.of(mockUser1,mockUser2));

        // Then
        assert savedUsers != null;
        assertEquals(2, savedUsers.size());
        verify(userRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteUser() {
        // When
        userService.delete(mockUser1);

        // Then
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    void shouldDeleteUserById() {
        // When
        userService.deleteById(MOCK_USER_ID_1);

        // Then
        verify(userRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldAddAGameRoleToUser() throws Exception {
        // Given
        GameRole mockGameRole2 = GameRole.builder().id("mock-gamerole-id-2").build();
        when(userRepository.findById(anyString())).thenReturn(Optional.of(mockUser1));
        when(userRepository.save(any(User.class))).thenReturn(mockUser1);

        // When
        User savedUser = userService.addGameroleToUser(mockUser1.getId(), mockGameRole2);

        // Then
        assert savedUser != null;
        assertEquals(1, savedUser.getGameRoles().size());
        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldFindExistingUser()  {
        // Given
        when(userRepository.findById(anyString())).thenReturn(Optional.of(mockUser1));
//        when(userRepository.save(any(User.class))).thenReturn(Mono.just(mockUser1));

        // When
        User returnedUser = userService.findOrCreateUser(mockUser1.getId(), mockUser1.getDisplayName(), mockUser1.getEmail());

        // Then
        assert returnedUser != null;
        assertEquals(mockUser1.getId(), returnedUser.getId());
        verify(userRepository, times(1)).findById(anyString());

    }

    @Test
    void shouldCreateNewUser()  {
        // Given
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(mockUser1);

        // When
        User returnedUser = userService.findOrCreateUser(mockUser1.getId(), mockUser1.getDisplayName(), mockUser1.getEmail());

        // Then
        assert returnedUser != null;
        assertEquals(mockUser1.getId(), returnedUser.getId());
        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldRemoveAGameRoleFromUser() throws Exception {
        // Given
        GameRole mockGameRole2 = GameRole.builder().id("mock-gamerole-id-2").build();
        Game mockGame = Game.builder().id("mock-game-id").build();
        mockGame.getGameRoles().add(mockGameRole2);
        mockGameRole2.setGame(mockGame);
        mockUser1.getGameRoles().add(mockGameRole2);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(mockUser1));
        when(userRepository.save(any(User.class))).thenReturn(mockUser1);

        // When
        userService.removeGameroleFromUser(mockUser1.getId(), mockGame.getId());

        // Then
        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }
}