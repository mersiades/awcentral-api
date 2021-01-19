package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Look;
import reactor.core.publisher.Flux;

public interface LookService extends ReactiveCrudService<Look, String> {
    Flux<Look> findAllByPlaybookType(PlaybookType playbookType);
}
