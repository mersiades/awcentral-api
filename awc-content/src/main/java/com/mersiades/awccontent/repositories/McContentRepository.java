package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.models.McContent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface McContentRepository extends MongoRepository<McContent, String> {
}
