package com.mersiades.awcdata.services;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Playbook;
import reactor.core.publisher.Mono;

public interface PlaybookService extends ReactiveCrudService<Playbook, String> {

    Mono<Playbook> findByPlaybookType(Playbooks playbookType);
}
