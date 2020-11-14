package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.PlaybookCreator;
import com.mersiades.awcdata.repositories.PlaybookCreatorReactiveRepository;
import com.mersiades.awcdata.services.PlaybookCreatorService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Profile("jpa")
public class PlaybookCreatorServiceImpl implements PlaybookCreatorService {

    private final PlaybookCreatorReactiveRepository playbookCreatorRepository;

    public PlaybookCreatorServiceImpl(PlaybookCreatorReactiveRepository playbookCreatorRepository) {
        this.playbookCreatorRepository = playbookCreatorRepository;
    }

    @Override
    public Flux<PlaybookCreator> findAll() {
        return playbookCreatorRepository.findAll();
    }

    @Override
    public Mono<PlaybookCreator> findById(String id) {
        return playbookCreatorRepository.findById(id);
    }

    @Override
    public Mono<PlaybookCreator> save(PlaybookCreator playbookCreator) {
        return playbookCreatorRepository.save(playbookCreator);
    }

    @Override
    public Flux<PlaybookCreator> saveAll(Flux<PlaybookCreator> playbookCreators) {
        return playbookCreatorRepository.saveAll(playbookCreators);
    }

    @Override
    public void delete(PlaybookCreator playbookCreator) {
        playbookCreatorRepository.delete(playbookCreator);
    }

    @Override
    public void deleteById(String id) {
       playbookCreatorRepository.deleteById(id);
    }

    @Override
    public Mono<PlaybookCreator> findByPlaybookType(Playbooks playbookType) {
        return playbookCreatorRepository.findByPlaybookType(playbookType);
    }
}
