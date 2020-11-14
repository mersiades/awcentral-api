package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Look;
import com.mersiades.awcdata.repositories.LookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LookServiceImpl implements com.mersiades.awcdata.services.LookService {

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
    public Flux<Look> findAllByPlaybookType(Playbooks playbookType) {
        return lookRepository.findAllByPlaybookType(playbookType);
    }
}
