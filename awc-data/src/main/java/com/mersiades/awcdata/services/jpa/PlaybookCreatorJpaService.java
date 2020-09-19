package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.PlaybookCreator;
import com.mersiades.awcdata.repositories.PlaybookCreatorRepository;
import com.mersiades.awcdata.services.PlaybookCreatorService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class PlaybookCreatorJpaService implements PlaybookCreatorService {

    private final PlaybookCreatorRepository playbookCreatorRepository;

    public PlaybookCreatorJpaService(PlaybookCreatorRepository playbookCreatorRepository) {
        this.playbookCreatorRepository = playbookCreatorRepository;
    }

    @Override
    public Set<PlaybookCreator> findAll() {
        Set<PlaybookCreator> playbookCreators = new HashSet<>();
        playbookCreatorRepository.findAll().forEach(playbookCreators::add);
        return playbookCreators;
    }

    @Override
    public PlaybookCreator findById(Long id) {
        Optional<PlaybookCreator> optionalPlaybookCreator = playbookCreatorRepository.findById(id);
        return optionalPlaybookCreator.orElse(null);
    }

    @Override
    public PlaybookCreator save(PlaybookCreator playbookCreator) {
        return playbookCreatorRepository.save(playbookCreator);
    }

    @Override
    public void delete(PlaybookCreator playbookCreator) {
        playbookCreatorRepository.delete(playbookCreator);
    }

    @Override
    public void deleteById(Long id) {
       playbookCreatorRepository.deleteById(id);
    }

    @Override
    public PlaybookCreator findByPlaybookType(Playbooks playbookType) {
        return playbookCreatorRepository.findByPlaybookType(playbookType);
    }
}
