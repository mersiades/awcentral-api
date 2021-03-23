package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.StatsOption;

import java.util.List;

public interface StatsOptionService extends CrudService<StatsOption, String> {

    List<StatsOption> findAllByPlaybookType(PlaybookType playbookType);
}
