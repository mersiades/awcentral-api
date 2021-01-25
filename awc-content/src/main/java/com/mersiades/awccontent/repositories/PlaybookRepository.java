package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Playbook;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PlaybookRepository extends ReactiveMongoRepository<Playbook, String> {

    Mono<Playbook> findByPlaybookType(PlaybookType playbookType);
}
