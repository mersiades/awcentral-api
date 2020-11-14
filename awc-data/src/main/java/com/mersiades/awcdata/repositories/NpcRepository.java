package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Npc;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NpcRepository extends ReactiveMongoRepository<Npc, String> {
}
