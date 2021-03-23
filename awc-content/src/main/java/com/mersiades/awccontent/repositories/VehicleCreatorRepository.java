package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.models.VehicleCreator;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleCreatorRepository extends MongoRepository<VehicleCreator, String> {
}
