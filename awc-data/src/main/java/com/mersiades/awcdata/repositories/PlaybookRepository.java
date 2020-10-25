package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Playbook;
import org.springframework.data.repository.CrudRepository;

public interface PlaybookRepository extends CrudRepository<Playbook, String> {

    Playbook findByPlaybookType(Playbooks playbookType);
}
