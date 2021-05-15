package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Ally;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AllyRepository extends MongoRepository<Ally, String> {
}
