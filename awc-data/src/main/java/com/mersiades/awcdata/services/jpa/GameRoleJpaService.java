package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.GameRoleRepository;
import com.mersiades.awcdata.services.GameRoleService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile("jpa")
public class GameRoleJpaService implements GameRoleService {

    private final GameRoleRepository gameRoleRepository;

    public GameRoleJpaService(GameRoleRepository gameRoleRepository) {
        this.gameRoleRepository = gameRoleRepository;
    }

    @Override
    public Set<GameRole> findAll() {
        Set<GameRole> gameRoles = new HashSet<>();
        gameRoleRepository.findAll().forEach(gameRoles::add);
        return gameRoles;
    }

    @Override
    public GameRole findById(Long id) {
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
    public void deleteById(Long id) {
        gameRoleRepository.deleteById(id);
    }

    @Override
    public List<GameRole> findAllByUser(User user) {
        System.out.println("user = " + user);
        System.out.println("gameRoles: " + user.getGameRoles().toString());
        List<GameRole> gameRoles = new ArrayList<>(gameRoleRepository.findAllByUser(user));
        System.out.println("Game Roles: " + gameRoles.size());
        return gameRoles;
//        return new ArrayList<>(gameRoleRepository.findAllByUser(user));
    }
}
