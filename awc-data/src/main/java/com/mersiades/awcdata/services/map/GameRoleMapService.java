package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.GameRoleService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({ "default", "map"})
public class GameRoleMapService extends AbstractMapService<GameRole, Long> implements GameRoleService {

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
        super.delete(gameRole);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public List<GameRole> findAllByUser(User user) {
        return new ArrayList<>(this.findAll());
    }

    @Override
    public List<GameRole> findAllByGame(Game game) {
        return this.findAll().stream()
                .filter(gameRole -> gameRole.getGame().getId().equals(game.getId()))
                .collect(Collectors.toList());
    }
}
