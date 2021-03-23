package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Look;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LookRepository extends MongoRepository<Look, String> {
    List<Look> findAllByPlaybookType(PlaybookType playbookType);
}
