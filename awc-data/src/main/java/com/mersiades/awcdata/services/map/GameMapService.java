package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.services.GameService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class GameMapService implements GameService {

    private Map<Long, Game> gamesMap = new HashMap<>();

    @Override
    public Set<Game> findAll() {
        return new HashSet<>(gamesMap.values());
    }

    @Override
    public Game findById(Long id) {
        return gamesMap.get(id);
    }

    @Override
    public Game save(Game game) {
        if (game != null) {
            if (game.getTextChannelId() == null) {
                throw new RuntimeException("Game must have id");
            }
            gamesMap.put(game.getTextChannelId(), game);
        } else {
            throw new RuntimeException("Game cannot be null");
        }
        return game;
    }

    @Override
    public void delete(Game game) {
       gamesMap.entrySet().removeIf(entry -> entry.getValue() == game);
    }

    @Override
    public void deleteById(Long id) {
       gamesMap.remove(id);
    }

    @Override
    public GameRole findGameRoleByUserId(Long id) {
        // TODO: Figure out how to reach into a Game and pull out the GameRole that matches the id
        return null;
    }
}
