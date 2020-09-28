package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.StatsOption;
import com.mersiades.awcdata.repositories.StatsOptionRepository;
import com.mersiades.awcdata.services.StatsOptionService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class StatsOptionJpaService implements StatsOptionService {

    private final StatsOptionRepository statsOptionRepository;

    public StatsOptionJpaService(StatsOptionRepository statsOptionRepository) {
        this.statsOptionRepository = statsOptionRepository;
    }

    @Override
    public Set<StatsOption> findAll() {
        Set<StatsOption> statsOptions = new HashSet<>();
        statsOptionRepository.findAll().forEach(statsOptions::add);
        return statsOptions;
    }

    @Override
    public StatsOption findById(Long id) {
        Optional<StatsOption> optionalStatsOption = statsOptionRepository.findById(id);
        return optionalStatsOption.orElse(null);
    }

    @Override
    public StatsOption save(StatsOption statsOption) {
        return statsOptionRepository.save(statsOption);
    }

    @Override
    public void delete(StatsOption statsOption) {
        statsOptionRepository.delete(statsOption);
    }

    @Override
    public void deleteById(Long id) {
        statsOptionRepository.deleteById(id);
    }

    @Override
    public Set<StatsOption> findAllByPlaybookType(Playbooks playbookType) {
        return statsOptionRepository.findAllByPlaybookType(playbookType);
    }
}
