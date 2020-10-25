package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.PlaybookCreator;
import org.springframework.data.repository.CrudRepository;

public interface PlaybookCreatorRepository extends CrudRepository<PlaybookCreator, String> {

    PlaybookCreator findByPlaybookType(Playbooks playbookType);
}
