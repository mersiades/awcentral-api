package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.ThreatCreator;
import com.mersiades.awccontent.repositories.ThreatCreatorRepository;
import com.mersiades.awccontent.services.ThreatCreatorService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ThreatCreatorServiceImpl implements ThreatCreatorService {

    private final ThreatCreatorRepository threatCreatorRepository;

    public ThreatCreatorServiceImpl(ThreatCreatorRepository threatCreatorRepository) {
        this.threatCreatorRepository = threatCreatorRepository;
    }

    @Override
    public Flux<ThreatCreator> findAll() {
        return threatCreatorRepository.findAll();
    }

    @Override
    public Mono<ThreatCreator> findById(String id) {
        return threatCreatorRepository.findById(id);
    }

    @Override
    public Mono<ThreatCreator> save(ThreatCreator threatCreator) {
        return threatCreatorRepository.save(threatCreator);
    }

    @Override
    public Flux<ThreatCreator> saveAll(Flux<ThreatCreator> threatCreatorFlux) {
        return threatCreatorRepository.saveAll(threatCreatorFlux);
    }

    @Override
    public void delete(ThreatCreator threatCreator) {
        threatCreatorRepository.delete(threatCreator);
    }

    @Override
    public void deleteById(String id) {
        threatCreatorRepository.deleteById(id);
    }
}
