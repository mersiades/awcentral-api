package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.Threat;
import com.mersiades.awcdata.repositories.ThreatReactiveRepository;
import com.mersiades.awcdata.services.ThreatService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Profile("jpa")
public class ThreatServiceImpl implements ThreatService {

    private final ThreatReactiveRepository threatRepository;

    public ThreatServiceImpl(ThreatReactiveRepository threatRepository) {
        this.threatRepository = threatRepository;
    }

    @Override
    public Flux<Threat> findAll() {
        return threatRepository.findAll();
    }

    @Override
    public Mono<Threat> findById(String id) {
        return threatRepository.findById(id);
    }

    @Override
    public Mono<Threat> save(Threat threat) {
        return threatRepository.save(threat);
    }

    @Override
    public Flux<Threat> saveAll(Flux<Threat> threats) {
        return threatRepository.saveAll(threats);
    }

    @Override
    public void delete(Threat threat) {
       threatRepository.delete(threat);
    }

    @Override
    public void deleteById(String id) {
       threatRepository.deleteById(id);
    }
}
