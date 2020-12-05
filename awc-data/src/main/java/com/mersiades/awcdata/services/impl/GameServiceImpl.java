package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.GameRepository;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final GameRoleService gameRoleService;

    public GameServiceImpl(GameRepository gameRepository, UserService userService, GameRoleService gameRoleService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.gameRoleService = gameRoleService;
    }

    @Override
    public Flux<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Mono<Game> findById(String id) {
        return gameRepository.findById(id);
    }

    @Override
    public Mono<Game> save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Flux<Game> saveAll(Flux<Game> games) {
        return gameRepository.saveAll(games);
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
    public Mono<Game> findByGameRoles(GameRole gameRole) {
        return gameRepository.findByGameRoles(gameRole);
    }

    @Override
    public Game createGameWithMC(String discordId, String name) {
        System.out.println("createGameWithMC in gameServiceImpl");
        // Create the new game
        Game newGame = new Game(UUID.randomUUID().toString(), name);

        // Find the User who created the game to associate with GameRole
        User creator = userService.findByDiscordId(discordId).block();

        // Create an MC GameRole for the Game creator and add it to the Game
        GameRole mcGameRole = new GameRole(UUID.randomUUID().toString(), Roles.MC);
        newGame.getGameRoles().add(mcGameRole);
        Game savedGame = gameRepository.save(newGame).block();

        // Add the Game and User to the MC's GameRole
        mcGameRole.setGame(savedGame);
        mcGameRole.setUser(creator);
        gameRoleService.save(mcGameRole).block();

        return newGame;
    }

    @Override
    public Mono<Game> deleteGameByTextChannelId(String textChannelId) {
        // TODO: check if changing this to deleteByTextChannelId helps
        return gameRepository.deleteGameByTextChannelId(textChannelId).map(game -> {
            for (GameRole gameRole: game.getGameRoles()) {
                gameRoleService.delete(gameRole);
            }
            return game;
        });
    }

    @Override
    public Mono<Game> findGameByTextChannelId(String textChannelId) {
        return gameRepository.findGameByTextChannelId(textChannelId);
    }

    @Override
    public Mono<Game> appendChannels(String id, String textChannelId, String voiceChannelId) {
        return gameRepository.findById(id).flatMap(game -> {
            game.setTextChannelId(textChannelId);
            game.setVoiceChannelId(voiceChannelId);
            return gameRepository.save(game);
        });
    }
}
