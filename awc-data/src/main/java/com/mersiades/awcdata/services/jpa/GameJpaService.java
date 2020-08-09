package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.repositories.GameRepository;
import com.mersiades.awcdata.services.GameService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class GameJpaService implements GameService {

    private final GameRepository gameRepository;

    public GameJpaService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Set<Game> findAll() {
        Set<Game> games = new HashSet<>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    @Override
    public Game findById(Long id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        return optionalGame.orElse(null);
    }

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void delete(Game game) {
        gameRepository.delete(game);
    }

    @Override
    public void deleteById(Long id) {
        gameRepository.deleteById(id);
    }
}
