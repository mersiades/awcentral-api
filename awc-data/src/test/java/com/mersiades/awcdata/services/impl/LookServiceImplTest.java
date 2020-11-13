package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.LookCategories;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Look;
import com.mersiades.awcdata.repositories.LookReactiveRepository;
import com.mersiades.awcdata.services.LookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LookServiceImplTest {

    public static final String MOCK_LOOK_ID_1 = "mock-look-id-1";

    @Mock
    LookReactiveRepository lookRepository;

    LookService lookService;

    Look mockLook1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockLook1 = new Look(MOCK_LOOK_ID_1, Playbooks.BATTLEBABE, LookCategories.CLOTHES, "Sexy leatherwear");

        lookService = new LookServiceImpl(lookRepository);
    }

    @Test
    void shouldFindAllLooks() {
        // Given
        Look mockLook2 = new Look(Playbooks.SKINNER, LookCategories.EYES, "askance");
        when(lookRepository.findAll()).thenReturn(Flux.just(mockLook1, mockLook2));

        // When
        List<Look> looks = lookService.findAll().collectList().block();

        // Then
        assert looks != null;
        assertEquals(2, looks.size());
        verify(lookRepository, times(1)).findAll();
    }

    @Test
    void shouldFindLookById() {
        // Given
        when(lookRepository.findById(anyString())).thenReturn(Mono.just(mockLook1));

        // When
        Look returnedLook = lookService.findById(MOCK_LOOK_ID_1).block();

        // Then
        assert returnedLook != null;
        assertEquals(MOCK_LOOK_ID_1, returnedLook.getId());
        verify(lookRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveLook() {
        // Given
        when(lookRepository.save(any())).thenReturn(Mono.just(mockLook1));

        // When
        Look savedLook = lookService.save(mockLook1).block();

        // Then
        assert savedLook != null;
        assertEquals(MOCK_LOOK_ID_1, savedLook.getId());
        verify(lookRepository, times(1)).save(any(Look.class));
    }

//    @Test
//    void shouldSaveAllLooks() {
//        // Given
//        Look mockLook2 = Look.builder().build();
//        when(lookRepository.saveAll(any())).thenReturn(Flux.just(mockLook1, mockLook2));
//
//        // When
//        List<Look> savedLooks = lookService.saveAll(Flux.just(mockLook1,mockLook2)).collectList().block();
//
//        // Then
//        assert savedLooks != null;
//        assertEquals(2, savedLooks.size());
//        verify(lookRepository, times(1)).saveAll(any());
//    }

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
        Look mockLook3 = new Look(Playbooks.BATTLEBABE, LookCategories.EYES, "almond");
        when(lookService.findAllByPlaybookType(any())).thenReturn(Flux.just(mockLook1, mockLook3));

        // When
        List<Look> returnedLooks = lookService.findAllByPlaybookType(Playbooks.BATTLEBABE).collectList().block();

        // Then
        assert returnedLooks != null;
        assertEquals(2, returnedLooks.size());
        verify(lookRepository, times(1)).findAllByPlaybookType(any(Playbooks.class));
    }
}