package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Playbook;
import com.mersiades.awcdata.repositories.PlaybookRepository;
import com.mersiades.awcdata.services.PlaybookService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class PlaybookJpaService implements PlaybookService {

    private final PlaybookRepository playbookRepository;

    public PlaybookJpaService(PlaybookRepository playbookRepository) {
        this.playbookRepository = playbookRepository;
    }

    @Override
    public Set<Playbook> findAll() {
        Set<Playbook> playbooks = new HashSet<>();
        playbookRepository.findAll().forEach(playbooks::add);
        return playbooks;
    }

    @Override
    public Playbook findById(String id) {
        Optional<Playbook> optionalPlaybook = playbookRepository.findById(id);
        return optionalPlaybook.orElse(null);
    }

    @Override
    public Playbook save(Playbook playbook) {
        return playbookRepository.save(playbook);
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
    public Playbook findByPlaybookType(Playbooks playbookType) {
        return playbookRepository.findByPlaybookType(playbookType);
    }
}
