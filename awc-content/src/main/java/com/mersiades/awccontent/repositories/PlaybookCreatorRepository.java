package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.PlaybookCreator;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PlaybookCreatorRepository extends ReactiveMongoRepository<PlaybookCreator, String> {
    Mono<PlaybookCreator> findByPlaybookType(PlaybookType playbookType);
}
