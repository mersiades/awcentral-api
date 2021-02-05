package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.models.ThreatCreator;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ThreatCreatorRepository extends ReactiveMongoRepository<ThreatCreator, String> {
}
