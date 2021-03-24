package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.Npc;
import com.mersiades.awcdata.repositories.NpcRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NpcServiceImpl implements com.mersiades.awcdata.services.NpcService {

    private final NpcRepository npcRepository;

    public NpcServiceImpl(NpcRepository npcRepository) {
        this.npcRepository = npcRepository;
    }

    @Override
    public List<Npc> findAll() {
        return npcRepository.findAll();
    }

    @Override
    public Npc findById(String id) {
        return npcRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Npc save(Npc npc) {
        return npcRepository.save(npc);
    }

    @Override
    public List<Npc> saveAll(List<Npc> npcs) {
        return npcRepository.saveAll(npcs);
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
