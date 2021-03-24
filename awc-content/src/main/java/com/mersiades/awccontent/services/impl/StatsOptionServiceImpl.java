package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.StatsOption;
import com.mersiades.awccontent.repositories.StatsOptionRepository;
import com.mersiades.awccontent.services.StatsOptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsOptionServiceImpl implements StatsOptionService {

    private final StatsOptionRepository statsOptionRepository;

    public StatsOptionServiceImpl(StatsOptionRepository statsOptionRepository) {
        this.statsOptionRepository = statsOptionRepository;
    }

    @Override
    public List<StatsOption> findAll() {
        return statsOptionRepository.findAll();
    }

    @Override
    public StatsOption findById(String id) {
        return statsOptionRepository.findById(id).orElseThrow();
    }

    @Override
    public StatsOption save(StatsOption statsOption) {
        return statsOptionRepository.save(statsOption);
    }

    @Override
    public List<StatsOption> saveAll(List<StatsOption> statsOptions) {
        return statsOptionRepository.saveAll(statsOptions);
    }

    @Override
    public void delete(StatsOption statsOption) {
        statsOptionRepository.delete(statsOption);
    }

    @Override
    public void deleteById(String id) {
        statsOptionRepository.deleteById(id);
    }

    @Override
    public List<StatsOption> findAllByPlaybookType(PlaybookType playbookType) {
        return statsOptionRepository.findAllByPlaybookType(playbookType);
    }
}
