package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.PlaybookCreator;
import com.mersiades.awccontent.repositories.PlaybookCreatorRepository;
import com.mersiades.awccontent.services.PlaybookCreatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaybookCreatorServiceImpl implements PlaybookCreatorService {

    private final PlaybookCreatorRepository playbookCreatorRepository;

    public PlaybookCreatorServiceImpl(PlaybookCreatorRepository playbookCreatorRepository) {
        this.playbookCreatorRepository = playbookCreatorRepository;
    }

    @Override
    public List<PlaybookCreator> findAll() {
        return playbookCreatorRepository.findAll();
    }

    @Override
    public PlaybookCreator findById(String id) {
        return playbookCreatorRepository.findById(id).orElseThrow();
    }

    @Override
    public PlaybookCreator save(PlaybookCreator playbookCreator) {
        return playbookCreatorRepository.save(playbookCreator);
    }

    @Override
    public List<PlaybookCreator> saveAll(List<PlaybookCreator> playbookCreators) {
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
    public PlaybookCreator findByPlaybookType(PlaybookType playbookType) {
        return playbookCreatorRepository.findByPlaybookType(playbookType);
    }
}
