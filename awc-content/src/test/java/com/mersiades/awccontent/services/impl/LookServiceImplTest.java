package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Look;
import com.mersiades.awccontent.repositories.LookRepository;
import com.mersiades.awccontent.services.LookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.mersiades.awccontent.content.LooksContent.lookAngel1;
import static com.mersiades.awccontent.content.LooksContent.lookBattlebabe1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LookServiceImplTest {

    @Mock
    LookRepository lookRepository;

    LookService lookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        lookService = new LookServiceImpl(lookRepository);
    }

    @Test
    void shouldFindAllLooks() {
        // Given
        when(lookRepository.findAll()).thenReturn(List.of(lookAngel1, lookBattlebabe1));

        // When
        List<Look> looks = lookService.findAll();

        // Then
        assert looks != null;
        assertEquals(2, looks.size());
        verify(lookRepository, times(1)).findAll();
    }

    @Test
    void shouldFindLookById() {
        // Given
        when(lookRepository.findById(anyString())).thenReturn(Optional.of(lookAngel1));

        // When
        Look returnedLook = lookService.findById(lookAngel1.getId());

        // Then
        assert returnedLook != null;
        assertEquals(lookAngel1.getId(), returnedLook.getId());
        verify(lookRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveLook() {
        // Given
        when(lookRepository.save(any())).thenReturn(lookAngel1);

        // When
        Look savedLook = lookService.save(lookAngel1);

        // Then
        assert savedLook != null;
        assertEquals(lookAngel1.getId(), savedLook.getId());
        verify(lookRepository, times(1)).save(any(Look.class));
    }

    @Test
    void shouldSaveAllLooks() {
        // Given
        when(lookRepository.saveAll(anyIterable())).thenReturn(List.of(lookAngel1, lookBattlebabe1));

        // When
        List<Look> savedLooks = lookService.saveAll(List.of(lookAngel1,lookBattlebabe1));

        // Then
        assert savedLooks != null;
        assertEquals(2, savedLooks.size());
        verify(lookRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteLook() {
        // When
        lookService.delete(lookBattlebabe1);

        // Then
        verify(lookRepository, times(1)).delete(any(Look.class));
    }

    @Test
    void deleteById() {
        // When
        lookService.deleteById(lookBattlebabe1.getId());

        // Then
        verify(lookRepository, times(1)).deleteById(anyString());
    }

    @Test
    void findAllByPlaybookType() {
        // Given
        when(lookService.findAllByPlaybookType(any())).thenReturn(List.of(lookBattlebabe1, lookBattlebabe1));

        // When
        List<Look> returnedLooks = lookService.findAllByPlaybookType(PlaybookType.BATTLEBABE);

        // Then
        assert returnedLooks != null;
        assertEquals(2, returnedLooks.size());
        verify(lookRepository, times(1)).findAllByPlaybookType(any(PlaybookType.class));
    }
}