package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.PlaybookCreator;

public interface PlaybookCreatorService extends CrudService<PlaybookCreator, String>{

    PlaybookCreator findByPlaybookType(PlaybookType playbookType);
}
