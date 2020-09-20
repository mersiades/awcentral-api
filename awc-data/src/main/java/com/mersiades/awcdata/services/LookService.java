package com.mersiades.awcdata.services;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Look;

import java.util.Set;

public interface LookService extends CrudService<Look, Long> {

    Set<Look> findAllByPlaybookType(Playbooks playbookType);
}
