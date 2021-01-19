package com.mersiades.awccontent.services;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.StatsOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.mersiades.awccontent.repositories.StatsOptionRepository;
import com.mersiades.awccontent.services.impl.StatsOptionServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StatsOptionServiceImplTest {

    public static final String MOCK_STATS_OPTION_ID_1 = "mock-stats-option-id-1";

    @Mock
    StatsOptionRepository soRepository;

    StatsOptionService soService;

    StatsOption mockSo1;

    StatsOption mockSo2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockSo1 = StatsOption.builder()
                .id(MOCK_STATS_OPTION_ID_1)
                .playbookType(PlaybookType.ANGEL)
                .build();

        mockSo2 = StatsOption.builder()
                .id("mock-stats-option-id-2")
                .playbookType(PlaybookType.ANGEL)
                .build();

        soService = new StatsOptionServiceImpl(soRepository);
    }

    @Test
    void shouldFindAllStatsOptions() {
        // Given
        when(soRepository.findAll()).thenReturn(Flux.just(mockSo1, mockSo2));

        // When
        List<StatsOption> returnedStatsOptions = soService.findAll().collectList().block();

        // Then
        assert returnedStatsOptions != null;
        assertEquals(2, returnedStatsOptions.size());
        verify(soRepository, times(1)).findAll();
    }

    @Test
    void shouldFindStatsOptionById() {
        // Given
        when(soRepository.findById(anyString())).thenReturn(Mono.just(mockSo1));

        // When
        StatsOption returnedStatsOption = soService.findById(MOCK_STATS_OPTION_ID_1).block();

        // Then
        assert returnedStatsOption != null;
        assertEquals(MOCK_STATS_OPTION_ID_1, returnedStatsOption.getId());
        verify(soRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveStatsOption() {
        // Given
        when(soRepository.save(any(StatsOption.class))).thenReturn(Mono.just(mockSo1));

        // When
        StatsOption savedStatsOption = soService.save(mockSo1).block();

        // Then
        assert savedStatsOption != null;
        assertEquals(mockSo1.getId(), savedStatsOption.getId());
        verify(soRepository, times(1)).save(any(StatsOption.class));
    }

    @Test
    void shouldSaveAllStatsOptions() {
        // Given
        StatsOption mockSo2 = StatsOption.builder().build();
        when(soRepository.saveAll(any(Publisher.class))).thenReturn(Flux.just(mockSo1, mockSo2));

        // When
        List<StatsOption> savedStatsOptions = soService.saveAll(Flux.just(mockSo1,mockSo2)).collectList().block();

        // Then
        assert savedStatsOptions != null;
        assertEquals(2, savedStatsOptions.size());
        verify(soRepository, times(1)).saveAll(any(Publisher.class));
    }

    @Test
    void shouldDeleteStatsOption() {
        // When
        soService.delete(mockSo1);

        // Then
        verify(soRepository, times(1)).delete(any(StatsOption.class));
    }

    @Test
    void shouldDeleteStatsOptionById() {
        // When
        soService.deleteById(MOCK_STATS_OPTION_ID_1);

        // Then
        verify(soRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldFindAllStatsOptionsByPlaybookType() {
        // Given
        when(soRepository.findAllByPlaybookType(any(PlaybookType.class))).thenReturn(Flux.just(mockSo1, mockSo2));

        // When
        List<StatsOption> returnedStatsOptions = soService.findAllByPlaybookType(PlaybookType.ANGEL).collectList().block();

        // Then
        assert returnedStatsOptions != null;
        for (StatsOption statsOption : returnedStatsOptions) {
            assertEquals(PlaybookType.ANGEL, statsOption.getPlaybookType());
        }
        verify(soRepository, times(1)).findAllByPlaybookType(any(PlaybookType.class));
    }
}