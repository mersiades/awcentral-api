package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.StatsOption;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface StatsOptionRepository extends ReactiveMongoRepository<StatsOption, String> {
    Flux<StatsOption> findAllByPlaybookType(Playbooks playbookType);
}
