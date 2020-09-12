package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.repositories.CharacterRepository;
import com.mersiades.awcdata.services.CharacterService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class CharacterJpaService implements CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterJpaService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public Set<Character> findAll() {
        Set<Character> characters = new HashSet<>();
        characterRepository.findAll().forEach(characters::add);
        return characters;
    }

    @Override
    public Character findById(Long id) {
        Optional<Character> optionalCharacter = characterRepository.findById(id);
        return optionalCharacter.orElse(null);
    }

    @Override
    public Character save(Character character) {
        return characterRepository.save(character);
    }

    @Override
    public void delete(Character character) {
        characterRepository.delete(character);
    }

    @Override
    public void deleteById(Long id) {
        characterRepository.deleteById(id);
    }
}
