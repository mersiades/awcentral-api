package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Threat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThreatRepository extends MongoRepository<Threat, String> {
}
