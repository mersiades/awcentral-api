package com.mersiades.awcdata.services;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.StatsOption;
import reactor.core.publisher.Flux;

public interface StatsOptionService extends ReactiveCrudService<StatsOption, String> {

    Flux<StatsOption> findAllByPlaybookType(Playbooks playbookType);
}
