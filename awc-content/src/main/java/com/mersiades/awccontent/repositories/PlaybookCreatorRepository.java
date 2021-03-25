package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.PlaybookCreator;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlaybookCreatorRepository extends MongoRepository<PlaybookCreator, String> {
    PlaybookCreator findByPlaybookType(PlaybookType playbookType);
}
