package com.mersiades.awcweb.graphql;

import com.mersiades.awcdata.enums.LookCategories;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {

    private final GameService gameService;
    private final GameRoleService gameRoleService;

    public Mutation(GameService gameService, GameRoleService gameRoleService) {
        this.gameService = gameService;
        this.gameRoleService = gameRoleService;
    }

    public Game createGame(String discordId, String name) {
        System.out.println("Creating Game for Discord id: " + discordId);
        return gameService.createGameWithMC(discordId, name);
    }

    public Game appendChannels(String id, String textChannelId, String voiceChannelId) {
        System.out.println("Appending channels to Game: " + id);
        return gameService.appendChannels(id, textChannelId, voiceChannelId).block();
    }

    public Game deleteGame(String textChannelId) {
        System.out.println("Deleting Game with textChannelId: " + textChannelId);
        return gameService.deleteGameByTextChannelId(textChannelId).block();
    }

    public Character createCharacter(String gameRoleId) {
        System.out.println("Creating Character for for GameRole: " + gameRoleId);
        return gameRoleService.addNewCharacter(gameRoleId);
    }

    public Character setCharacterPlaybook(String gameRoleId, String characterId, Playbooks playbookType) {
        System.out.println("Setting Playbook for for Character: " + characterId);
        return gameRoleService.setCharacterPlaybook(gameRoleId, characterId, playbookType);
    }

    public Character setCharacterName(String gameRoleId, String characterId, String name) {
        System.out.println("Setting name for for Character: " + characterId);
        return gameRoleService.setCharacterName(gameRoleId, characterId, name);
    }

    public Character setCharacterLook(String gameRoleId, String characterId, String look, LookCategories category) {
        System.out.println("Setting a Look for for Character: " + characterId);
        return gameRoleService.setCharacterLook(gameRoleId, characterId, look, category);
    }

    public Character setCharacterStats(String gameRoleId, String characterId, String statsOptionId) {
        System.out.println("Setting CharacterStats for for Character: " + characterId);
        return gameRoleService.setCharacterStats(gameRoleId, characterId, statsOptionId);
    }

}
