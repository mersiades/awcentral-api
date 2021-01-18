package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.models.StatModifier;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StatModifierRepository extends ReactiveMongoRepository<StatModifier, String> {
}
