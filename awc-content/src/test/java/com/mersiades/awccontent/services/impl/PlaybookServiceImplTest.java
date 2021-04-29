package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Playbook;
import com.mersiades.awccontent.repositories.PlaybookRepository;
import com.mersiades.awccontent.services.PlaybookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.mersiades.awccontent.content.PlaybooksContent.angel;
import static com.mersiades.awccontent.content.PlaybooksContent.battlebabe;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlaybookServiceImplTest {

    @Mock
    PlaybookRepository playbookRepository;

    PlaybookService playbookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        playbookService = new PlaybookServiceImpl(playbookRepository);
    }

    @Test
    void shouldFindAllPlaybooks() {
        // Given
        when(playbookRepository.findAll()).thenReturn(List.of(angel, battlebabe));

        // When
        List<Playbook> returnedPlaybooks = playbookService.findAll();

        // Then
        assert returnedPlaybooks != null;
        assertEquals(2, returnedPlaybooks.size());
        verify(playbookRepository, times(1)).findAll();
    }

    @Test
    void shouldFindPlaybookById() {
        // Given
        when(playbookRepository.findById(anyString())).thenReturn(Optional.of(angel));

        // When
        Playbook returnedPlaybook = playbookService.findById(angel.getId());

        // Then
        assert returnedPlaybook != null;
        assertEquals(angel.getId(), returnedPlaybook.getId());
        verify(playbookRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSavePlaybook() {
        // Given
        when(playbookRepository.save(any(Playbook.class))).thenReturn(angel);

        // When
        Playbook savedPlaybook = playbookService.save(angel);

        // Then
        assert savedPlaybook != null;
        assertEquals(angel.getId(), savedPlaybook.getId());
        verify(playbookRepository, times(1)).save(any(Playbook.class));
    }

    @Test
    void shouldSaveAllPlaybooks() {
        // Given
        when(playbookRepository.saveAll(anyIterable())).thenReturn(List.of(angel, battlebabe));

        // When
        List<Playbook> savedPlaybooks = playbookService.saveAll(List.of(angel,battlebabe));

        // Then
        assert savedPlaybooks != null;
        assertEquals(2, savedPlaybooks.size());
        verify(playbookRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeletePlaybook() {
        // When
        playbookService.delete(angel);

        // Then
        verify(playbookRepository, times(1)).delete(any(Playbook.class));
    }

    @Test
    void shouldDeletePlaybookById() {
        // When
        playbookService.deleteById(angel.getId());

        // Then
        verify(playbookRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldFindPlaybookByPlaybookType() {
        // Given
        when(playbookRepository.findByPlaybookType(any(PlaybookType.class))).thenReturn(angel);

        // When
        Playbook returnedPlaybook = playbookService.findByPlaybookType(PlaybookType.ANGEL);

        // Then
        assert returnedPlaybook != null;
        assertEquals(returnedPlaybook.getPlaybookType(), PlaybookType.ANGEL);
        verify(playbookRepository, times(1)).findByPlaybookType(any(PlaybookType.class));
    }
}