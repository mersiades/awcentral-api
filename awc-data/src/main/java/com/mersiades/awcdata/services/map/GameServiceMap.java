package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.services.GameService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GameServiceMap extends AbstractMapService<Game, Long> implements GameService {

    @Override
    public Set<Game> findAll() {
        return super.findAll();
    }

    @Override
    public Game findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Game save(Game game) {
        return super.save(game);
    }

    @Override
    public void delete(Game game) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public GameRole addGameRole(Long playerId, Long gameId, GameRole.Role role) {
        return new GameRole(playerId, gameId, role);
    }
}
