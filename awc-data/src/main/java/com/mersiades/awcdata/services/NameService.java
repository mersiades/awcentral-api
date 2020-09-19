package com.mersiades.awcdata.services;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Name;

import java.util.Set;

public interface NameService extends CrudService<Name, Long>{

    Set<Name> findAllByPlaybookType(Playbooks playbookType);
}
