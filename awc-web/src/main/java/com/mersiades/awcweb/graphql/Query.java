package com.mersiades.awcweb.graphql;

import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

    private final UserService userService;

    public Query(UserService userService) {
        this.userService = userService;
    }

    public User userByDiscordId(String discordId) {
        System.out.println("Fetching User by Discord id: " + discordId);
        return userService.findByDiscordId(discordId);
    }
}
