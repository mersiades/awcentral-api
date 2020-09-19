package com.mersiades.awcdata.services;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.PlaybookCreator;

public interface PlaybookCreatorService extends CrudService<PlaybookCreator, Long>{

    PlaybookCreator findByPlaybookType(Playbooks playbookType);
}
