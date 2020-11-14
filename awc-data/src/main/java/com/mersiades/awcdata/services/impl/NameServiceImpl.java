package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Name;
import com.mersiades.awcdata.repositories.NameRepository;
import com.mersiades.awcdata.services.NameService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NameServiceImpl implements NameService {

    private final NameRepository nameRepository;

    public NameServiceImpl(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    @Override
    public Flux<Name> findAll() {
        return nameRepository.findAll();
    }

    @Override
    public Mono<Name> findById(String id) {
        return nameRepository.findById(id);
    }

    @Override
    public Mono<Name> save(Name name) {
        return nameRepository.save(name);
    }

    @Override
    public Flux<Name> saveAll(Flux<Name> moves) {
        return nameRepository.saveAll(moves);
    }

    @Override
    public void delete(Name name) {
        nameRepository.delete(name);
    }

    @Override
    public void deleteById(String id) {
        nameRepository.deleteById(id);
    }

    @Override
    public Flux<Name> findAllByPlaybookType(Playbooks playbookType) {
        return nameRepository.findAllByPlaybookType(playbookType);
    }
}
