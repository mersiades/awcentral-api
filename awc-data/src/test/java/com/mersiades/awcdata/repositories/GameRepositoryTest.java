package com.mersiades.awcdata.repositories;

import com.mersiades.awccontent.enums.RoleType;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@Disabled
//@DataMongoTest
@ExtendWith(SpringExtension.class)
@SpringBootApplication // I think @DataMongoTest is not working because AwcWebApplication is in a different module
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    private GameRole mockGameRole1;

    @BeforeEach
    public void setup() {
        User mockUser1 = User.builder().id("mock-user-id-1").displayName("Mock User 1").email("user1@email.com").build();
        User mockUser2 = User.builder().id("mock-user-id-2").displayName("Mock User 2").email("user1@email.com").build();

        mockGameRole1 = GameRole.builder().id("mock-gamerole-id-1").role(RoleType.MC).userId(mockUser1.getId()).build();
        GameRole mockGameRole2 = GameRole.builder().id("mock-gamerole-id-2").role(RoleType.PLAYER).userId(mockUser1.getId()).build();
        GameRole mockGameRole3 = GameRole.builder().id("mock-gamerole-id-3").role(RoleType.MC).userId(mockUser2.getId()).build();
        GameRole mockGameRole4 = GameRole.builder().id("mock-gamerole-id-4").role(RoleType.PLAYER).userId(mockUser2.getId()).build();

        Game mockGame1 = Game.builder()
                .name("Mock Game 1")
                .mc(mockUser1)
                .players(List.of(mockUser2))
                .gameRoles(List.of(mockGameRole1, mockGameRole4))
                .invitees(List.of("user3@email.com"))
                .build();
        Game mockGame2 = Game.builder()
                .name("Mock Game 2")
                .mc(mockUser2)
                .players(List.of(mockUser1))
                .gameRoles(List.of(mockGameRole2, mockGameRole3))
                .invitees(List.of("user3@email.com"))
                .build();

        gameRepository.deleteAll();
        gameRepository.saveAll(List.of(mockGame1, mockGame2));
//                .thenMany(Flux.just(mockGame1, mockGame2))
//                .flatMap(gameRepository::save)
//                .doOnNext(System.out::println)
//                .blockLast();
    }

    @Test
    public void shouldFindByGameRoles() {
        Game returnedGame = gameRepository.findByGameRoles(mockGameRole1);
//        StepVerifier.create(gameRepository.findByGameRoles(mockGameRole1))
//                .expectSubscription()
//                .expectNextCount(1)
//                .verifyComplete();
    }

//    @Test
//    public void shouldFindAllByInvitees() {
//        StepVerifier.create(gameRepository.findAllByInviteesContaining("user3@email.com"))
//                .expectSubscription()
//                .expectNextCount(2)
//                .verifyComplete();
//    }



}