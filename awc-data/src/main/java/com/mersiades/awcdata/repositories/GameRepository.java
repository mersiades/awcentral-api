package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {


    @Query(value = "{ _id : ?0 }", fields = "{ gameMessages: {$slice: [?1, ?2]}}")
    Mono<Game> findById(String gameId, Integer skip, Integer limit);

    Mono<Game> findByGameRoles(GameRole gameRole);

    Flux<Game> findAllByInviteesContaining(String email);
}
