package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.models.Threat;
import com.mersiades.awcdata.repositories.ThreatRepository;
import com.mersiades.awcdata.services.ThreatService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class ThreatJpaService implements ThreatService {

    private final ThreatRepository threatRepository;

    public ThreatJpaService(ThreatRepository threatRepository) {
        this.threatRepository = threatRepository;
    }

    @Override
    public Set<Threat> findAll() {
        Set<Threat> threats = new HashSet<>();
        threatRepository.findAll().forEach(threats::add);
        return threats;
    }

    @Override
    public Threat findById(Long id) {
        Optional<Threat> optionalThreat = threatRepository.findById(id);
        return optionalThreat.orElse(null);
    }

    @Override
    public Threat save(Threat threat) {
        return threatRepository.save(threat);
    }

    @Override
    public void delete(Threat threat) {
       threatRepository.delete(threat);
    }

    @Override
    public void deleteById(Long id) {
       threatRepository.deleteById(id);
    }
}
