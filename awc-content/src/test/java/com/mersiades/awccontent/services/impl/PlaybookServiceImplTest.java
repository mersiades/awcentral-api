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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlaybookServiceImplTest {

    public static final String MOCK_PLAYBOOK_ID_1 = "mock-playbook-id-1";

    @Mock
    PlaybookRepository playbookRepository;

    PlaybookService playbookService;

    Playbook mockPlaybook1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockPlaybook1 = Playbook.builder()
                .id(MOCK_PLAYBOOK_ID_1)
                .playbookType(PlaybookType.ANGEL)
                .build();

        playbookService = new PlaybookServiceImpl(playbookRepository);
    }

    @Test
    void shouldFindAllPlaybooks() {
        // Given
        Playbook mockPlaybook2 = Playbook.builder().build();
        when(playbookRepository.findAll()).thenReturn(List.of(mockPlaybook1, mockPlaybook2));

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
        when(playbookRepository.findById(anyString())).thenReturn(Optional.of(mockPlaybook1));

        // When
        Playbook returnedPlaybook = playbookService.findById(MOCK_PLAYBOOK_ID_1);

        // Then
        assert returnedPlaybook != null;
        assertEquals(MOCK_PLAYBOOK_ID_1, returnedPlaybook.getId());
        verify(playbookRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSavePlaybook() {
        // Given
        when(playbookRepository.save(any(Playbook.class))).thenReturn(mockPlaybook1);

        // When
        Playbook savedPlaybook = playbookService.save(mockPlaybook1);

        // Then
        assert savedPlaybook != null;
        assertEquals(mockPlaybook1.getId(), savedPlaybook.getId());
        verify(playbookRepository, times(1)).save(any(Playbook.class));
    }

    @Test
    void shouldSaveAllPlaybooks() {
        // Given
        Playbook mockPlaybook2 = Playbook.builder().build();
        when(playbookRepository.saveAll(anyIterable())).thenReturn(List.of(mockPlaybook1, mockPlaybook2));

        // When
        List<Playbook> savedPlaybooks = playbookService.saveAll(List.of(mockPlaybook1,mockPlaybook2));

        // Then
        assert savedPlaybooks != null;
        assertEquals(2, savedPlaybooks.size());
        verify(playbookRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeletePlaybook() {
        // When
        playbookService.delete(mockPlaybook1);

        // Then
        verify(playbookRepository, times(1)).delete(any(Playbook.class));
    }

    @Test
    void shouldDeletePlaybookById() {
        // When
        playbookService.deleteById(MOCK_PLAYBOOK_ID_1);

        // Then
        verify(playbookRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldFindPlaybookByPlaybookType() {
        // Given
        when(playbookRepository.findByPlaybookType(any(PlaybookType.class))).thenReturn(mockPlaybook1);

        // When
        Playbook returnedPlaybook = playbookService.findByPlaybookType(PlaybookType.ANGEL);

        // Then
        assert returnedPlaybook != null;
        assertEquals(returnedPlaybook.getPlaybookType(), PlaybookType.ANGEL);
        verify(playbookRepository, times(1)).findByPlaybookType(any(PlaybookType.class));
    }
}