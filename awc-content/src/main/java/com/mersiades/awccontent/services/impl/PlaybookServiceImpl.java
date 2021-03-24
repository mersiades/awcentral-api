package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Playbook;
import com.mersiades.awccontent.repositories.PlaybookRepository;
import com.mersiades.awccontent.services.PlaybookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaybookServiceImpl implements PlaybookService {

    private final PlaybookRepository playbookRepository;

    public PlaybookServiceImpl(PlaybookRepository playbookRepository) {
        this.playbookRepository = playbookRepository;
    }

    @Override
    public List<Playbook> findAll() {
        return playbookRepository.findAll();
    }

    @Override
    public Playbook findById(String id) {
        return playbookRepository.findById(id).orElseThrow();
    }

    @Override
    public Playbook save(Playbook playbook) {
        return playbookRepository.save(playbook);
    }

    @Override
    public List<Playbook> saveAll(List<Playbook> playbooks) {
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
    public Playbook findByPlaybookType(PlaybookType playbookType) {
        return playbookRepository.findByPlaybookType(playbookType);
    }
}
