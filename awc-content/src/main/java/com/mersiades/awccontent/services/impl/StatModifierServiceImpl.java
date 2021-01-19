package com.mersiades.awccontent.services.impl;


import com.mersiades.awccontent.models.StatModifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.mersiades.awccontent.repositories.StatModifierRepository;
import com.mersiades.awccontent.services.StatModifierService;

@Service
public class StatModifierServiceImpl implements StatModifierService {

    private final StatModifierRepository statModifierRepository;

    public StatModifierServiceImpl(StatModifierRepository statModifierRepository) {
        this.statModifierRepository = statModifierRepository;
    }

    @Override
    public Flux<StatModifier> findAll() {
        return statModifierRepository.findAll();
    }

    @Override
    public Mono<StatModifier> findById(String id) {
        return statModifierRepository.findById(id);
    }

    @Override
    public Mono<StatModifier> save(StatModifier statModifier) {
        return statModifierRepository.save(statModifier);
    }

    @Override
    public Flux<StatModifier> saveAll(Flux<StatModifier> statModifierFlux) {
        return statModifierRepository.saveAll(statModifierFlux);
    }

    @Override
    public void delete(StatModifier statModifier) {
        statModifierRepository.delete(statModifier);
    }

    @Override
    public void deleteById(String id) {
        statModifierRepository.deleteById(id);
    }
}
