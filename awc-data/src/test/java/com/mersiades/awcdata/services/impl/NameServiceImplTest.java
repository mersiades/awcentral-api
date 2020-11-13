package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Name;
import com.mersiades.awcdata.models.PlaybookCreator;
import com.mersiades.awcdata.repositories.NameReactiveRepository;
import com.mersiades.awcdata.services.NameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NameServiceImplTest {

    public static final String MOCK_NAME_ID_1 = "mock-name-id-1";

    @Mock
    NameReactiveRepository nameRepository;

    NameService nameService;

    Name mockName1;

    PlaybookCreator mockPlaybookCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockPlaybookCreator = PlaybookCreator.builder().build();
        mockName1 = Name.builder()
                .id(MOCK_NAME_ID_1)
                .name("Mr Mock Name")
                .playbookCreator(mockPlaybookCreator)
                .playbookType(Playbooks.ANGEL)
                .build();

        nameService = new NameServiceImpl(nameRepository);
    }

    @Test
    void shouldFindAllNames() {
        // Given
        Name mockName2 = Name.builder().build();
        when(nameRepository.findAll()).thenReturn(Flux.just(mockName1, mockName2));

        // When
        List<Name> returnedNames = nameService.findAll().collectList().block();

        // Then
        assert returnedNames != null;
        assertEquals(2, returnedNames.size());
        verify(nameRepository, times(1)).findAll();
    }

    @Test
    void shouldFindNameById() {
        // Given
        when(nameRepository.findById(anyString())).thenReturn(Mono.just(mockName1));

        // When
        Name returnedName = nameService.findById(MOCK_NAME_ID_1).block();

        // Then
        assert returnedName != null;
        assertEquals(MOCK_NAME_ID_1, returnedName.getId());
        verify(nameRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveName() {
        // Given
        when(nameRepository.save(any(Name.class))).thenReturn(Mono.just(mockName1));

        // When
        Name savedName = nameService.save(mockName1).block();

        // Then
        assert savedName != null;
        assertEquals(mockName1.getId(), savedName.getId());
        verify(nameRepository, times(1)).save(any(Name.class));
    }

//    @Test
//    void shouldSaveAllNames() {
//        // Given
//
//        // When
//
//        // Then
//    }

    @Test
    void shouldDeleteName() {
        // When
        nameService.delete(mockName1);

        // Then
        verify(nameRepository, times(1)).delete(any(Name.class));
    }

    @Test
    void shouldDeleteNameById() {
        // When
        nameService.deleteById(MOCK_NAME_ID_1);

        // Then
        verify(nameRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldFindAllNamesByPlaybookType() {
        // Given
        Name mockName3 = Name.builder()
                .id("mock-name-id-3")
                .name("another name")
                .playbookCreator(mockPlaybookCreator)
                .playbookType(Playbooks.ANGEL)
                .build();
        when(nameRepository.findAllByPlaybookType(any(Playbooks.class))).thenReturn(Flux.just(mockName1, mockName3));

        // When
        List<Name> angelNames = nameService.findAllByPlaybookType(Playbooks.ANGEL).collectList().block();

        // Then
        assert angelNames != null;
        assertEquals(2, angelNames.size());
        verify(nameRepository, times(1)).findAllByPlaybookType(any(Playbooks.class));
    }
}