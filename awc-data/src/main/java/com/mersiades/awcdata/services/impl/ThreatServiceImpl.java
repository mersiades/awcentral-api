package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.Threat;
import com.mersiades.awcdata.repositories.ThreatRepository;
import com.mersiades.awcdata.services.ThreatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ThreatServiceImpl implements ThreatService {

    private final ThreatRepository threatRepository;

    public ThreatServiceImpl(ThreatRepository threatRepository) {
        this.threatRepository = threatRepository;
    }

    @Override
    public List<Threat> findAll() {
        return threatRepository.findAll();
    }

    @Override
    public Threat findById(String id) {
        return threatRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Threat save(Threat threat) {
        return threatRepository.save(threat);
    }

    @Override
    public List<Threat> saveAll(List<Threat> threats) {
        return threatRepository.saveAll(threats);
    }

    @Override
    public void delete(Threat threat) {
       threatRepository.delete(threat);
    }

    @Override
    public void deleteById(String id) {
       threatRepository.deleteById(id);
    }
}
