package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapServiceTest {

    UserMapService userMapService;

    final Long userId = 1L;

    @BeforeEach
    void setUp() {
        userMapService = new UserMapService();
        userMapService.save(new User(userId));
    }

    @Test
    void findAll() {
        Set<User> users = userMapService.findAll();

        assertEquals(1, users.size());
    }

    @Test
    void findById() {
        User user = userMapService.findById(userId);

        assertEquals(userId, user.getId());
    }

    @Test
    void saveWithGivenId() {
        Long id = 2L;
        User user2 = new User(id);

        User savedUser = userMapService.save(user2);

        assertEquals(id, savedUser.getId());
    }

    @Test
    void saveWithNoIdGiven() {
        User savedUser = userMapService.save(new User());

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
    }

    @Test
    void delete() {
        userMapService.delete(userMapService.findById(userId));

        assertEquals(0, userMapService.findAll().size());
    }

    @Test
    void deleteById() {
        userMapService.deleteById(userId);

        assertEquals(0, userMapService.findAll().size());
    }
}