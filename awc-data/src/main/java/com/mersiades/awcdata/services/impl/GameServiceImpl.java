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

import java.util.NoSuchElementException;
import java.util.Optional;
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
    public Game createGameWithMC(String userId, String name) throws Exception {
        System.out.println("createGameWithMC in gameServiceImpl");
        // Create the new game
        Game newGame = Game.builder().id(UUID.randomUUID().toString()).name(name).build();

        // Find the User who created the game to associate with GameRole
        Optional<User> creatorOptional = userService.findById(userId).blockOptional();

        User creator;
        if (creatorOptional.isEmpty()) {
            User newUser = User.builder().id(userId).build();
            creator = userService.save(newUser).block();
        } else {
            creator = creatorOptional.get();
        }

        // Create an MC GameRole for the Game creator and add it to the Game
        GameRole mcGameRole = GameRole.builder().id(UUID.randomUUID().toString()).role(Roles.MC).build();
        newGame.getGameRoles().add(mcGameRole);
        Game savedGame = gameRepository.save(newGame).block();

        assert creator != null;
        userService.addGameroleToUser(creator.getId(), mcGameRole);
        // Add the Game and User to the MC's GameRole
        mcGameRole.setGame(savedGame);
        mcGameRole.setUser(creator);
        gameRoleService.save(mcGameRole).block();

        return newGame;
    }

    @Override
    public Mono<Game> findAndDeleteById(String gameId) {
        return gameRepository.findById(gameId).map(game -> {
            for (GameRole gameRole: game.getGameRoles()) {
                gameRoleService.delete(gameRole);
            }
            gameRepository.delete(game);
            return game;
        });
    }

    @Override
    public Game addInvitee(String gameId, String email) {
        Game game = findById(gameId).blockOptional().orElseThrow(NoSuchElementException::new);
        game.getInvitees().add(email);
        gameRepository.save(game);
        return game;
    }

}
