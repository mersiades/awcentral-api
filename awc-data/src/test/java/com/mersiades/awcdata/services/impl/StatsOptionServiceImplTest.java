package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.StatsOption;
import com.mersiades.awcdata.repositories.StatsOptionRepository;
import com.mersiades.awcdata.services.StatsOptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                .playbookType(Playbooks.ANGEL)
                .build();

        mockSo2 = StatsOption.builder()
                .id("mock-stats-option-id-2")
                .playbookType(Playbooks.ANGEL)
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

//    @Test
//    void shouldSaveAllStatsOptions() {
//    }

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
        when(soRepository.findAllByPlaybookType(any(Playbooks.class))).thenReturn(Flux.just(mockSo1, mockSo2));

        // When
        List<StatsOption> returnedStatsOptions = soService.findAllByPlaybookType(Playbooks.ANGEL).collectList().block();

        // Then
        assert returnedStatsOptions != null;
        for (StatsOption statsOption : returnedStatsOptions) {
            assertEquals(Playbooks.ANGEL, statsOption.getPlaybookType());
        }
        verify(soRepository, times(1)).findAllByPlaybookType(any(Playbooks.class));
    }
}