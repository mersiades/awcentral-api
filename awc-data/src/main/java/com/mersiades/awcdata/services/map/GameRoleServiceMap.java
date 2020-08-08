package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.services.GameRoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GameRoleServiceMap extends AbstractMapService<GameRole, Long> implements GameRoleService {

    @Override
    public Set<GameRole> findAll() {
        return super.findAll();
    }

    @Override
    public GameRole findById(Long id) {
        return super.findById(id);
    }

    @Override
    public GameRole save(GameRole gameRole) {
        return super.save(gameRole);
    }

    @Override
    public void delete(GameRole gameRole) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Set<GameRole> findByUserId(Long id) {
        Set<GameRole> usersGameRoles = new HashSet<>();
        for (GameRole gameRole : super.map.values()) {
            if (gameRole.getPlayerId().equals(id)) {
                usersGameRoles.add(gameRole);
            }
        }
        return usersGameRoles;
    }
}
