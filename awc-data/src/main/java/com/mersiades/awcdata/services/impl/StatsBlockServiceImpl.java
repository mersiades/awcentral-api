package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.StatsBlock;
import com.mersiades.awcdata.repositories.StatsBlockRepository;
import com.mersiades.awcdata.services.StatsBlockService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StatsBlockServiceImpl implements StatsBlockService  {

    private final StatsBlockRepository statsBlockRepository;

    public StatsBlockServiceImpl(StatsBlockRepository statsBlockRepository) {
        this.statsBlockRepository = statsBlockRepository;
    }

    @Override
    public Flux<StatsBlock> findAll() {
        return statsBlockRepository.findAll();
    }

    @Override
    public Mono<StatsBlock> findById(String id) {
        return statsBlockRepository.findById(id);
    }

    @Override
    public Mono<StatsBlock> save(StatsBlock statsBlock) {
        return statsBlockRepository.save(statsBlock);
    }

    @Override
    public Flux<StatsBlock> saveAll(Flux<StatsBlock> statsBlocks) {
        return statsBlockRepository.saveAll(statsBlocks);
    }

    @Override
    public void delete(StatsBlock statsBlock) {
        statsBlockRepository.delete(statsBlock);
    }

    @Override
    public void deleteById(String id) {
        statsBlockRepository.deleteById(id);
    }
}
