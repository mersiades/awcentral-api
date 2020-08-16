package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.Npc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NpcMapServiceTest {

    NpcMapService npcMapService;

    final Long npcId = 1L;

    @BeforeEach
    void setUp() {
        npcMapService = new NpcMapService();
        npcMapService.save(new Npc(npcId, "Bruiser"));
    }

    @Test
    void findAll() {
        Set<Npc> npcs = npcMapService.findAll();

        assertEquals(1, npcs.size());
    }

    @Test
    void findById() {
        Npc npc = npcMapService.findById(npcId);

        assertEquals(npcId, npc.getId());
    }

    @Test
    void saveWithGivenId() {
        Long id = 2L;
        Npc npc2 = new Npc(id, "Tweeter");

        Npc savedNpc = npcMapService.save(npc2);

        assertEquals(id, savedNpc.getId());
    }

    @Test
    void saveWithNoIdGiven() {

        Npc savedNpc = npcMapService.save(new Npc());

        assertNotNull(savedNpc);
        assertNotNull(savedNpc.getId());
    }

    @Test
    void delete() {
        npcMapService.delete(npcMapService.findById(npcId));

        assertEquals(0, npcMapService.findAll().size());
    }

    @Test
    void deleteById() {
        npcMapService.deleteById(npcId);

        assertEquals(0, npcMapService.findAll().size());
    }
}