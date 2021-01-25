package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Look;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface LookRepository extends ReactiveMongoRepository<Look, String> {
    Flux<Look> findAllByPlaybookType(PlaybookType playbookType);
}
