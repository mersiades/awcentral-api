package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Playbook;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PlaybookReactiveRepository extends ReactiveMongoRepository<Playbook, String> {

    Mono<Playbook> findByPlaybookType(Playbooks playbookType);
}
