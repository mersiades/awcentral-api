package com.mersiades.awcweb.graphql;

import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

    private final UserService userService;
    private final GameService gameService;

    public Query(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    public User userByDiscordId(String discordId) {
        System.out.println("discourseId = " + discordId);
        return userService.findByDiscordId(discordId);
    }
}
