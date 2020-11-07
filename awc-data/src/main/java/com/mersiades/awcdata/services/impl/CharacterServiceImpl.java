package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.repositories.CharacterReactiveRepository;
import com.mersiades.awcdata.services.CharacterService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Profile("jpa")
public class CharacterServiceImpl implements CharacterService {

    private final CharacterReactiveRepository characterRepository;

    public CharacterServiceImpl(CharacterReactiveRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public Flux<Character> findAll() {
        return characterRepository.findAll();
//        Set<Character> characters = new HashSet<>();
//        characterRepository.findAll().forEach(characters::add);
//        return characters;
    }

    @Override
    public Mono<Character> findById(String id) {
        return characterRepository.findById(id);
//        Mono<Character> optionalCharacter = characterRepository.findById(id);
//        return optionalCharacter.orElse(null);
    }

    @Override
    public Mono<Character> save(Character character) {
        return characterRepository.save(character);
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
