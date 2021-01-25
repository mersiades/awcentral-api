package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Name;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.mersiades.awccontent.repositories.NameRepository;
import com.mersiades.awccontent.services.NameService;

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
    public Flux<Name> findAllByPlaybookType(PlaybookType playbookType) {
        return nameRepository.findAllByPlaybookType(playbookType);
    }
}
