package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.Playbooks;
import com.mersiades.awccontent.models.Playbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.mersiades.awccontent.repositories.PlaybookRepository;
import com.mersiades.awccontent.services.impl.PlaybookServiceImpl;

import java.util.List;

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
                .playbookType(Playbooks.ANGEL)
                .build();

        playbookService = new PlaybookServiceImpl(playbookRepository);
    }

    @Test
    void shouldFindAllPlaybooks() {
        // Given
        Playbook mockPlaybook2 = Playbook.builder().build();
        when(playbookRepository.findAll()).thenReturn(Flux.just(mockPlaybook1, mockPlaybook2));

        // When
        List<Playbook> returnedPlaybooks = playbookService.findAll().collectList().block();

        // Then
        assert returnedPlaybooks != null;
        assertEquals(2, returnedPlaybooks.size());
        verify(playbookRepository, times(1)).findAll();
    }

    @Test
    void shouldFindPlaybookById() {
        // Given
        when(playbookRepository.findById(anyString())).thenReturn(Mono.just(mockPlaybook1));

        // When
        Playbook returnedPlaybook = playbookService.findById(MOCK_PLAYBOOK_ID_1).block();

        // Then
        assert returnedPlaybook != null;
        assertEquals(MOCK_PLAYBOOK_ID_1, returnedPlaybook.getId());
        verify(playbookRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSavePlaybook() {
        // Given
        when(playbookRepository.save(any(Playbook.class))).thenReturn(Mono.just(mockPlaybook1));

        // When
        Playbook savedPlaybook = playbookService.save(mockPlaybook1).block();

        // Then
        assert savedPlaybook != null;
        assertEquals(mockPlaybook1.getId(), savedPlaybook.getId());
        verify(playbookRepository, times(1)).save(any(Playbook.class));
    }

    @Test
    void shouldSaveAllPlaybooks() {
        // Given
        Playbook mockPlaybook2 = Playbook.builder().build();
        when(playbookRepository.saveAll(any(Publisher.class))).thenReturn(Flux.just(mockPlaybook1, mockPlaybook2));

        // When
        List<Playbook> savedPlaybooks = playbookService.saveAll(Flux.just(mockPlaybook1,mockPlaybook2)).collectList().block();

        // Then
        assert savedPlaybooks != null;
        assertEquals(2, savedPlaybooks.size());
        verify(playbookRepository, times(1)).saveAll(any(Publisher.class));
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
        when(playbookRepository.findByPlaybookType(any(Playbooks.class))).thenReturn(Mono.just(mockPlaybook1));

        // When
        Playbook returnedPlaybook = playbookService.findByPlaybookType(Playbooks.ANGEL).block();

        // Then
        assert returnedPlaybook != null;
        assertEquals(returnedPlaybook.getPlaybookType(), Playbooks.ANGEL);
        verify(playbookRepository, times(1)).findByPlaybookType(any(Playbooks.class));
    }
}