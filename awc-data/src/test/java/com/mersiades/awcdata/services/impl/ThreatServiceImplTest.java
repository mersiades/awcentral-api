package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.Threat;
import com.mersiades.awcdata.repositories.ThreatRepository;
import com.mersiades.awcdata.services.ThreatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ThreatServiceImplTest {

    public static final String MOCK_THREAT_ID_1 = "mock-threat-id-1";

    @Mock
    ThreatRepository threatRepository;

    ThreatService threatService;

    Threat mockThreat1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockThreat1 = Threat.builder()
                .id(MOCK_THREAT_ID_1)
                .build();

        threatService = new ThreatServiceImpl(threatRepository);
    }

    @Test
    void shouldFindAllThreats() {
        // Given
        Threat mockThreat2 = Threat.builder().build();
        when(threatRepository.findAll()).thenReturn(List.of(mockThreat1, mockThreat2));

        // When
        List<Threat> returnedThreats = threatService.findAll();

        // Then
        assert returnedThreats != null;
        assertEquals(2, returnedThreats.size());
        verify(threatRepository, times(1)).findAll();
    }

    @Test
    void shouldFindThreatById() {
        // Given
        when(threatRepository.findById(anyString())).thenReturn(Optional.of(mockThreat1));

        // When
        Threat returnedThreat = threatService.findById(MOCK_THREAT_ID_1);

        // Then
        assert returnedThreat != null;
        assertEquals(MOCK_THREAT_ID_1, returnedThreat.getId());
        verify(threatRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveThreat() {
        // Given
        when(threatRepository.save(any(Threat.class))).thenReturn(mockThreat1);

        // When
        Threat savedThreat = threatService.save(mockThreat1);

        // Then
        assert savedThreat != null;
        assertEquals(mockThreat1.getId(), savedThreat.getId());
        verify(threatRepository, times(1)).save(any(Threat.class));
    }

    @Test
    void shouldSaveAllThreats() {
        // Given
        Threat mockThreat2 = Threat.builder().build();
        when(threatRepository.saveAll(anyIterable())).thenReturn(List.of(mockThreat1, mockThreat2));

        // When
        List<Threat> savedThreats = threatService.saveAll(List.of(mockThreat1,mockThreat2));

        // Then
        assert savedThreats != null;
        assertEquals(2, savedThreats.size());
        verify(threatRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteThreat() {
        // When
        threatService.delete(mockThreat1);

        // Then
        verify(threatRepository, times(1)).delete(any(Threat.class));
    }

    @Test
    void shouldDeleteThreatById() {
        // When
        threatService.deleteById(MOCK_THREAT_ID_1);

        // Then
        verify(threatRepository, times(1)).deleteById(anyString());
    }
}