package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Move;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MoveReactiveRepository extends ReactiveMongoRepository<Move, String> {
}
