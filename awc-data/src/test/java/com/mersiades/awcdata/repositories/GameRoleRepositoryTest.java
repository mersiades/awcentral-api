package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


//@DataMongoTest
@ExtendWith(SpringExtension.class)
@SpringBootApplication
public class GameRoleRepositoryTest {

    @Autowired
    private GameRoleRepository gameRoleRepository;

    User mockUser1;

    @BeforeEach
    public void setup() {
        mockUser1 = User.builder().id("mock-user-id-1").displayName("Mock User 1").email("user1@email.com").build();
        User mockUser2 = User.builder().id("mock-user-id-2").displayName("Mock User 2").email("user1@email.com").build();

        GameRole mockGameRole1 = GameRole.builder().id("mock-gamerole-id-1").role(Roles.MC).user(mockUser1).build();
        GameRole mockGameRole2 = GameRole.builder().id("mock-gamerole-id-2").role(Roles.PLAYER).user(mockUser1).build();
        GameRole mockGameRole3 = GameRole.builder().id("mock-gamerole-id-3").role(Roles.MC).user(mockUser2).build();
        GameRole mockGameRole4 = GameRole.builder().id("mock-gamerole-id-4").role(Roles.PLAYER).user(mockUser2).build();

        gameRoleRepository.deleteAll()
                .thenMany(Flux.just(mockGameRole1, mockGameRole2, mockGameRole3, mockGameRole4))
                .flatMap(gameRoleRepository::save)
                .doOnNext(System.out::println)
                .blockLast();
    }

    @Test
    public void shouldFindAllGameRolesForAUser() {
        StepVerifier.create(gameRoleRepository.findAllByUser(mockUser1))
                .expectSubscription()
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void shouldFindAllGameRolesForAUserId() {
        StepVerifier.create(gameRoleRepository.findAllByUserId(mockUser1.getId()))
                .expectSubscription()
                .expectNextCount(2)
                .verifyComplete();
    }
}