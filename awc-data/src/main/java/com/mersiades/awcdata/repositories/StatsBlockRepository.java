package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.StatsBlock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StatsBlockRepository extends ReactiveMongoRepository<StatsBlock, String> {
}
