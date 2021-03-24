package com.mersiades.awccontent.repositories;


import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Name;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NameRepository extends MongoRepository<Name, String> {
    List<Name> findAllByPlaybookType(PlaybookType playbookType);
}
