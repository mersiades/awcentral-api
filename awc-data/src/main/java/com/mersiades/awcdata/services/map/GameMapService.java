package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Profile({ "default", "map"})
public class GameMapService extends AbstractMapService<Game, Long> implements GameService {
    private final UserService userService;
    private final GameRoleService gameRoleService;

    public GameMapService(UserService userService, GameRoleService gameRoleService) {
        this.userService = userService;
        this.gameRoleService = gameRoleService;
    }

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
        super.delete(game);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Game findByGameRoles(GameRole gameRole) {
        Optional<Game> optionalGame = this.findAll()
                .stream()
                .filter(game -> game.getId().equals(gameRole.getGame().getId()))
                .findFirst();
        return optionalGame.orElse(null);
    }

    @Override
    public Game createGameWithMC(String discordId, String name, String textChannelId, String voiceChannelId) {
        // Find the User who created the game and add to Game
        User creator = userService.findByDiscordId(discordId);

        // Create the MC GameRole for the Game creator
        GameRole mcGameRole = new GameRole(Roles.MC, creator);

        Game newGame = new Game(textChannelId, voiceChannelId, name, mcGameRole);

        this.save(newGame);

        mcGameRole.setGame(newGame);
        gameRoleService.save(mcGameRole);

        return newGame;
    }

    @Override
    public Game deleteGameByTextChannelId(String textChannelId) {
        Optional<Game> gameToDeleteOptional = this.findAll().stream()
                .filter(game -> game.getTextChannelId().equals(textChannelId))
                .findFirst();
        if (gameToDeleteOptional.isEmpty()) {
            System.out.println("Could not find game to delete. TextChannelId = " + textChannelId);
        } else {
        this.delete(gameToDeleteOptional.get());
        }
        return null;
    }
}






















