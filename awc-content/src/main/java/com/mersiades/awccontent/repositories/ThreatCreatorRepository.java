package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.models.ThreatCreator;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThreatCreatorRepository extends MongoRepository<ThreatCreator, String> {
}
