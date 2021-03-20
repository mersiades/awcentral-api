package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.models.McContent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface McContentRepository extends ReactiveMongoRepository<McContent, String> {
}
