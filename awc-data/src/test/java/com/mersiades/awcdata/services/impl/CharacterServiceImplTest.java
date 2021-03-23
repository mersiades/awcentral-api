package com.mersiades.awcdata.services.impl;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.repositories.CharacterRepository;
import com.mersiades.awcdata.services.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

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
                .playbook(PlaybookType.ANGEL)
                .gear(List.of("gun", "knife"))
                .gameRole(mockGameRole1)
                .build();

        characterService = new CharacterServiceImpl(characterRepository);

    }

    @Test
    void shouldFindAllCharacters() {
        // Given
        Character mockCharacter2 = Character.builder().build();
        when(characterRepository.findAll()).thenReturn(List.of(mockCharacter1, mockCharacter2));

        // When
        List<Character> returnedCharacters = characterService.findAll();

        // Then
        assert returnedCharacters != null;
        assertEquals(2, returnedCharacters.size());
        verify(characterRepository, times(1)).findAll();
    }

    @Test
    void shouldFindCharacterById() {
        // Given
        when(characterRepository.findById(anyString())).thenReturn(Optional.of(mockCharacter1));

        // When
        Character returnedCharacter = characterService.findById(MOCK_CHARACTER_ID_1);

        // Then
        assert returnedCharacter != null;
        assertEquals(MOCK_CHARACTER_ID_1, returnedCharacter.getId());
        verify(characterRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldSaveCharacter() {
        // Given
        when(characterRepository.save(any(Character.class))).thenReturn(mockCharacter1);

        // When
        Character savedCharacter = characterService.save(mockCharacter1);

        // Then
        assert savedCharacter != null;
        assertEquals(MOCK_CHARACTER_ID_1, savedCharacter.getId());
        verify(characterRepository, times(1)).save(any(Character.class));
    }

    @Test
    void shouldSaveAllCharacters() {
        // Given
        Character mockCharacter2 = Character.builder().build();
        when(characterRepository.saveAll(anyIterable())).thenReturn(List.of(mockCharacter1, mockCharacter2));

        // When
        List<Character> savedLooks = characterService.saveAll(List.of(mockCharacter1,mockCharacter2));

        // Then
        assert savedLooks != null;
        assertEquals(2, savedLooks.size());
        verify(characterRepository, times(1)).saveAll(anyIterable());
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