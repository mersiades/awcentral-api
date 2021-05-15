package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.Ally;
import com.mersiades.awcdata.repositories.AllyRepository;
import com.mersiades.awcdata.services.AllyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AllyServiceImplTest {

    public static final String MOCK_ALLY_ID_1 = "mock-ally-id-1";

    @Mock
    AllyRepository allyRepository;

    AllyService allyService;

    Ally mockAlly1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockAlly1 = Ally.builder()
                .id(MOCK_ALLY_ID_1)
                .build();

        allyService = new AllyServiceImpl(allyRepository);
    }

    @Test
    void shouldFindAllAllies() {
        // Given
        Ally mockAlly2 = Ally.builder().build();
        when(allyRepository.findAll()).thenReturn(List.of(mockAlly1, mockAlly2));

        // When
        List<Ally> returnedAllies = allyService.findAll();

        // Then
        assert returnedAllies != null;
        assertEquals(2, returnedAllies.size());
        verify(allyRepository, times(1)).findAll();
    }

    @Test
    void shouldFindAllyById() {
        // Given
        when(allyRepository.findById(anyString())).thenReturn(Optional.of(mockAlly1));

        // When
        Ally returnedAlly = allyService.findById(MOCK_ALLY_ID_1);

        // Then
        assert returnedAlly != null;
        assertEquals(MOCK_ALLY_ID_1, returnedAlly.getId());
        verify(allyRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveAlly() {
        // Given
        when(allyRepository.save(any(Ally.class))).thenReturn(mockAlly1);

        // When
        Ally savedAlly = allyService.save(mockAlly1);

        // Then
        assert savedAlly != null;
        assertEquals(mockAlly1.getId(), savedAlly.getId());
        verify(allyRepository, times(1)).save(any(Ally.class));
    }

    @Test
    void shouldSaveAllThreats() {
        // Given
        Ally mockAlly2 = Ally.builder().build();
        when(allyRepository.saveAll(anyIterable())).thenReturn(List.of(mockAlly1, mockAlly2));

        // When
        List<Ally> savedThreats = allyService.saveAll(List.of(mockAlly1,mockAlly2));

        // Then
        assert savedThreats != null;
        assertEquals(2, savedThreats.size());
        verify(allyRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteAlly() {
        // When
        allyService.delete(mockAlly1);

        // Then
        verify(allyRepository, times(1)).delete(any(Ally.class));
    }

    @Test
    void shouldDeleteThreatById() {
        // When
        allyService.deleteById(MOCK_ALLY_ID_1);

        // Then
        verify(allyRepository, times(1)).deleteById(anyString());
    }
}