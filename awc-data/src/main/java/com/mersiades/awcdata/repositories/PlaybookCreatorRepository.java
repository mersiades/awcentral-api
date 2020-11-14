package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.PlaybookCreator;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PlaybookCreatorRepository extends ReactiveMongoRepository<PlaybookCreator, String> {
    Mono<PlaybookCreator> findByPlaybookType(Playbooks playbookType);
}
