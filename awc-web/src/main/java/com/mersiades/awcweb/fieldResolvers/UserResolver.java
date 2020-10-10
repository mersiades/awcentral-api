package com.mersiades.awcweb.fieldResolvers;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserResolver implements GraphQLResolver<User> {
    private final GameService gameService;
    private final GameRoleService gameRoleService;

    public UserResolver(GameService gameService, GameRoleService gameRoleService) {
        this.gameService = gameService;
        this.gameRoleService = gameRoleService;
    }

    public List<Game> getGames(User user) {
        return gameService.findAllByUsers(user);
    }

    public List<GameRole> getGameRoles(User user) {
        return gameRoleService.findAllByUser(user);
    }
}
