package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.Playbooks;
import com.mersiades.awccontent.models.StatsOption;
import reactor.core.publisher.Flux;

public interface StatsOptionService extends ReactiveCrudService<StatsOption, String> {

    Flux<StatsOption> findAllByPlaybookType(Playbooks playbookType);
}
