package com.mersiades.awcdata.services.jpa;

//@Service
//@Profile("jpa")
//public class CharacterJpaService implements CharacterService {
//
//    private final CharacterRepository characterRepository;
//
//    public CharacterJpaService(CharacterRepository characterRepository) {
//        this.characterRepository = characterRepository;
//    }
//
//    @Override
//    public Set<Character> findAll() {
//        Set<Character> characters = new HashSet<>();
//        characterRepository.findAll().forEach(characters::add);
//        return characters;
//    }
//
//    @Override
//    public Character findById(String id) {
//        Optional<Character> optionalCharacter = characterRepository.findById(id);
//        return optionalCharacter.orElse(null);
//    }
//
//    @Override
//    public Character save(Character character) {
//        return characterRepository.save(character);
//    }
//
//    @Override
//    public void delete(Character character) {
//        characterRepository.delete(character);
//    }
//
//    @Override
//    public void deleteById(String id) {
//        characterRepository.deleteById(id);
//    }
//}
