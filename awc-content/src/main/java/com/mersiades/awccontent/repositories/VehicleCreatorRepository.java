package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.models.VehicleCreator;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VehicleCreatorRepository extends ReactiveMongoRepository<VehicleCreator, String> {
}
