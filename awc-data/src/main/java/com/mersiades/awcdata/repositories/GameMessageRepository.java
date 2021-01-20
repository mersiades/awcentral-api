package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.GameMessage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface GameMessageRepository extends ReactiveMongoRepository<GameMessage, String> {
}
