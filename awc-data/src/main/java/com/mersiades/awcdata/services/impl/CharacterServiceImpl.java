package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.repositories.CharacterRepository;
import com.mersiades.awcdata.services.CharacterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    @Override
    public Character findById(String id) {
        return characterRepository.findById(id).orElseThrow();
    }

    @Override
    public Character save(Character character) {
        return characterRepository.save(character);
    }

    @Override
    public List<Character> saveAll(List<Character> characters) {
        return characterRepository.saveAll(characters);
    }

    @Override
    public void delete(Character character) {
        characterRepository.delete(character);
    }

    @Override
    public void deleteById(String id) {
        characterRepository.deleteById(id);
    }
}
