package com.mersiades.awcweb.graphql;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.services.GameService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {

    private final GameService gameService;

    public Mutation(GameService gameService) {
        this.gameService = gameService;
    }

    public Game createGame(String discordId, String name, String textChannelId, String voiceChannelId) {
        System.out.println("Creating Game for Discord id: " + discordId);
        return gameService.createGameWithMC(discordId, name, textChannelId, voiceChannelId);
    }

    public Game deleteGame(String textChannelId) {
        System.out.println("Deleting Game with textChannelId: " + textChannelId);
        return gameService.deleteGameByTextChannelId(textChannelId);
    }
}
