package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.GameRoleRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class GameRoleJpaService implements GameRoleService {

    private final GameRoleRepository gameRoleRepository;
    private final CharacterService characterService;

    public GameRoleJpaService(GameRoleRepository gameRoleRepository, CharacterService characterService) {
        this.gameRoleRepository = gameRoleRepository;
        this.characterService = characterService;
    }

    @Override
    public Set<GameRole> findAll() {
        Set<GameRole> gameRoles = new HashSet<>();
        gameRoleRepository.findAll().forEach(gameRoles::add);
        return gameRoles;
    }

    @Override
    public GameRole findById(String id) {
        Optional<GameRole> optionalGameRole = gameRoleRepository.findById(id);
        return optionalGameRole.orElse(null);
    }

    @Override
    public GameRole save(GameRole gameRole) {
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
    public List<GameRole> findAllByUser(User user) {
        System.out.println("user = " + user);
        System.out.println("gameRoles: " + user.getGameRoles().toString());
        List<GameRole> gameRoles = gameRoleRepository.findAllByUser(user);
        System.out.println("Game Roles: " + gameRoles.size());
        return gameRoles;
    }

    @Override
    public Character addNewCharacter(String gameRoleId) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow();
        Character newCharacter = new Character();
        System.out.println("newCharacter = " + newCharacter);
        characterService.save(newCharacter);
        gameRole.getCharacters().add(newCharacter);
        gameRoleRepository.save(gameRole);
        return newCharacter;
    }

    @Override
    public Character setCharacterPlaybook(String gameRoleId, String characterId, Playbooks playbookType) {
        GameRole gameRole = gameRoleRepository.findById(gameRoleId).orElseThrow();
        Character character = gameRole.getCharacters().stream().filter(character1 -> character1.getId().equals(characterId)).findFirst().orElseThrow();
        character.setPlaybook(playbookType);
        characterService.save(character);
        gameRoleRepository.save(gameRole);
        return character;
    }
}
