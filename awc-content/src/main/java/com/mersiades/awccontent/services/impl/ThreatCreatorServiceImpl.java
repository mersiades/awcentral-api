package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.ThreatCreator;
import com.mersiades.awccontent.repositories.ThreatCreatorRepository;
import com.mersiades.awccontent.services.ThreatCreatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreatCreatorServiceImpl implements ThreatCreatorService {

    private final ThreatCreatorRepository threatCreatorRepository;

    public ThreatCreatorServiceImpl(ThreatCreatorRepository threatCreatorRepository) {
        this.threatCreatorRepository = threatCreatorRepository;
    }

    @Override
    public List<ThreatCreator> findAll() {
        return threatCreatorRepository.findAll();
    }

    @Override
    public ThreatCreator findById(String id) {
        return threatCreatorRepository.findById(id).orElseThrow();
    }

    @Override
    public ThreatCreator save(ThreatCreator threatCreator) {
        return threatCreatorRepository.save(threatCreator);
    }

    @Override
    public List<ThreatCreator> saveAll(List<ThreatCreator> threatCreatorList) {
        return threatCreatorRepository.saveAll(threatCreatorList);
    }

    @Override
    public void delete(ThreatCreator threatCreator) {
        threatCreatorRepository.delete(threatCreator);
    }

    @Override
    public void deleteById(String id) {
        threatCreatorRepository.deleteById(id);
    }
}
