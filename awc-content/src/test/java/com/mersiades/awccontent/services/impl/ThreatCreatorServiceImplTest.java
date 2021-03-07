package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.ThreatCreator;
import com.mersiades.awccontent.repositories.ThreatCreatorRepository;
import com.mersiades.awccontent.services.ThreatCreatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ThreatCreatorServiceImplTest {

    public static final String MOCK_THREAT_CREATOR_ID_1 = "mock-threat-creator-id-1";

    @Mock
    ThreatCreatorRepository threatCreatorRepository;

    ThreatCreatorService threatCreatorService;

    ThreatCreator mockThreatCreator1;

    ThreatCreator mockThreatCreator2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        threatCreatorService = new ThreatCreatorServiceImpl(threatCreatorRepository);

        mockThreatCreator1 =  ThreatCreator.builder()
                .id(MOCK_THREAT_CREATOR_ID_1)
                .threatNames(List.of("Tum Tum", "Gnarly", "Fleece"))
                .createThreatInstructions("To create a threat:")
                .essentialThreatInstructions("Where the PCs are, create as landscape")
                .build();

        mockThreatCreator2 = ThreatCreator.builder().build();
    }

    @Test
    void shouldFindAllThreatCreators() {
        // Given
        when(threatCreatorRepository.findAll()).thenReturn(Flux.just(mockThreatCreator1, mockThreatCreator2));

        // When
        List<ThreatCreator> returnedThreatCreators = threatCreatorService.findAll().collectList().block();

        // Then
        assert returnedThreatCreators != null;
        assertEquals(2, returnedThreatCreators.size());
        verify(threatCreatorRepository, times(1)).findAll();
    }

    @Test
    void shouldFindThreatCreatorById() {
        // Given
        when(threatCreatorRepository.findById(anyString())).thenReturn(Mono.just(mockThreatCreator1));

        // When
        ThreatCreator returnedThreatCreator = threatCreatorService.findById(MOCK_THREAT_CREATOR_ID_1).block();

        // Then
        assert returnedThreatCreator != null;
        assertEquals(MOCK_THREAT_CREATOR_ID_1, returnedThreatCreator.getId());
        verify(threatCreatorRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveThreatCreator() {
        // Given
        when(threatCreatorService.save(any(ThreatCreator.class))).thenReturn(Mono.just(mockThreatCreator1));

        // When
        ThreatCreator savedThreatCreator = threatCreatorService.save(mockThreatCreator1).block();

        // Then
        assert savedThreatCreator != null;
        assertEquals(MOCK_THREAT_CREATOR_ID_1, savedThreatCreator.getId());
        verify(threatCreatorRepository, times(1)).save(any(ThreatCreator.class));
    }

    @Test
    void shouldSaveAllThreatCreators() {
        // Given
        when(threatCreatorRepository.saveAll(any(Publisher.class))).thenReturn(Flux.just(mockThreatCreator1, mockThreatCreator2));

        // When
        List<ThreatCreator> savedThreatCreators = threatCreatorService.saveAll(Flux.just(mockThreatCreator1, mockThreatCreator2)).collectList().block();

        // Then
        assert savedThreatCreators != null;
        assertEquals(2, savedThreatCreators.size());
        verify(threatCreatorRepository, times(1)).saveAll(any(Publisher.class));
    }

    @Test
    void shouldDeleteThreatCreator() {
        // When
        threatCreatorService.delete(mockThreatCreator1);

        // Then
        verify(threatCreatorRepository, times(1)).delete(any(ThreatCreator.class));
    }

    @Test
    void shouldDeleteThreatCreatorById() {
        // When
        threatCreatorService.deleteById(MOCK_THREAT_CREATOR_ID_1);

        // Then
        verify(threatCreatorRepository, times(1)).deleteById(anyString());
    }
}