package com.mersiades.awcdata.services;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Name;
import reactor.core.publisher.Flux;

public interface NameService extends ReactiveCrudService<Name, String>{

    Flux<Name> findAllByPlaybookType(Playbooks playbookType);
}
