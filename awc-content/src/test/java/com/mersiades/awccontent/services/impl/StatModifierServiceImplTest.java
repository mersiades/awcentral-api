package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.StatType;
import com.mersiades.awccontent.models.StatModifier;
import com.mersiades.awccontent.repositories.StatModifierRepository;
import com.mersiades.awccontent.services.StatModifierService;
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

class StatModifierServiceImplTest {

    public static final String MOCK_STAT_MODIFIER_ID_1 = "mock-stat-modifier-id-1";

    @Mock
    StatModifierRepository statModifierRepository;

    StatModifierService statModifierService;

    StatModifier mockStatModifier1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        statModifierService = new StatModifierServiceImpl(statModifierRepository);

        mockStatModifier1 = StatModifier.builder()
                .id(MOCK_STAT_MODIFIER_ID_1)
                .statToModify(StatType.COOL)
                .modification(1)
                .build();

    }

    @Test
    void shouldFindAllStatModifiers() {
        // Given
        StatModifier mockStatModifier2 = StatModifier.builder().build();
        when(statModifierRepository.findAll()).thenReturn(Flux.just(mockStatModifier1, mockStatModifier2));

        // When
        List<StatModifier> returnedStatModifiers = statModifierService.findAll().collectList().block();

        // Then
        assert returnedStatModifiers != null;
        assertEquals(2, returnedStatModifiers.size());
        verify(statModifierRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        // Given
        when(statModifierRepository.findById(anyString())).thenReturn(Mono.just(mockStatModifier1));

        // When
        StatModifier returnedStatModifier = statModifierService.findById(MOCK_STAT_MODIFIER_ID_1).block();

        // Then
        assert returnedStatModifier != null;
        assertEquals(MOCK_STAT_MODIFIER_ID_1, returnedStatModifier.getId());
        verify(statModifierRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveStatModifier() {
        // Given
        when(statModifierRepository.save(any(StatModifier.class))).thenReturn(Mono.just(mockStatModifier1));

        // When
        StatModifier savedStatModifier = statModifierService.save(mockStatModifier1).block();

        // Then
        assert savedStatModifier != null;
        assertEquals(MOCK_STAT_MODIFIER_ID_1, savedStatModifier.getId());
        verify(statModifierRepository, times(1)).save(any(StatModifier.class));
    }

    @Test
    void shouldSaveAllStatModifiers() {
        // Given
        StatModifier mockStatModifier2 = StatModifier.builder().build();
        when(statModifierRepository.saveAll(any(Publisher.class))).thenReturn(Flux.just(mockStatModifier1, mockStatModifier2));

        // When
        List<StatModifier> savedStatModifiers = statModifierService.saveAll(Flux.just(mockStatModifier1, mockStatModifier2)).collectList().block();

        // Then
        assert savedStatModifiers != null;
        assertEquals(2, savedStatModifiers.size());
        verify(statModifierRepository, times(1)).saveAll(any(Publisher.class));
    }

    @Test
    void shouldDeleteStatModifier() {
        // When
        statModifierService.delete(mockStatModifier1);

        // Then
        verify(statModifierRepository, times(1)).delete(any(StatModifier.class));
    }

    @Test
    void deleteById() {
        // When
        statModifierService.deleteById(MOCK_STAT_MODIFIER_ID_1);

        // Then
        verify(statModifierRepository, times(1)).deleteById(anyString());
    }
}