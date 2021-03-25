package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.CharacterStat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterStatRepository extends MongoRepository<CharacterStat, String> {
}
