package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Npc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NpcRepository extends MongoRepository<Npc, String> {
}
