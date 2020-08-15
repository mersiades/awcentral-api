package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.Threat;
import com.mersiades.awcdata.services.ThreatService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({ "default", "map"})
public class ThreatMapService  extends AbstractMapService<Threat, Long> implements ThreatService {

    @Override
    public Set<Threat> findAll() {
        return super.findAll();
    }

    @Override
    public Threat findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Threat save(Threat threat) {
        return super.save(threat);
    }

    @Override
    public void delete(Threat threat) {
        super.delete(threat);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
