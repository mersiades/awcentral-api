package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.ThreatCreator;
import com.mersiades.awccontent.repositories.ThreatCreatorRepository;
import com.mersiades.awccontent.services.ThreatCreatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.mersiades.awccontent.content.ThreatsCreatorContent.threatCreator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ThreatCreatorServiceImplTest {

    @Mock
    ThreatCreatorRepository threatCreatorRepository;

    ThreatCreatorService threatCreatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        threatCreatorService = new ThreatCreatorServiceImpl(threatCreatorRepository);
    }

    // There is only one ThreatCreator
    @Test
    void shouldFindAllThreatCreators() {
        // Given
        when(threatCreatorRepository.findAll()).thenReturn(List.of(threatCreator));

        // When
        List<ThreatCreator> returnedThreatCreators = threatCreatorService.findAll();

        // Then
        assert returnedThreatCreators != null;
        assertEquals(1, returnedThreatCreators.size());
        verify(threatCreatorRepository, times(1)).findAll();
    }

    @Test
    void shouldFindThreatCreatorById() {
        // Given
        when(threatCreatorRepository.findById(anyString())).thenReturn(Optional.of(threatCreator));

        // When
        ThreatCreator returnedThreatCreator = threatCreatorService.findById(threatCreator.getId());

        // Then
        assert returnedThreatCreator != null;
        assertEquals(threatCreator.getId(), returnedThreatCreator.getId());
        verify(threatCreatorRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveThreatCreator() {
        // Given
        when(threatCreatorService.save(any(ThreatCreator.class))).thenReturn(threatCreator);

        // When
        ThreatCreator savedThreatCreator = threatCreatorService.save(threatCreator);

        // Then
        assert savedThreatCreator != null;
        assertEquals(threatCreator.getId(), savedThreatCreator.getId());
        verify(threatCreatorRepository, times(1)).save(any(ThreatCreator.class));
    }

    // There is only one ThreatCreator
    @Test
    void shouldSaveAllThreatCreators() {
        // Given
        when(threatCreatorRepository.saveAll(anyIterable())).thenReturn(List.of(threatCreator));

        // When
        List<ThreatCreator> savedThreatCreators = threatCreatorService.saveAll(List.of(threatCreator));

        // Then
        assert savedThreatCreators != null;
        assertEquals(1, savedThreatCreators.size());
        verify(threatCreatorRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteThreatCreator() {
        // When
        threatCreatorService.delete(threatCreator);

        // Then
        verify(threatCreatorRepository, times(1)).delete(any(ThreatCreator.class));
    }

    @Test
    void shouldDeleteThreatCreatorById() {
        // When
        threatCreatorService.deleteById(threatCreator.getId());

        // Then
        verify(threatCreatorRepository, times(1)).deleteById(anyString());
    }
}