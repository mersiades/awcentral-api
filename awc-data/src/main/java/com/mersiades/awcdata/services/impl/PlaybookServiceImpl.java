package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Playbook;
import com.mersiades.awcdata.repositories.PlaybookRepository;
import com.mersiades.awcdata.services.PlaybookService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Playbook> findByPlaybookType(Playbooks playbookType) {
        return playbookRepository.findByPlaybookType(playbookType);
    }
}
