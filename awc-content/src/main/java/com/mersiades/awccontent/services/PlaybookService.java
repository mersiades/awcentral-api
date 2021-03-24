package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Playbook;

public interface PlaybookService extends CrudService<Playbook, String> {

    Playbook findByPlaybookType(PlaybookType playbookType);
}
