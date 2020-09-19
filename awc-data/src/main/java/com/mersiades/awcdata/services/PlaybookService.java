package com.mersiades.awcdata.services;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Playbook;

public interface PlaybookService extends CrudService<Playbook, Long> {

    Playbook findByPlaybookType(Playbooks playbookType);
}
