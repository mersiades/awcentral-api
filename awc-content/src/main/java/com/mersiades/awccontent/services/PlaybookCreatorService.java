package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.Playbooks;
import com.mersiades.awccontent.models.PlaybookCreator;
import reactor.core.publisher.Mono;

public interface PlaybookCreatorService extends ReactiveCrudService<PlaybookCreator, String>{

    Mono<PlaybookCreator> findByPlaybookType(Playbooks playbookType);
}
