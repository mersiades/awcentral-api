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

import static com.mersiades.awccontent.content.McContentContent.mcContent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class McContentServiceImplTest {

    @Mock
    McContentRepository mcContentRepository;

    McContentService mcContentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mcContentService = new McContentServiceImpl(mcContentRepository) {
        };
    }

    // There is only one McContent
    @Test
    void shouldFindAllMcContents() {
        // Given
        when(mcContentRepository.findAll()).thenReturn(List.of(mcContent));

        // When
        List<McContent> looks = mcContentService.findAll();

        // Then
        assert looks != null;
        assertEquals(1, looks.size());
        verify(mcContentRepository, times(1)).findAll();
    }

    @Test
    void shouldFindMcContentById() {
        // Given
        when(mcContentRepository.findById(anyString())).thenReturn(Optional.of(mcContent));

        // When
        McContent returnedMcContent = mcContentService.findById(mcContent.getId());

        // Then
        assert returnedMcContent != null;
        assertEquals(mcContent.getId(), returnedMcContent.getId());
        verify(mcContentRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveMcContent() {
        // Given
        when(mcContentRepository.save(any())).thenReturn(mcContent);

        // When
        McContent savedMcContent = mcContentService.save(mcContent);

        // Then
        assert savedMcContent != null;
        assertEquals(mcContent.getId(), savedMcContent.getId());
        verify(mcContentRepository, times(1)).save(any(McContent.class));
    }

    // There is only one McContent
    @Test
    void shouldSaveAllMcContents() {
        // Given
        when(mcContentRepository.saveAll(anyIterable())).thenReturn(List.of(mcContent));

        // When
        List<McContent> savedMcContents = mcContentService.saveAll(List.of(mcContent));

        // Then
        assert savedMcContents != null;
        assertEquals(1, savedMcContents.size());
        verify(mcContentRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteMcContent() {
        // When
        mcContentService.delete(mcContent);

        // Then
        verify(mcContentRepository, times(1)).delete(any(McContent.class));
    }

    @Test
    void shouldDeleteMcContentById() {
        // When
        mcContentService.deleteById(mcContent.getId());

        // Then
        verify(mcContentRepository, times(1)).deleteById(anyString());
    }
}