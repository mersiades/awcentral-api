package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.StatsOption;
import com.mersiades.awccontent.repositories.StatsOptionRepository;
import com.mersiades.awccontent.services.StatsOptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

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
        when(soRepository.findAll()).thenReturn(List.of(mockSo1, mockSo2));

        // When
        List<StatsOption> returnedStatsOptions = soService.findAll();

        // Then
        assert returnedStatsOptions != null;
        assertEquals(2, returnedStatsOptions.size());
        verify(soRepository, times(1)).findAll();
    }

    @Test
    void shouldFindStatsOptionById() {
        // Given
        when(soRepository.findById(anyString())).thenReturn(Optional.of(mockSo1));

        // When
        StatsOption returnedStatsOption = soService.findById(MOCK_STATS_OPTION_ID_1);

        // Then
        assert returnedStatsOption != null;
        assertEquals(MOCK_STATS_OPTION_ID_1, returnedStatsOption.getId());
        verify(soRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveStatsOption() {
        // Given
        when(soRepository.save(any(StatsOption.class))).thenReturn(mockSo1);

        // When
        StatsOption savedStatsOption = soService.save(mockSo1);

        // Then
        assert savedStatsOption != null;
        assertEquals(mockSo1.getId(), savedStatsOption.getId());
        verify(soRepository, times(1)).save(any(StatsOption.class));
    }

    @Test
    void shouldSaveAllStatsOptions() {
        // Given
        StatsOption mockSo2 = StatsOption.builder().build();
        when(soRepository.saveAll(anyIterable())).thenReturn(List.of(mockSo1, mockSo2));

        // When
        List<StatsOption> savedStatsOptions = soService.saveAll(List.of(mockSo1,mockSo2));

        // Then
        assert savedStatsOptions != null;
        assertEquals(2, savedStatsOptions.size());
        verify(soRepository, times(1)).saveAll(anyIterable());
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
        when(soRepository.findAllByPlaybookType(any(PlaybookType.class))).thenReturn(List.of(mockSo1, mockSo2));

        // When
        List<StatsOption> returnedStatsOptions = soService.findAllByPlaybookType(PlaybookType.ANGEL);

        // Then
        assert returnedStatsOptions != null;
        for (StatsOption statsOption : returnedStatsOptions) {
            assertEquals(PlaybookType.ANGEL, statsOption.getPlaybookType());
        }
        verify(soRepository, times(1)).findAllByPlaybookType(any(PlaybookType.class));
    }
}