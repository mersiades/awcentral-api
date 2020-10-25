package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Look;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface LookRepository extends CrudRepository<Look, String> {

    Set<Look> findAllByPlaybookType(Playbooks playbookType);
}
