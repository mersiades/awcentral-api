package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.PlaybookCreator;
import reactor.core.publisher.Mono;

public interface PlaybookCreatorService extends ReactiveCrudService<PlaybookCreator, String>{

    Mono<PlaybookCreator> findByPlaybookType(PlaybookType playbookType);
}
