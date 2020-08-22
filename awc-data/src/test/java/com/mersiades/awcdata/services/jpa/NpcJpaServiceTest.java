package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.models.Npc;
import com.mersiades.awcdata.repositories.NpcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NpcJpaServiceTest {

    @Mock
    NpcRepository npcRepository;

    @InjectMocks
    NpcJpaService service;

    Npc mockNpc;

    @BeforeEach
    void setUp() {
        mockNpc = new Npc(1L, "Butch");
    }

    @Test
    void findAll() {
        Set<Npc> returnNpcs = new HashSet<>();
        returnNpcs.add(new Npc(2L, "Sarah"));
        returnNpcs.add(new Npc(3L, "Reginald"));

        when(npcRepository.findAll()).thenReturn(returnNpcs);

        Set<Npc> npcs = service.findAll();

        assertNotNull(npcs);
        assertEquals(2, npcs.size());
    }

    @Test
    void findById() {
        when(npcRepository.findById(any())).thenReturn(Optional.of(mockNpc));

        Npc npc = service.findById(1L);

        assertNotNull(npc);
    }

    @Test
    void save() {
        when(npcRepository.save(any())).thenReturn(mockNpc);

        Npc npc = npcRepository.save(mockNpc);

        assertNotNull(npc);
        assertEquals(npc.getId(), mockNpc.getId());
        verify(npcRepository).save(any());
    }

    @Test
    void delete() {
        npcRepository.delete(mockNpc);

        verify(npcRepository).delete(any());
    }

    @Test
    void deleteById() {
        npcRepository.deleteById(1L);

        verify(npcRepository).deleteById(any());
    }
}