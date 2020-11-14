package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Move;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MoveRepository extends ReactiveMongoRepository<Move, String> {
}
