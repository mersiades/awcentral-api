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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public List<Game> findAllByUsers(User user) {
        return this.findAll()
                .stream()
                .filter(otherUser -> otherUser.getId().equals(user.getId()))
                .collect(Collectors.toList());
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
        Game newGame = new Game();

        // Find the User who created the game and add to Game
        User creator = userService.findByDiscordId(discordId);
        newGame.getUsers().add(creator);

        // Create the MC GameRole and add to Game
        GameRole mcGameRole = new GameRole(Roles.MC, newGame, creator);
        newGame.getGameRoles().add(mcGameRole);
        gameRoleService.save(mcGameRole);

        this.save(newGame);

        return newGame;
    }
}






















