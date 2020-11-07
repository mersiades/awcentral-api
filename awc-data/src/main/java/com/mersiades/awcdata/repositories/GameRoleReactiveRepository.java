package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameRoleReactiveRepository extends ReactiveMongoRepository<GameRole, String> {

    Flux<GameRole> findAllByUser(User user);

    Flux<GameRole> findAllByGame(Game game);

    Mono<GameRole> findByGameIdAndUserId(String gameId, String userId);
}
