package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Character;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CharacterReactiveRepository extends ReactiveMongoRepository<Character, String> {
}
