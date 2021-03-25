package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.models.McContent;
import com.mersiades.awccontent.repositories.McContentRepository;
import com.mersiades.awccontent.services.McContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class McContentServiceImplTest {

    public static final String MOCK_MC_CONTENT_ID_1 = "mock-mc-content-id-1";

    @Mock
    McContentRepository mcContentRepository;

    McContentService mcContentService;

    McContent mcContent1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mcContent1 = McContent.builder()
                .id(MOCK_MC_CONTENT_ID_1).build();

        mcContentService = new McContentServiceImpl(mcContentRepository) {
        };
    }

    @Test
    void shouldFindAllMcContents() {
        // Given
        McContent mcContent2 = McContent.builder()
                .id("mock-mc-content-id-2").build();

        when(mcContentRepository.findAll()).thenReturn(List.of(mcContent1, mcContent2));

        // When
        List<McContent> looks = mcContentService.findAll();

        // Then
        assert looks != null;
        assertEquals(2, looks.size());
        verify(mcContentRepository, times(1)).findAll();
    }

    @Test
    void shouldFindMcContentById() {
        // Given
        when(mcContentRepository.findById(anyString())).thenReturn(Optional.of(mcContent1));

        // When
        McContent returnedMcContent = mcContentService.findById(MOCK_MC_CONTENT_ID_1);

        // Then
        assert returnedMcContent != null;
        assertEquals(MOCK_MC_CONTENT_ID_1, returnedMcContent.getId());
        verify(mcContentRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveMcContent() {
        // Given
        when(mcContentRepository.save(any())).thenReturn(mcContent1);

        // When
        McContent savedMcContent = mcContentService.save(mcContent1);

        // Then
        assert savedMcContent != null;
        assertEquals(MOCK_MC_CONTENT_ID_1, savedMcContent.getId());
        verify(mcContentRepository, times(1)).save(any(McContent.class));
    }

    @Test
    void shouldSaveAllMcContents() {
        // Given
        McContent mcContent2 = McContent.builder().build();
        when(mcContentRepository.saveAll(anyIterable())).thenReturn(List.of(mcContent1, mcContent2));

        // When
        List<McContent> savedMcContents = mcContentService.saveAll(List.of(mcContent1,mcContent2));

        // Then
        assert savedMcContents != null;
        assertEquals(2, savedMcContents.size());
        verify(mcContentRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteMcContent() {
        // When
        mcContentService.delete(mcContent1);

        // Then
        verify(mcContentRepository, times(1)).delete(any(McContent.class));
    }

    @Test
    void shouldDeleteMcContentById() {
        // When
        mcContentService.deleteById(MOCK_MC_CONTENT_ID_1);

        // Then
        verify(mcContentRepository, times(1)).deleteById(anyString());
    }
}