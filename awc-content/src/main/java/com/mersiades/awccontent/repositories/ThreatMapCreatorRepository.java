package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.models.ThreatMapCreator;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThreatMapCreatorRepository extends MongoRepository<ThreatMapCreator, String> {
}
