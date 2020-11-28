package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.CharacterStat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CharacterStatRepository extends ReactiveMongoRepository<CharacterStat, String> {
}
