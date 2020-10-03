package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.UserRepository;
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
class UserJpaServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserJpaService service;

    User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User(1L);
    }

    @Test
    void findAll() {
        Set<User> returnUsers = new HashSet<>();
        returnUsers.add(new User(2L));
        returnUsers.add(new User("696484065859076146", "Mock Username"));

        when(userRepository.findAll()).thenReturn(returnUsers);

        Set<User> users = service.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void findById() {
        when(userRepository.findById(any())).thenReturn(Optional.of(mockUser));

        User user = service.findById(1L);

        assertNotNull(user);
        assertEquals(user.getId(), mockUser.getId());
    }

    @Test
    void findByIdNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        User user = service.findById(1L);

        assertNull(user);
    }

    @Test
    void save() {
        when(userRepository.save(any())).thenReturn(mockUser);

        User user = service.save(mockUser);

        assertNotNull(user);
        verify(userRepository).save((any()));
    }

    @Test
    void delete() {
        service.delete(mockUser);

        verify(userRepository).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(1L);

        verify(userRepository).deleteById(any());
    }
}