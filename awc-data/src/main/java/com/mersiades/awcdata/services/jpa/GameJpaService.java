package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.GameRepository;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Profile("jpa")
public class GameJpaService implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final GameRoleService gameRoleService;

    public GameJpaService(GameRepository gameRepository, UserService userService, GameRoleService gameRoleService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.gameRoleService = gameRoleService;
    }

    @Override
    public Set<Game> findAll() {
        Set<Game> games = new HashSet<>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    @Override
    public Game findById(String id) {
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
    public void deleteById(String id) {
        gameRepository.deleteById(id);
    }

    @Override
    public Game findByGameRoles(GameRole gameRole) {
        return gameRepository.findByGameRoles(gameRole);
    }

    @Override
    public Game createGameWithMC(String discordId, String name, String textChannelId, String voiceChannelId) {
        // Create the new game
        Game newGame = new Game(UUID.randomUUID().toString(), textChannelId, voiceChannelId, name);

        // Find the User who created the game to associate with GameRole
        User creator = userService.findByDiscordId(discordId);

        // Create an MC GameRole for the Game creator and add it to the Game
        GameRole mcGameRole = new GameRole(UUID.randomUUID().toString(), Roles.MC);
        newGame.getGameRoles().add(mcGameRole);
        Game savedGame = gameRepository.save(newGame);

        // Add the Game and User to the MC's GameRole
        mcGameRole.setGame(savedGame);
        mcGameRole.setUser(creator);
        gameRoleService.save(mcGameRole);

        return newGame;
    }

    // transactional
    @Override
    public Game deleteGameByTextChannelId(String textChannelId) {
        gameRepository.deleteGameByTextChannelId(textChannelId);
        return null;
    }

    @Override
    public Game findGameByTextChannelId(String textChannelId) {
        return gameRepository.findGameByTextChannelId(textChannelId);
    }
}
