package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.models.StatModifier;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatModifierRepository extends MongoRepository<StatModifier, String> {
}
