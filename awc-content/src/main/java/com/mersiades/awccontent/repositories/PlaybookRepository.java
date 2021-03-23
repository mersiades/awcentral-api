package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Playbook;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlaybookRepository extends MongoRepository<Playbook, String> {

    Playbook findByPlaybookType(PlaybookType playbookType);
}
