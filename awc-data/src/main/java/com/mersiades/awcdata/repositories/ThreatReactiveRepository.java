package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Threat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ThreatReactiveRepository extends ReactiveMongoRepository<Threat, String> {
}