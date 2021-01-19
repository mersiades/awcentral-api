package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Name;
import reactor.core.publisher.Flux;

public interface NameService extends ReactiveCrudService<Name, String>{

    Flux<Name> findAllByPlaybookType(PlaybookType playbookType);
}
