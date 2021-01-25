package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Look;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.mersiades.awccontent.repositories.LookRepository;
import com.mersiades.awccontent.services.LookService;

@Service
public class LookServiceImpl implements LookService {

    private final LookRepository lookRepository;

    public LookServiceImpl(LookRepository lookRepository) {
        this.lookRepository = lookRepository;
    }

    @Override
    public Flux<Look> findAll() {
        return lookRepository.findAll();
    }

    @Override
    public Mono<Look> findById(String id) {
        return lookRepository.findById(id);
    }

    @Override
    public Mono<Look> save(Look look) {
        return lookRepository.save(look);
    }

    @Override
    public Flux<Look> saveAll(Flux<Look> looks) {
        return lookRepository.saveAll(looks);
    }

    @Override
    public void delete(Look look) {
        this.lookRepository.delete(look);
    }

    @Override
    public void deleteById(String id) {
        this.lookRepository.deleteById(id);
    }

    @Override
    public Flux<Look> findAllByPlaybookType(PlaybookType playbookType) {
        return lookRepository.findAllByPlaybookType(playbookType);
    }
}
