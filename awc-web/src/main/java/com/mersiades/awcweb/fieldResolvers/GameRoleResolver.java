package com.mersiades.awcweb.fieldResolvers;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.services.GameService;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

@Component
public class GameRoleResolver implements GraphQLResolver<GameRole> {

    private final GameService gameService;

    public GameRoleResolver(GameService gameService) {
        this.gameService = gameService;
    }

    public Game getGame(GameRole gameRole) {
        return gameService.findByGameRoles(gameRole);
    }

    public String getUserId(GameRole gameRole) {
        return gameRole.getUser().getId();
    }
}
