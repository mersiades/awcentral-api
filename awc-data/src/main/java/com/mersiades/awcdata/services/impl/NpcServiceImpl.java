package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.Npc;
import com.mersiades.awcdata.repositories.NpcReactiveRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Profile("jpa")
public class NpcServiceImpl implements com.mersiades.awcdata.services.NpcService {

    private final NpcReactiveRepository npcRepository;

    public NpcServiceImpl(NpcReactiveRepository npcRepository) {
        this.npcRepository = npcRepository;
    }

    @Override
    public Flux<Npc> findAll() {
        return npcRepository.findAll();
    }

    @Override
    public Mono<Npc> findById(String id) {
        return npcRepository.findById(id);
    }

    @Override
    public Mono<Npc> save(Npc npc) {
        return npcRepository.save(npc);
    }

    @Override
    public Flux<Npc> saveAll(Flux<Npc> npcs) {
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
