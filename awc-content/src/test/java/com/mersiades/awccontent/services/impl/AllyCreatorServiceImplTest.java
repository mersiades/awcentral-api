package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.AllyCreator;
import com.mersiades.awccontent.repositories.AllyCreatorRepository;
import com.mersiades.awccontent.services.AllyCreatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.mersiades.awccontent.content.AlliesCreatorContent.allyCreator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AllyCreatorServiceImplTest {
    
    @Mock
    AllyCreatorRepository allyCreatorRepository;
    
    AllyCreatorService allyCreatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        allyCreatorService = new AllyCreatorServiceImpl(allyCreatorRepository);
    }

    // There is only one AllyCreator
    @Test
    void shouldFindAllAllyCreators() {
        // Given
        when(allyCreatorRepository.findAll()).thenReturn(List.of(allyCreator));

        // When
        List<AllyCreator> returnedAllyCreators = allyCreatorService.findAll();

        // Then
        assert returnedAllyCreators != null;
        assertEquals(1, returnedAllyCreators.size());
        verify(allyCreatorRepository, times(1)).findAll();
    }

    @Test
    void shouldFindAllyCreatorById() {
        // Given
        when(allyCreatorRepository.findById(anyString())).thenReturn(Optional.of(allyCreator));

        // When
        AllyCreator returnedAllyCreator = allyCreatorService.findById(allyCreator.getId());

        // Then
        assert returnedAllyCreator != null;
        assertEquals(allyCreator.getId(), returnedAllyCreator.getId());
        verify(allyCreatorRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveAllyCreator() {
        // Given
        when(allyCreatorService.save(any(AllyCreator.class))).thenReturn(allyCreator);

        // When
        AllyCreator savedAllyCreator = allyCreatorService.save(allyCreator);

        // Then
        assert savedAllyCreator != null;
        assertEquals(allyCreator.getId(), savedAllyCreator.getId());
        verify(allyCreatorRepository, times(1)).save(any(AllyCreator.class));
    }

    // There is only one AllyCreator
    @Test
    void shouldSaveAllAllyCreators() {
        // Given
        when(allyCreatorRepository.saveAll(anyIterable())).thenReturn(List.of(allyCreator));

        // When
        List<AllyCreator> savedAllyCreators = allyCreatorService.saveAll(List.of(allyCreator));

        // Then
        assert savedAllyCreators != null;
        assertEquals(1, savedAllyCreators.size());
        verify(allyCreatorRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteAllyCreator() {

        // When
        allyCreatorService.delete(allyCreator);

        // Then
        verify(allyCreatorRepository, times(1)).delete(any(AllyCreator.class));
    }

    @Test
    void shouldDeleteAllyCreatorById() {
        // When
        allyCreatorService.deleteById(allyCreator.getId());

        // Then
        verify(allyCreatorRepository, times(1)).deleteById(anyString());
    }
}