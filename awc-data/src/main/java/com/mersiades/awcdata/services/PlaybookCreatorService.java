package com.mersiades.awcdata.services;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.PlaybookCreator;
import reactor.core.publisher.Mono;

public interface PlaybookCreatorService extends ReactiveCrudService<PlaybookCreator, String>{

    Mono<PlaybookCreator> findByPlaybookType(Playbooks playbookType);
}
