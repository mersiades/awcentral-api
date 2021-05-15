package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.ThreatMapCreator;
import com.mersiades.awccontent.repositories.ThreatMapCreatorRepository;
import com.mersiades.awccontent.services.ThreatMapCreatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreatMapCreatorServiceImpl implements ThreatMapCreatorService {

    private final ThreatMapCreatorRepository threatMapCreatorRepository;

    public ThreatMapCreatorServiceImpl(ThreatMapCreatorRepository threatMapCreatorRepository) {
        this.threatMapCreatorRepository = threatMapCreatorRepository;
    }

    @Override
    public List<ThreatMapCreator> findAll() {
        return threatMapCreatorRepository.findAll();
    }

    @Override
    public ThreatMapCreator findById(String id) {
        return threatMapCreatorRepository.findById(id).orElseThrow();
    }

    @Override
    public ThreatMapCreator save(ThreatMapCreator threatMapCreator) {
        return threatMapCreatorRepository.save(threatMapCreator);
    }

    @Override
    public List<ThreatMapCreator> saveAll(List<ThreatMapCreator> threatMapCreators) {
        return threatMapCreatorRepository.saveAll(threatMapCreators);
    }

    @Override
    public void delete(ThreatMapCreator threatMapCreator) {
        threatMapCreatorRepository.delete(threatMapCreator);
    }

    @Override
    public void deleteById(String id) {
        threatMapCreatorRepository.deleteById(id);
    }
}
