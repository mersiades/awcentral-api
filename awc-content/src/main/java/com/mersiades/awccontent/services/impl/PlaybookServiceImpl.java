package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Playbook;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.mersiades.awccontent.repositories.PlaybookRepository;
import com.mersiades.awccontent.services.PlaybookService;

@Service
public class PlaybookServiceImpl implements PlaybookService {

    private final PlaybookRepository playbookRepository;

    public PlaybookServiceImpl(PlaybookRepository playbookRepository) {
        this.playbookRepository = playbookRepository;
    }

    @Override
    public Flux<Playbook> findAll() {
        return playbookRepository.findAll();
    }

    @Override
    public Mono<Playbook> findById(String id) {
        return playbookRepository.findById(id);
    }

    @Override
    public Mono<Playbook> save(Playbook playbook) {
        return playbookRepository.save(playbook);
    }

    @Override
    public Flux<Playbook> saveAll(Flux<Playbook> playbooks) {
        return playbookRepository.saveAll(playbooks);
    }

    @Override
    public void delete(Playbook playbook) {
        playbookRepository.delete(playbook);
    }

    @Override
    public void deleteById(String id) {
        playbookRepository.deleteById(id);
    }

    @Override
    public Mono<Playbook> findByPlaybookType(PlaybookType playbookType) {
        return playbookRepository.findByPlaybookType(playbookType);
    }
}
