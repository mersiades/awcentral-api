package com.mersiades.awcweb.graphql;

import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.MoveService;
import com.mersiades.awcdata.services.PlaybookService;
import com.mersiades.awcdata.services.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private final UserService userService;
    private final GameService gameService;
    private final MoveService moveService;
    private final PlaybookService playbookService;

    public Query(UserService userService, GameService gameService, MoveService moveService, PlaybookService playbookService) {
        this.userService = userService;
        this.gameService = gameService;
        this.moveService = moveService;
        this.playbookService = playbookService;
    }

    public User userByDiscordId(String discordId) {
        System.out.println("Fetching User by Discord id: " + discordId);
        return userService.findByDiscordId(discordId);
    }

    public Game gameByTextChannelId(String textChannelId) {
        System.out.println("Fetching Game by textChannelId: " + textChannelId);
        return gameService.findGameByTextChannelId(textChannelId).block();
    }

    public Game gameForPlayer(String textChannelId, String userId) {
        System.out.println("Fetching Game for player: " + textChannelId);

        // Get the Game
        Game game = gameService.findGameByTextChannelId(textChannelId).block();

        // Get the User's GameRole from the Game
        assert game != null;
        GameRole usersGameRole = game.getGameRoles().stream().filter(gameRole -> gameRole.getUser().getId().equals(userId)).findFirst().orElseThrow();

        // Remove all GameRoles
        game.getGameRoles().clear();

        // Reinstate User's GameRole
        game.getGameRoles().add(usersGameRole);

        // Return the game, but with only the User's (player) GameRole
        return game;
    }

    public List<Move> allMoves() {
        return moveService.findAll().collectList().block();
    }

    public List<Playbook> playbooks() {
        return playbookService.findAll().collectList().block();
    }
}
