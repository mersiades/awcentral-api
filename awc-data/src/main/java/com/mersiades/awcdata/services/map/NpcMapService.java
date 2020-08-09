package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.Npc;
import com.mersiades.awcdata.services.NpcService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({ "default", "map"})
public class NpcMapService extends AbstractMapService<Npc, Long> implements NpcService {

    @Override
    public Set<Npc> findAll() {
        return super.findAll();
    }

    @Override
    public Npc findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Npc save(Npc npc) {
        return super.save(npc);
    }

    @Override
    public void delete(Npc npc) {
        super.delete(npc);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
