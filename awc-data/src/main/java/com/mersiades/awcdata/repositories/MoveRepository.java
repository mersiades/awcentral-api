package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.MoveKinds;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Move;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MoveRepository extends ReactiveMongoRepository<Move, String> {

    Flux<Move> findAllByPlaybookAndKind(Playbooks playbookType, MoveKinds kind);
}
