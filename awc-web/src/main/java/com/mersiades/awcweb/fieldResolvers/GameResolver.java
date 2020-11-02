package com.mersiades.awcweb.fieldResolvers;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.services.GameRoleService;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

@Component
public class GameResolver implements GraphQLResolver<Game> {

    private final GameRoleService gameRoleService;

    public GameResolver(GameRoleService gameRoleService) {
        this.gameRoleService = gameRoleService;
    }

}
