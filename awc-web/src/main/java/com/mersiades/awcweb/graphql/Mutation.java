package com.mersiades.awcweb.graphql;

import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.enums.StatType;
import com.mersiades.awccontent.models.Look;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.*;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
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

    // ---------------------------------------------- Game-related -------------------------------------------- //
    public Game createGame(String userId, String displayName, String email, String name) throws Exception {
        System.out.println("Creating Game for User: " + userId);
        return gameService.createGameWithMC(userId, displayName, email, name);
    }

    public Game setGameName(String gameId, String name)  {
        System.out.println("Setting name for Game: " + gameId);
        return gameService.setGameName(gameId, name);
    }

    public Game addInvitee(String gameId, String email) {
        System.out.println("Adding invitee to Game: " + gameId);
        return gameService.addInvitee(gameId, email);
    }

    public Game removeInvitee(String gameId, String email) {
        System.out.println("Removing invitee from Game: " + gameId);
        return gameService.removeInvitee(gameId, email);
    }

    public Game removePlayer(String gameId, String playerId) {
        System.out.println("Removing player from Game: " + gameId);
        return gameService.removePlayer(gameId, playerId);
    }

    public Game addCommsApp(String gameId, String app) {
        System.out.println("Adding comms app to Game: " + gameId);
        return gameService.addCommsApp(gameId, app);
    }

    public Game addCommsUrl(String gameId, String url) {
        System.out.println("Adding comms url to Game: " + gameId);
        return gameService.addCommsUrl(gameId, url);
    }

    public Game deleteGame(String gameId) {
        System.out.println("Deleting Game with id: " + gameId);
        return gameService.findAndDeleteById(gameId);
    }

    public Game addUserToGame(String gameId, String userId, String displayName, String email) throws Exception {
        System.out.println("Adding User to Game: " + gameId);
        return gameService.addUserToGame(gameId, userId, displayName, email);
    }

    // ---------------------------------------------- MC stuff -------------------------------------------- //

    public Game finishPreGame(String gameId) {
        System.out.println("Finishing pre-game for Game with id: " + gameId);
        return gameService.finishPreGame(gameId);
    }

    public Game closeFirstSession(String gameId) {
        System.out.println("Closing first session for Game with id: " + gameId);
        return gameService.closeFirstSession(gameId);
    }

    public GameRole addThreat(String gameRoleId, Threat threat) {
        System.out.println("Adding Threat for GameRole with id: " + gameRoleId);
        return gameRoleService.addThreat(gameRoleId, threat);
    }

    public GameRole addNpc(String gameRoleId, Npc npc) {
        System.out.println("Adding Npc for GameRole with id: " + gameRoleId);
        return gameRoleService.addNpc(gameRoleId, npc);
    }

    // ------------------------------------ Creating and editing characters ---------------------------------- //

    public Character createCharacter(String gameRoleId) {
        System.out.println("Creating Character for for GameRole: " + gameRoleId);
        return gameRoleService.addNewCharacter(gameRoleId);
    }

    public Character setCharacterPlaybook(String gameRoleId, String characterId, PlaybookType playbookType) {
        System.out.println("Setting Playbook for for Character: " + characterId);
        return gameRoleService.setCharacterPlaybook(gameRoleId, characterId, playbookType);
    }

    public Character setCharacterName(String gameRoleId, String characterId, String name) {
        System.out.println("Setting name for for Character: " + characterId);
        return gameRoleService.setCharacterName(gameRoleId, characterId, name);
    }

    public Character setCharacterLook(String gameRoleId, String characterId, Look look) {
        System.out.println("Setting a Look for for Character: " + characterId);
        return gameRoleService.setCharacterLook(gameRoleId, characterId, look);
    }

    public Character setCharacterStats(String gameRoleId, String characterId, String statsOptionId) {
        System.out.println("Setting CharacterStats for Character: " + characterId);
        return gameRoleService.setCharacterStats(gameRoleId, characterId, statsOptionId);
    }

    public Character setCharacterGear(String gameRoleId, String characterId, List<String> gear) {
        System.out.println("Setting CharacterStats for Character: " + characterId);
        return gameRoleService.setCharacterGear(gameRoleId, characterId, gear);
    }

    public Character setCharacterMoves(String gameRoleId, String characterId, List<String> moveIds) {
        System.out.println("Setting Moves for Character: " + characterId);
        return gameRoleService.setCharacterMoves(gameRoleId, characterId, moveIds);
    }

    public Character setCharacterHx(String gameRoleId, String characterId, List<HxStat> hxStats) {
        System.out.println("Setting Hx for Character: " + characterId);
        return gameRoleService.setCharacterHx(gameRoleId, characterId, hxStats);
    }

    public Character finishCharacterCreation(String gameRoleId, String characterId) {
        System.out.println("Finishing character creation for Character: " + characterId);
        return gameRoleService.finishCharacterCreation(gameRoleId, characterId);
    }

    // --------------------------------------- Setting Playbook Uniques ------------------------------------- //

    public Character setAngelKit(String gameRoleId, String characterId, int stock, Boolean hasSupplier) {
        System.out.println("Setting AngelKit for Character: " + characterId);
        return gameRoleService.setAngelKit(gameRoleId, characterId, stock, hasSupplier);
    }

    public Character setBrainerGear(String gameRoleId, String characterId, List<String> brainerGear) {
        System.out.println("Setting setBrainerGear for Character: " + characterId);
        return gameRoleService.setBrainerGear(gameRoleId, characterId, brainerGear);
    }

    public Character setCustomWeapons(String gameRoleId, String characterId, List<String> weapons) {
        System.out.println("Setting CustomWeapons for Character: " + characterId);
        return gameRoleService.setCustomWeapons(gameRoleId, characterId, weapons);
    }

    public Character setEstablishment(String gameRoleId, String characterId, Establishment establishment) {
        System.out.println("Setting Establishment for Character: " + characterId);
        return gameRoleService.setEstablishment(gameRoleId, characterId, establishment);
    }

    public Character setFollowers(String gameRoleId, String characterId, Followers followers) {
        System.out.println("Setting Holding for Character: " + characterId);
        return gameRoleService.setFollowers(gameRoleId, characterId, followers);
    }

    public Character setGang(String gameRoleId, String characterId, Gang gang) {
        System.out.println("Setting Gang for Character: " + characterId);
        return gameRoleService.setGang(gameRoleId, characterId, gang);
    }

    public Character setHolding(String gameRoleId, String characterId, Holding holding, int vehicleCount, int battleVehicleCount) {
        System.out.println("Setting Holding for Character: " + characterId);
        return gameRoleService.setHolding(gameRoleId, characterId, holding, vehicleCount, battleVehicleCount);
    }

    public Character setSkinnerGear(String gameRoleId, String characterId, SkinnerGear skinnerGear) {
        System.out.println("Setting SkinnerGear for Character: " + characterId);
        return gameRoleService.setSkinnerGear(gameRoleId, characterId, skinnerGear);
    }

    public Character setWeapons(String gameRoleId, String characterId, List<String> weapons) {
        System.out.println("Setting Weapons for Character: " + characterId);
        return gameRoleService.setWeapons(gameRoleId, characterId, weapons);
    }

    public Character setWorkspace(String gameRoleId, String characterId, Workspace workspace) {
        System.out.println("Setting Workspace for Character: " + characterId);
        return gameRoleService.setWorkspace(gameRoleId, characterId, workspace);
    }

    // ------------------------------------------ Setting Vehicles ---------------------------------------- //

    public Character setVehicleCount(String gameRoleId, String characterId, int vehicleCount) {
        System.out.println("Setting vehicleCount for Character: " + characterId);
        return gameRoleService.setVehicleCount(gameRoleId, characterId, vehicleCount);
    }

    public Character setBattleVehicleCount(String gameRoleId, String characterId, int battleVehicleCount) {
        System.out.println("Setting battleVehicleCount for Character: " + characterId);
        return gameRoleService.setBattleVehicleCount(gameRoleId, characterId, battleVehicleCount);
    }

    public Character setVehicle(String gameRoleId, String characterId, Vehicle vehicle) {
        System.out.println("Setting Vehicle for Character: " + characterId);
        return gameRoleService.setVehicle(gameRoleId, characterId, vehicle);
    }

    public Character setBattleVehicle(String gameRoleId, String characterId, BattleVehicle battleVehicle) {
        System.out.println("Setting BattleVehicle for Character: " + characterId);
        return gameRoleService.setBattleVehicle(gameRoleId, characterId, battleVehicle);
    }


    // ------------------------------------- Adjusting from PlaybookPanel ----------------------------------- //

    public Character adjustCharacterHx(String gameRoleId, String characterId, HxStat hxStat) {
        System.out.println("Adjusting Hx for Character: " + characterId);
        return gameRoleService.adjustCharacterHx(gameRoleId, characterId, hxStat);
    }

    public Character setCharacterHarm(String gameRoleId, String characterId, CharacterHarm harm) {
        System.out.println("Setting harm for Character: " + characterId);
        return gameRoleService.setCharacterHarm(gameRoleId, characterId, harm);
    }

    public Character toggleStatHighlight(String gameRoleId, String characterId, StatType stat) {
        System.out.println("Setting harm for Character: " + characterId);
        return gameRoleService.toggleStatHighlight(gameRoleId, characterId, stat);
    }

    public Character setCharacterBarter(String gameRoleId, String characterId, int amount) {
        System.out.println("Setting barter for Character: " + characterId);
        return gameRoleService.setCharacterBarter(gameRoleId, characterId, amount);
    }

    public Character setHoldingBarter(String gameRoleId, String characterId, int amount) {
        System.out.println("Setting Holding barter for Character: " + characterId);
        return gameRoleService.setHoldingBarter(gameRoleId, characterId, amount);
    }

    public Character updateFollowers(String gameRoleId, String characterId, int barter, int followers, String description) {
        System.out.println("Updating Followers for Character: " + characterId);
        return gameRoleService.updateFollowers(gameRoleId, characterId, barter, followers, description);
    }

    public Character addProject(String gameRoleId, String characterId, Project project) {
        System.out.println("Adding Project for Character: " + characterId);
        return gameRoleService.addProject(gameRoleId, characterId, project);
    }

    public Character removeProject(String gameRoleId, String characterId, Project project) {
        System.out.println("Removing Project for Character: " + characterId);
        return gameRoleService.removeProject(gameRoleId, characterId, project);
    }

    // ------------------------------------------ Move Categories --------------------------------------- //

    public Game performPrintMove(String gameId, String gameroleId, String characterId, String moveId, boolean isGangMove) {
        System.out.println("Performing print move for Character: " + characterId);
        return gameService.performPrintMove(gameId, gameroleId, characterId, moveId, isGangMove);
    }

    public Game performBarterMove(String gameId, String gameroleId, String characterId, String moveId, int barter) {
        System.out.println("Performing barter move for Character: " + characterId);
        return gameService.performBarterMove(gameId, gameroleId, characterId, moveId, barter);
    }

    public Game performStockMove(String gameId, String gameroleId, String characterId, String moveName, int stockSpent) {
        System.out.println("Performing SPEED RECOVERY OF SOMEONE move for Character: " + characterId);
        return gameService.performStockMove(gameId, gameroleId, characterId, moveName, stockSpent);
    }

    // ------------------------------------------ Roll Move Categories --------------------------------------- //

    public Game performStatRollMove(String gameId, String gameroleId, String characterId, String moveId, boolean isGangMove) {
        System.out.println("Performing stat roll move for Character: " + characterId);
        return gameService.performStatRollMove(gameId, gameroleId, characterId, moveId, isGangMove);
    }

    public Game performSpeedRollMove(String gameId, String gameroleId, String characterId, String moveId, int modifier) {
        System.out.println("Performing stat roll move for Character: " + characterId);
        return gameService.performSpeedRollMove(gameId, gameroleId, characterId, moveId, modifier);
    }

    // ------------------------------------------ Specific Moves --------------------------------------- //

    public Game performWealthMove(String gameId, String gameroleId, String characterId) {
        System.out.println("Performing Wealth roll move for Character: " + characterId);
        return gameService.performWealthMove(gameId, gameroleId, characterId);
    }

    public Game performFortunesMove(String gameId, String gameroleId, String characterId) {
        System.out.println("Performing Fortunes roll move for Character: " + characterId);
        return gameService.performFortunesMove(gameId, gameroleId, characterId);
    }

    public Game performHelpOrInterfereMove(String gameId, String gameroleId, String characterId, String moveId, String targetId) {
        System.out.println("Performing hx roll move for Character: " + characterId);
        return gameService.performHelpOrInterfereMove(gameId, gameroleId, characterId, moveId, targetId);
    }

    public Game performMakeWantKnownMove(String gameId, String gameroleId, String characterId, String moveId, int barter) {
        System.out.println("Performing barter roll move for Character: " + characterId);
        return gameService.performMakeWantKnownMove(gameId, gameroleId, characterId, moveId, barter);
    }

    public Game performSufferHarmMove(String gameId, String gameroleId, String characterId, String moveId, int harm) {
        System.out.println("Performing SUFFER HARM move for Character: " + characterId);
        return gameService.performSufferHarmMove(gameId, gameroleId, characterId, moveId, harm);
    }

    public Game performSufferVHarmMove(String gameId, String gameroleId, String characterId, int harm) {
        System.out.println("Performing SUFFER V-HARM move for Character: " + characterId);
        return gameService.performSufferVHarmMove(gameId, gameroleId, characterId, harm);
    }

    public Game performInflictHarmMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, int harm) {
        System.out.println("Performing INFLICT HARM ON PC move for Character: " + characterId);
        return gameService.performInflictHarmMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId, harm);
    }

    public Game performHealHarmMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, int harm) {
        System.out.println("Performing HEAL PC HARM move for Character: " + characterId);
        return gameService.performHealHarmMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId, harm);
    }

    public Game performAngelSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId) {
        System.out.println("Performing ANGEL SPECIAL move for Character: " + characterId);
        return gameService.performAngelSpecialMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId);
    }

    public Game performChopperSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, int hxChange) {
        System.out.println("Performing CHOPPER SPECIAL move for Character: " + characterId);
        return gameService.performChopperSpecialMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId, hxChange);
    }

    public Game performGunluggerSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, boolean addPlus1Forward) {
        System.out.println("Performing GUNLUGGER SPECIAL move for Character: " + characterId);
        return gameService.performGunluggerSpecialMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId, addPlus1Forward);
    }

    public Game performHocusSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId) {
        System.out.println("Performing HOCUS SPECIAL move for Character: " + characterId);
        return gameService.performHocusSpecialMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId);
    }

    public Game performSkinnerSpecialMove(String gameId,
                                          String gameroleId,
                                          String otherGameroleId,
                                          String characterId,
                                          String otherCharacterId,
                                          boolean plus1ForUser,
                                          boolean plus1ForOther) {
        System.out.println("Performing SKINNER SPECIAL move for Character: " + characterId);
        return gameService.performSkinnerSpecialMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId, plus1ForUser, plus1ForOther);
    }



    public Game performStabilizeAndHealMove(String gameId, String gameroleId, String characterId, int stockSpent) {
        System.out.println("Performing SUFFER HARM move for Character: " + characterId);
        return gameService.performStabilizeAndHealMove(gameId, gameroleId, characterId, stockSpent);
    }

    public Game performJustGiveMotivationMove(String gameId, String gameroleId, String characterId, String targetId) {
        System.out.println("Performing SUFFER HARM move for Character: " + characterId);
        return gameService.performJustGiveMotivationMove(gameId, gameroleId, characterId, targetId);
    }

    // ------------------------------------------ Other --------------------------------------- //

    public Game spendHold(String gameId, String gameroleId, String characterId, Hold hold) {
        System.out.println("Spending hold for Character: " + characterId);
        return gameService.spendHold(gameId, gameroleId, characterId, hold);
    }

    public Character removeHold(String gameRoleId, String characterId, Hold hold) {
        System.out.println("Removing Hold for Character: " + characterId);
        return gameRoleService.removeHold(gameRoleId, characterId, hold);
    }

}
