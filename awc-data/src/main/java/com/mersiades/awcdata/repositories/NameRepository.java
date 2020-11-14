package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Name;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface NameRepository extends ReactiveMongoRepository<Name, String> {
    Flux<Name> findAllByPlaybookType(Playbooks playbookType);
}
