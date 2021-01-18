package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.MoveKinds;
import com.mersiades.awccontent.enums.Playbooks;
import com.mersiades.awccontent.models.Move;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MoveRepository extends ReactiveMongoRepository<Move, String> {

    Flux<Move> findAllByPlaybookAndKind(Playbooks playbookType, MoveKinds kind);
}
