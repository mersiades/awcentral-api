package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.Playbooks;
import com.mersiades.awccontent.models.Name;
import reactor.core.publisher.Flux;

public interface NameService extends ReactiveCrudService<Name, String>{

    Flux<Name> findAllByPlaybookType(Playbooks playbookType);
}
