package com.mersiades.awcdata.services;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.StatsOption;

import java.util.Set;

public interface StatsOptionService extends CrudService<StatsOption, String> {

    Set<StatsOption> findAllByPlaybookType(Playbooks playbookType);
}
