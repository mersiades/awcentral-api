package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.Npc;
import com.mersiades.awcdata.repositories.NpcRepository;
import com.mersiades.awcdata.services.NpcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NpcServiceImplTest {

    public static final String MOCK_NPC_ID_1 = "mock-npc-id-1";

    @Mock
    NpcRepository npcRepository;

    NpcService npcService;

    Npc mockNpc1;

    GameRole mockGameRole1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockGameRole1 = GameRole.builder().build();

        mockNpc1 = Npc.builder()
                .id(MOCK_NPC_ID_1)
                .name("Mrs Mock NPC")
                .description("just a mock npc")
//                .gameRole(mockGameRole1)
                .build();

        npcService = new NpcServiceImpl(npcRepository);
    }

    @Test
    void shouldFindAllNpcs() {
        // Given
        Npc mockNpc2 = Npc.builder().build();
        when(npcRepository.findAll()).thenReturn(List.of(mockNpc1, mockNpc2));

        // When
        List<Npc> returnedNpcs = npcService.findAll();

        // Then
        assert returnedNpcs != null;
        assertEquals(2, returnedNpcs.size());
        verify(npcRepository, times(1)).findAll();
    }

    @Test
    void shouldFindNpcById() {
        // Given
        when(npcRepository.findById(anyString())).thenReturn(Optional.of(mockNpc1));

        // When
        Npc returnedNpc = npcService.findById(MOCK_NPC_ID_1);

        // Then
        assert returnedNpc != null;
        assertEquals(MOCK_NPC_ID_1, returnedNpc.getId());
        verify(npcRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveNpc() {
        // Given
        when(npcRepository.save(any(Npc.class))).thenReturn(mockNpc1);

        // When
        Npc savedNpc = npcService.save(mockNpc1);

        // Then
        assert savedNpc != null;
        assertEquals(mockNpc1.getId(), savedNpc.getId());
        verify(npcRepository, times(1)).save(any(Npc.class));
    }

    @Test
    void shouldSaveAllNpcs() {
        // Given
        Npc mockNpc2 = Npc.builder().build();
        when(npcRepository.saveAll(anyIterable())).thenReturn(List.of(mockNpc1, mockNpc2));

        // When
        List<Npc> savedNpcs = npcService.saveAll(List.of(mockNpc1,mockNpc2));

        // Then
        assert savedNpcs != null;
        assertEquals(2, savedNpcs.size());
        verify(npcRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteNpc() {
        // When
        npcService.delete(mockNpc1);

        // Then
        verify(npcRepository, times(1)).delete(any(Npc.class));
    }

    @Test
    void shouldDeleteNpcById() {
        // When
        npcService.deleteById(MOCK_NPC_ID_1);

        // Then
        verify(npcRepository, times(1)).deleteById(anyString());
    }
}