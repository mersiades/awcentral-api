package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.StatsOption;
import com.mersiades.awcdata.repositories.StatsOptionRepository;
import com.mersiades.awcdata.services.StatsOptionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StatsOptionServiceImpl implements StatsOptionService {

    private final StatsOptionRepository statsOptionRepository;

    public StatsOptionServiceImpl(StatsOptionRepository statsOptionRepository) {
        this.statsOptionRepository = statsOptionRepository;
    }

    @Override
    public Flux<StatsOption> findAll() {
        return statsOptionRepository.findAll();
    }

    @Override
    public Mono<StatsOption> findById(String id) {
        return statsOptionRepository.findById(id);
    }

    @Override
    public Mono<StatsOption> save(StatsOption statsOption) {
        return statsOptionRepository.save(statsOption);
    }

    @Override
    public Flux<StatsOption> saveAll(Flux<StatsOption> statsOptions) {
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
    public Flux<StatsOption> findAllByPlaybookType(Playbooks playbookType) {
        return statsOptionRepository.findAllByPlaybookType(playbookType);
    }
}
