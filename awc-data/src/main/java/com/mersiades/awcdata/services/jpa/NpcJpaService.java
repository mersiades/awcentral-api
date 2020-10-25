package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.models.Npc;
import com.mersiades.awcdata.repositories.NpcRepository;
import com.mersiades.awcdata.services.NpcService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class NpcJpaService implements NpcService {

    private final NpcRepository npcRepository;

    public NpcJpaService(NpcRepository npcRepository) {
        this.npcRepository = npcRepository;
    }

    @Override
    public Set<Npc> findAll() {
        Set<Npc> npcs = new HashSet<>();
        npcRepository.findAll().forEach(npcs::add);
        return npcs;
    }

    @Override
    public Npc findById(String id) {
        Optional<Npc> optionalNpc = npcRepository.findById(id);
        return optionalNpc.orElse(null);
    }

    @Override
    public Npc save(Npc npc) {
        return npcRepository.save(npc);
    }

    @Override
    public void delete(Npc npc) {
         npcRepository.delete(npc);
    }

    @Override
    public void deleteById(String id) {
         npcRepository.deleteById(id);
    }
}
