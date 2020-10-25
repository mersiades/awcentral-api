package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Name;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface NameRepository extends CrudRepository<Name, String> {

    Set<Name> findAllByPlaybookType(Playbooks playbookType);
}
