package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.MoveType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Move;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MoveRepository extends ReactiveMongoRepository<Move, String> {

    Flux<Move> findAllByPlaybookAndKind(PlaybookType playbookType, MoveType kind);

    Mono<Move> findByName(String moveName);
}
