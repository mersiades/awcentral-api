package com.mersiades.awccontent.repositories;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.StatsOption;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StatsOptionRepository extends MongoRepository<StatsOption, String> {
    List<StatsOption> findAllByPlaybookType(PlaybookType playbookType);
}
