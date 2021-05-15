package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.models.AllyCreator;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AllyCreatorRepository extends MongoRepository<AllyCreator, String> {
}
