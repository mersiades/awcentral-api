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

import static com.mersiades.awccontent.content.StatOptionsContent.statsOptionAngel1;
import static com.mersiades.awccontent.content.StatOptionsContent.statsOptionAngel2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StatsOptionServiceImplTest {

    @Mock
    StatsOptionRepository soRepository;

    StatsOptionService soService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        soService = new StatsOptionServiceImpl(soRepository);
    }

    @Test
    void shouldFindAllStatsOptions() {
        // Given
        when(soRepository.findAll()).thenReturn(List.of(statsOptionAngel1, statsOptionAngel2));

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
        when(soRepository.findById(anyString())).thenReturn(Optional.of(statsOptionAngel1));

        // When
        StatsOption returnedStatsOption = soService.findById(statsOptionAngel1.getId());

        // Then
        assert returnedStatsOption != null;
        assertEquals(statsOptionAngel1.getId(), returnedStatsOption.getId());
        verify(soRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveStatsOption() {
        // Given
        when(soRepository.save(any(StatsOption.class))).thenReturn(statsOptionAngel1);

        // When
        StatsOption savedStatsOption = soService.save(statsOptionAngel1);

        // Then
        assert savedStatsOption != null;
        assertEquals(statsOptionAngel1.getId(), savedStatsOption.getId());
        verify(soRepository, times(1)).save(any(StatsOption.class));
    }

    @Test
    void shouldSaveAllStatsOptions() {
        // Given
        StatsOption mockSo2 = StatsOption.builder().build();
        when(soRepository.saveAll(anyIterable())).thenReturn(List.of(statsOptionAngel1, statsOptionAngel2));

        // When
        List<StatsOption> savedStatsOptions = soService.saveAll(List.of(statsOptionAngel1, statsOptionAngel2));

        // Then
        assert savedStatsOptions != null;
        assertEquals(2, savedStatsOptions.size());
        verify(soRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteStatsOption() {
        // When
        soService.delete(statsOptionAngel2);

        // Then
        verify(soRepository, times(1)).delete(any(StatsOption.class));
    }

    @Test
    void shouldDeleteStatsOptionById() {
        // When
        soService.deleteById(statsOptionAngel1.getId());

        // Then
        verify(soRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldFindAllStatsOptionsByPlaybookType() {
        // Given
        when(soRepository.findAllByPlaybookType(any(PlaybookType.class))).thenReturn(List.of(statsOptionAngel1, statsOptionAngel2));

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