package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.ThreatMapCreator;
import com.mersiades.awccontent.repositories.ThreatMapCreatorRepository;
import com.mersiades.awccontent.services.ThreatMapCreatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.mersiades.awccontent.content.AlliesCreatorContent.allyCreator;
import static com.mersiades.awccontent.content.ThreatMapCreatorContent.threatMapCreator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ThreatMapCreatorServiceImplTest {

    @Mock
    ThreatMapCreatorRepository threatMapCreatorRepository;

    ThreatMapCreatorService threatMapCreatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        threatMapCreatorService = new ThreatMapCreatorServiceImpl(threatMapCreatorRepository);
    }

    // There is only one ThreatMapCreator
    @Test
    void shouldFindAllThreatMapCreators() {
        // Given
        when(threatMapCreatorRepository.findAll()).thenReturn(List.of(threatMapCreator));

        // When
        List<ThreatMapCreator> returnedThreatMapCreators = threatMapCreatorService.findAll();

        // Then
        assert returnedThreatMapCreators != null;
        assertEquals(1, returnedThreatMapCreators.size());
        verify(threatMapCreatorRepository, times(1)).findAll();
    }

    @Test
    void shouldFindThreatMapCreatorById() {
        // Given
        when(threatMapCreatorRepository.findById(anyString())).thenReturn(Optional.of(threatMapCreator));

        // When
        ThreatMapCreator returnedThreatMapCreator = threatMapCreatorService.findById(allyCreator.getId());

        // Then
        assert returnedThreatMapCreator != null;
        assertEquals(threatMapCreator.getId(), returnedThreatMapCreator.getId());
        verify(threatMapCreatorRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveThreatMapCreator() {
        // Given
        when(threatMapCreatorService.save(any(ThreatMapCreator.class))).thenReturn(threatMapCreator);

        // When
        ThreatMapCreator savedThreatMapCreator = threatMapCreatorService.save(threatMapCreator);

        // Then
        assert savedThreatMapCreator != null;
        assertEquals(threatMapCreator.getId(), savedThreatMapCreator.getId());
        verify(threatMapCreatorRepository, times(1)).save(any(ThreatMapCreator.class));
    }

    // There is only one ThreatMapCreator
    @Test
    void shouldSaveAllThreatMapCreators() {
        // Given
        when(threatMapCreatorRepository.saveAll(anyIterable())).thenReturn(List.of(threatMapCreator));

        // When
        List<ThreatMapCreator> savedThreatMapCreators = threatMapCreatorService.saveAll(List.of(threatMapCreator));

        // Then
        assert savedThreatMapCreators != null;
        assertEquals(1, savedThreatMapCreators.size());
        verify(threatMapCreatorRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteThreatMapCreator() {

        // When
        threatMapCreatorService.delete(threatMapCreator);

        // Then
        verify(threatMapCreatorRepository, times(1)).delete(any(ThreatMapCreator.class));
    }

    @Test
    void shouldDeleteThreatMapCreatorById() {
        // When
        threatMapCreatorService.deleteById(threatMapCreator.getId());

        // Then
        verify(threatMapCreatorRepository, times(1)).deleteById(anyString());
    }
}