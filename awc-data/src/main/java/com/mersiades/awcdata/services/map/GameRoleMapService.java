package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.services.GameRoleService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

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
}
