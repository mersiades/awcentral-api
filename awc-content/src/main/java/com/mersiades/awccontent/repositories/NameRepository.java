package com.mersiades.awccontent.repositories;


import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Name;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface NameRepository extends ReactiveMongoRepository<Name, String> {
    Flux<Name> findAllByPlaybookType(PlaybookType playbookType);
}
