package com.mersiades.awccontent.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.models.Name;
import com.mersiades.awccontent.models.PlaybookCreator;
import com.mersiades.awccontent.repositories.NameRepository;
import com.mersiades.awccontent.services.NameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.mersiades.awccontent.content.NamesContent.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NameServiceImplTest {

    @Mock
    NameRepository nameRepository;

    NameService nameService;

    PlaybookCreator mockPlaybookCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockPlaybookCreator = PlaybookCreator.builder().build();

        nameService = new NameServiceImpl(nameRepository);
    }

    @Test
    void shouldFindAllNames() {
        // Given
        when(nameRepository.findAll()).thenReturn(List.of(nameAngel1, nameAngel2));

        // When
        List<Name> returnedNames = nameService.findAll();

        // Then
        assert returnedNames != null;
        assertEquals(2, returnedNames.size());
        verify(nameRepository, times(1)).findAll();
    }

    @Test
    void shouldFindNameById() {
        // Given
        when(nameRepository.findById(anyString())).thenReturn(Optional.of(nameAngel1));

        // When
        Name returnedName = nameService.findById(nameAngel1.getId());

        // Then
        assert returnedName != null;
        assertEquals(nameAngel1.getId(), returnedName.getId());
        verify(nameRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveName() {
        // Given
        when(nameRepository.save(any(Name.class))).thenReturn(nameAngel1);

        // When
        Name savedName = nameService.save(nameAngel1);

        // Then
        assert savedName != null;
        assertEquals(nameAngel1.getId(), savedName.getId());
        verify(nameRepository, times(1)).save(any(Name.class));
    }

    @Test
    void shouldSaveAllNames() {
        // Given
        when(nameRepository.saveAll(anyIterable())).thenReturn(List.of(nameAngel1, nameAngel2));

        // When
        List<Name> savedNames = nameService.saveAll(List.of(nameAngel1,nameAngel2));

        // Then
        assert savedNames != null;
        assertEquals(2, savedNames.size());
        verify(nameRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteName() {
        // When
        nameService.delete(nameAngel1);

        // Then
        verify(nameRepository, times(1)).delete(any(Name.class));
    }

    @Test
    void shouldDeleteNameById() {
        // When
        nameService.deleteById(nameAngel1.getId());

        // Then
        verify(nameRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldFindAllNamesByPlaybookType() {
        // Given
        when(nameRepository.findAllByPlaybookType(any(PlaybookType.class))).thenReturn(List.of(nameAngel1, nameAngel3));

        // When
        List<Name> angelNames = nameService.findAllByPlaybookType(PlaybookType.ANGEL);

        // Then
        assert angelNames != null;
        assertEquals(2, angelNames.size());
        verify(nameRepository, times(1)).findAllByPlaybookType(any(PlaybookType.class));
    }
}