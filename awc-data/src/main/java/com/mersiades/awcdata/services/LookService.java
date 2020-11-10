package com.mersiades.awcdata.services;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Look;
import reactor.core.publisher.Flux;

public interface LookService extends ReactiveCrudService<Look, String> {

    Flux<Look> findAllByPlaybookType(Playbooks playbookType);
}
