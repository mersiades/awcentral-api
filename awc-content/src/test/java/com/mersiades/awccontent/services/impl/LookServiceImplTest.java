package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.LookType;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LookServiceImplTest {

    public static final String MOCK_LOOK_ID_1 = "mock-look-id-1";

    @Mock
    LookRepository lookRepository;

    LookService lookService;

    Look mockLook1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockLook1 = Look.builder()
                .id(MOCK_LOOK_ID_1)
                .category(LookType.CLOTHES)
                .playbookType(PlaybookType.BATTLEBABE)
                .look("Sexy leatherwear")
                .build();

        lookService = new LookServiceImpl(lookRepository);
    }

    @Test
    void shouldFindAllLooks() {
        // Given
        Look mockLook2 = Look.builder()
                .playbookType(PlaybookType.SKINNER)
                .category(LookType.EYES)
                .look("askance").build();

        when(lookRepository.findAll()).thenReturn(List.of(mockLook1, mockLook2));

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
        when(lookRepository.findById(anyString())).thenReturn(Optional.of(mockLook1));

        // When
        Look returnedLook = lookService.findById(MOCK_LOOK_ID_1);

        // Then
        assert returnedLook != null;
        assertEquals(MOCK_LOOK_ID_1, returnedLook.getId());
        verify(lookRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveLook() {
        // Given
        when(lookRepository.save(any())).thenReturn(mockLook1);

        // When
        Look savedLook = lookService.save(mockLook1);

        // Then
        assert savedLook != null;
        assertEquals(MOCK_LOOK_ID_1, savedLook.getId());
        verify(lookRepository, times(1)).save(any(Look.class));
    }

    @Test
    void shouldSaveAllLooks() {
        // Given
        Look mockLook2 = Look.builder().build();
        when(lookRepository.saveAll(anyIterable())).thenReturn(List.of(mockLook1, mockLook2));

        // When
        List<Look> savedLooks = lookService.saveAll(List.of(mockLook1,mockLook2));

        // Then
        assert savedLooks != null;
        assertEquals(2, savedLooks.size());
        verify(lookRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteLook() {
        // When
        lookService.delete(mockLook1);

        // Then
        verify(lookRepository, times(1)).delete(any(Look.class));
    }

    @Test
    void deleteById() {
        // When
        lookService.deleteById(MOCK_LOOK_ID_1);

        // Then
        verify(lookRepository, times(1)).deleteById(anyString());
    }

    @Test
    void findAllByPlaybookType() {
        // Given
        Look mockLook3 = Look.builder()
                .playbookType(PlaybookType.BATTLEBABE)
                .category(LookType.EYES)
                .look("almond").build();
        when(lookService.findAllByPlaybookType(any())).thenReturn(List.of(mockLook1, mockLook3));

        // When
        List<Look> returnedLooks = lookService.findAllByPlaybookType(PlaybookType.BATTLEBABE);

        // Then
        assert returnedLooks != null;
        assertEquals(2, returnedLooks.size());
        verify(lookRepository, times(1)).findAllByPlaybookType(any(PlaybookType.class));
    }
}