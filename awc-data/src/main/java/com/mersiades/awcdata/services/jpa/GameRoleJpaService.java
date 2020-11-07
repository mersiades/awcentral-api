package com.mersiades.awcdata.services.jpa;

//@Service
//@Profile("jpa")
//public class GameRoleJpaService implements GameRoleService {
//
//    private final GameRoleRepository gameRoleRepository;
//    private final CharacterService characterService;
//
//    public GameRoleJpaService(GameRoleRepository gameRoleRepository, CharacterService characterService) {
//        this.gameRoleRepository = gameRoleRepository;
//        this.characterService = characterService;
//    }
//
//    @Override
//    public Set<GameRole> findAll() {
//        Set<GameRole> gameRoles = new HashSet<>();
//        gameRoleRepository.findAll().forEach(gameRoles::add);
//        return gameRoles;
//    }
//
//    @Override
//    public GameRole findById(String id) {
//        Optional<GameRole> optionalGameRole = gameRoleRepository.findById(id);
//        return optionalGameRole.orElse(null);
//    }
//
//    @Override
//    public GameRole save(GameRole gameRole) {
//        return gameRoleRepository.save(gameRole);
//    }
//
//    @Override
//    public void delete(GameRole gameRole) {
//        gameRoleRepository.delete(gameRole);
//    }
//
//    @Override
//    public void deleteById(String id) {
//        gameRoleRepository.deleteById(id);
//    }
//
//    @Override
//    public List<GameRole> findAllByUser(User user) {
//        System.out.println("user = " + user);
//        System.out.println("gameRoles: " + user.getGameRoles().toString());
//        List<GameRole> gameRoles = gameRoleRepository.findAllByUser(user);
//        System.out.println("Game Roles: " + gameRoles.size());
//        return gameRoles;
//    }
//
//    @Override
//    public Character addNewCharacter(String gameRoleId) {
//        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow();
//        Character newCharacter = new Character();
//        System.out.println("newCharacter = " + newCharacter);
//        characterService.save(newCharacter);
//        gameRole.getCharacters().add(newCharacter);
//        gameRoleRepository.save(gameRole);
//        return newCharacter;
//    }
//
//    @Override
//    public Character setCharacterPlaybook(String gameRoleId, String characterId, Playbooks playbookType) {
//        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow();
//        Character character = gameRole.getCharacters().stream().filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();
//        character.setPlaybook(playbookType);
//        characterService.save(character);
//        gameRoleRepository.save(gameRole);
//        return character;
//    }
//}
