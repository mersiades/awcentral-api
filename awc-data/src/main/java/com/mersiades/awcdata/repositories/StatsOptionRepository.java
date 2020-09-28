package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.StatsOption;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface StatsOptionRepository extends CrudRepository<StatsOption, Long> {

    Set<StatsOption> findAllByPlaybookType(Playbooks playbookType);
}
