package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Character;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository extends MongoRepository<Character, String> {
}
