package com.mersiades.awcweb.graphql;

import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.CharacterHarm;
import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.HxStat;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awccontent.enums.LookCategories;
import com.mersiades.awccontent.enums.Playbooks;
import com.mersiades.awccontent.enums.Stats;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mutation implements GraphQLMutationResolver {

    private final GameService gameService;
    private final GameRoleService gameRoleService;

    public Mutation(GameService gameService, GameRoleService gameRoleService) {
        this.gameService = gameService;
        this.gameRoleService = gameRoleService;
    }

    public Game createGame(String userId, String displayName, String email, String name) throws Exception {
        System.out.println("Creating Game for User: " + userId);
        return gameService.createGameWithMC(userId, displayName, email, name);
    }

    public Game setGameName(String gameId, String name) throws Exception {
        System.out.println("Setting name for Game: " + gameId);
        return gameService.setGameName(gameId, name).block();
    }

    public Game addInvitee(String gameId, String email) {
        System.out.println("Adding invitee to Game: " + gameId);
        return gameService.addInvitee(gameId, email);
    }

    public Game addCommsApp(String gameId, String app) {
        System.out.println("Adding comms app to Game: " + gameId);
        return gameService.addCommsApp(gameId, app);
    }

    public Game addCommsUrl(String gameId, String url) {
        System.out.println("Adding comms url to Game: " + gameId);
        return gameService.addCommsUrl(gameId, url);
    }

    public Game removeInvitee(String gameId, String email) {
        System.out.println("Removing invitee from Game: " + gameId);
        return gameService.removeInvitee(gameId, email);
    }

    public Game addUserToGame(String gameId, String userId, String displayName, String email) throws Exception {
        System.out.println("Adding User to Game: " + gameId);
        return gameService.addUserToGame(gameId, userId, displayName, email);
    }

    public Game deleteGame(String gameId) {
        System.out.println("Deleting Game with id: " + gameId);
        return gameService.findAndDeleteById(gameId);
    }

    public Game finishPreGame(String gameId) {
        System.out.println("Finishing pre-game for Game with id: " + gameId);
        return gameService.finishPreGame(gameId).block();
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
        System.out.println("Setting CharacterStats for Character: " + characterId);
        return gameRoleService.setCharacterStats(gameRoleId, characterId, statsOptionId);
    }

    public Character setCharacterGear(String gameRoleId, String characterId, List<String> gear) {
        System.out.println("Setting CharacterStats for Character: " + characterId);
        return gameRoleService.setCharacterGear(gameRoleId, characterId, gear);
    }

    public Character setCharacterBarter(String gameRoleId, String characterId, int amount) {
        System.out.println("Setting barter for Character: " + characterId);
        return gameRoleService.setCharacterBarter(gameRoleId, characterId, amount);
    }

    public Character setBrainerGear(String gameRoleId, String characterId, List<String> brainerGear) {
        System.out.println("Setting setBrainerGear for Character: " + characterId);
        return gameRoleService.setBrainerGear(gameRoleId, characterId, brainerGear);
    }

    public Character setAngelKit(String gameRoleId, String characterId, int stock, Boolean hasSupplier) {
        System.out.println("Setting AngelKit for Character: " + characterId);
        return gameRoleService.setAngelKit(gameRoleId, characterId, stock, hasSupplier);
    }

    public Character setCustomWeapons(String gameRoleId, String characterId, List<String> weapons) {
        System.out.println("Setting CustomWeapons for Character: " + characterId);
        return gameRoleService.setCustomWeapons(gameRoleId, characterId, weapons);
    }

    public Character setCharacterMoves(String gameRoleId, String characterId, List<String> moveIds) {
        System.out.println("Setting Moves for Character: " + characterId);
        return gameRoleService.setCharacterMoves(gameRoleId, characterId, moveIds);
    }

    public Character setCharacterHx(String gameRoleId, String characterId, List<HxStat> hxStats) {
        System.out.println("Setting Hx for Character: " + characterId);
        return gameRoleService.setCharacterHx(gameRoleId, characterId, hxStats);
    }

    public Character adjustCharacterHx(String gameRoleId, String characterId, String hxId, int value) {
        System.out.println("Adjusting Hx for Character: " + characterId);
        return gameRoleService.adjustCharacterHx(gameRoleId, characterId, hxId, value);
    }

    public Character setCharacterHarm(String gameRoleId, String characterId, CharacterHarm harm) {
        System.out.println("Setting harm for Character: " + characterId);
        return gameRoleService.setCharacterHarm(gameRoleId, characterId, harm);
    }

    public Character toggleStatHighlight(String gameRoleId, String characterId, Stats stat) {
        System.out.println("Setting harm for Character: " + characterId);
        return gameRoleService.toggleStatHighlight(gameRoleId, characterId, stat);
    }

    public Character finishCharacterCreation(String gameRoleId, String characterId) {
        System.out.println("Finishing character creation for Character: " + characterId);
        return gameRoleService.finishCharacterCreation(gameRoleId, characterId);
    }

}
