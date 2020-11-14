package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.repositories.CharacterRepository;
import com.mersiades.awcdata.services.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CharacterServiceImplTest {

    public static final String MOCK_CHARACTER_ID_1 = "mock-character-id-1";

    @Mock
    CharacterRepository characterRepository;

    CharacterService characterService;

    Character mockCharacter1;

    GameRole mockGameRole1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockGameRole1 = new GameRole();

        mockCharacter1 = Character.builder()
                .id(MOCK_CHARACTER_ID_1)
                .name("Mr Mock Character Name")
                .playbook(Playbooks.ANGEL)
                .gear("I haz some cool gear")
                .gameRole(mockGameRole1)
                .build();

        characterService = new CharacterServiceImpl(characterRepository);

    }

    @Test
    void shouldFindAllCharacters() {
        // Given
        Character mockCharacter2 = Character.builder().build();
        when(characterRepository.findAll()).thenReturn(Flux.just(mockCharacter1, mockCharacter2));

        // When
        List<Character> returnedCharacters = characterService.findAll().collectList().block();

        // Then
        assert returnedCharacters != null;
        assertEquals(2, returnedCharacters.size());
        verify(characterRepository, times(1)).findAll();
    }

    @Test
    void shouldFindCharacterById() {
        // Given
        when(characterRepository.findById(anyString())).thenReturn(Mono.just(mockCharacter1));

        // When
        Character returnedCharacter = characterService.findById(MOCK_CHARACTER_ID_1).block();

        // Then
        assert returnedCharacter != null;
        assertEquals(MOCK_CHARACTER_ID_1, returnedCharacter.getId());
        verify(characterRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveCharacter() {
        // Given
        when(characterRepository.save(any(Character.class))).thenReturn(Mono.just(mockCharacter1));

        // When
        Character savedCharacter = characterService.save(mockCharacter1).block();

        // Then
        assert savedCharacter != null;
        assertEquals(MOCK_CHARACTER_ID_1, savedCharacter.getId());
        verify(characterRepository, times(1)).save(any(Character.class));
    }

    @Test
    void shouldDeleteCharacter() {
        // When
        characterService.delete(mockCharacter1);

        // Then
        verify(characterRepository, times(1)).delete(any(Character.class));
    }

    @Test
    void shouldDeleteCharacterById() {
        // When
        characterService.deleteById(MOCK_CHARACTER_ID_1);

        // Then
        verify(characterRepository, times(1)).deleteById(anyString());
    }
}