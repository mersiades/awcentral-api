package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.StatModifier;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StatModifierRepository extends ReactiveMongoRepository<StatModifier, String> {
}
