package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.Npc;
import com.mersiades.awcdata.services.NpcService;

import java.util.Set;

public class NpcMapService extends AbstractMapService<Npc, Long> implements NpcService {

    @Override
    public Set<Npc> findAll() {
        return super.findAll();
    }

    @Override
    public Npc findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public Npc save(Npc object) {
        return super.save(object);
    }

    @Override
    public void delete(Npc object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        super.deleteById(aLong);
    }
}
