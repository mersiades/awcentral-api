package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.Playbooks;
import com.mersiades.awccontent.models.StatsOption;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.mersiades.awccontent.repositories.StatsOptionRepository;
import com.mersiades.awccontent.services.StatsOptionService;

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
