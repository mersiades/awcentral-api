package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.StatsOption;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface StatsOptionRepository extends ReactiveMongoRepository<StatsOption, String> {
    Flux<StatsOption> findAllByPlaybookType(PlaybookType playbookType);
}
