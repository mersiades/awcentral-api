package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface GameReactiveRepository extends ReactiveMongoRepository<Game, String> {
    Mono<Game> findByGameRoles(GameRole gameRole);

    void deleteGameByTextChannelId(String textChannelId);

    Mono<Game> findGameByTextChannelId(String textChannelId);
}
