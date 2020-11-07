package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.GameRoleReactiveRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Profile("jpa")
public class GameRoleServiceImpl implements GameRoleService {

    private final GameRoleReactiveRepository gameRoleRepository;
    private final CharacterService characterService;

    public GameRoleServiceImpl(GameRoleReactiveRepository gameRoleRepository, CharacterService characterService) {
        this.gameRoleRepository = gameRoleRepository;
        this.characterService = characterService;
    }

    @Override
    public Flux<GameRole> findAll() {
        return gameRoleRepository.findAll();
//        Set<GameRole> gameRoles = new HashSet<>();
//        gameRoleRepository.findAll().forEach(gameRoles::add);
//        return gameRoles;
    }

    @Override
    public Mono<GameRole> findById(String id) {
        return gameRoleRepository.findById(id);
//        Optional<GameRole> optionalGameRole = gameRoleRepository.findById(id);
//        return optionalGameRole.orElse(null);
    }

    @Override
    public Mono<GameRole> save(GameRole gameRole) {
//        Mono<GameRole> gameRoleMono = gameRoleRepository.save(gameRole);
//        System.out.println("gameRoleMono: " + gameRoleMono);
//        return gameRoleMono;
        return gameRoleRepository.save(gameRole);
    }

    @Override
    public void delete(GameRole gameRole) {
        gameRoleRepository.delete(gameRole);
    }

    @Override
    public void deleteById(String id) {
        gameRoleRepository.deleteById(id);
    }

    @Override
    public Flux<GameRole> findAllByUser(User user) {
        System.out.println("user = " + user);
        System.out.println("gameRoles: " + user.getGameRoles().toString());
        return gameRoleRepository.findAllByUser(user);
//        List<GameRole> gameRoles = gameRoleRepository.findAllByUser(user);
//        System.out.println("Game Roles: " + gameRoles.size());
//        return gameRoles;
    }

    @Override
    public Character addNewCharacter(String gameRoleId) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        Character newCharacter = new Character();
        System.out.println("newCharacter = " + newCharacter);
        characterService.save(newCharacter);
        assert gameRole != null;
        gameRole.getCharacters().add(newCharacter);
        gameRoleRepository.save(gameRole);
        return newCharacter;
    }

    @Override
    public Character setCharacterPlaybook(String gameRoleId, String characterId, Playbooks playbookType) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).block();
        assert gameRole != null;
        Character character = gameRole.getCharacters().stream().filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();
        character.setPlaybook(playbookType);
        characterService.save(character);
        gameRoleRepository.save(gameRole);
        return character;
    }
}
