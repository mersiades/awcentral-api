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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class Mutation implements GraphQLMutationResolver {

    private final GameService gameService;
    private final GameRoleService gameRoleService;

    public Mutation(GameService gameService, GameRoleService gameRoleService) {
        this.gameService = gameService;
        this.gameRoleService = gameRoleService;
    }

    // ---------------------------------------------- Game-related -------------------------------------------- //
    public Game createGame(String userId, String displayName, String email, String name) throws Exception {
        log.info("Creating Game for User: " + userId);
        return gameService.createGameWithMC(userId, displayName, email, name);
    }

    public Game setGameName(String gameId, String name)  {
        log.info("Setting name for Game: " + gameId);
        return gameService.setGameName(gameId, name);
    }

    public Game addInvitee(String gameId, String email) {
        log.info("Adding invitee to Game: " + gameId);
        return gameService.addInvitee(gameId, email);
    }

    public Game removeInvitee(String gameId, String email) {
        log.info("Removing invitee from Game: " + gameId);
        return gameService.removeInvitee(gameId, email);
    }

    public Game removePlayer(String gameId, String playerId) {
        log.info("Removing player from Game: " + gameId);
        return gameService.removePlayer(gameId, playerId);
    }

    public Game addCommsApp(String gameId, String app) {
        log.info("Adding comms app to Game: " + gameId);
        return gameService.addCommsApp(gameId, app);
    }

    public Game addCommsUrl(String gameId, String url) {
        log.info("Adding comms url to Game: " + gameId);
        return gameService.addCommsUrl(gameId, url);
    }

    public Game deleteGame(String gameId) {
        log.info("Deleting Game with id: " + gameId);
        return gameService.findAndDeleteById(gameId);
    }

    public Game addUserToGame(String gameId, String userId, String displayName, String email) throws Exception {
        log.info("Adding User to Game: " + gameId);
        return gameService.addUserToGame(gameId, userId, displayName, email);
    }

    // ---------------------------------------------- MC stuff -------------------------------------------- //

    public Game finishPreGame(String gameId) {
        log.info("Finishing pre-game for Game with id: " + gameId);
        return gameService.finishPreGame(gameId);
    }

    public Game closeFirstSession(String gameId) {
        log.info("Closing first session for Game with id: " + gameId);
        return gameService.closeFirstSession(gameId);
    }

    public GameRole addThreat(String gameRoleId, Threat threat) {
        log.info("Adding Threat for GameRole with id: " + gameRoleId);
        return gameRoleService.addThreat(gameRoleId, threat);
    }

    public GameRole addNpc(String gameRoleId, Npc npc) {
        log.info("Adding Npc for GameRole with id: " + gameRoleId);
        return gameRoleService.addNpc(gameRoleId, npc);
    }

    // ------------------------------------ Creating and editing characters ---------------------------------- //

    public Character createCharacter(String gameRoleId) {
        log.info("Creating Character for for GameRole: " + gameRoleId);
        return gameRoleService.addNewCharacter(gameRoleId);
    }

    public Character setCharacterPlaybook(String gameRoleId, String characterId, PlaybookType playbookType) {
        log.info("Setting Playbook for for Character: " + characterId);
        return gameRoleService.setCharacterPlaybook(gameRoleId, characterId, playbookType);
    }

    public Character setCharacterName(String gameRoleId, String characterId, String name) {
        log.info("Setting name for for Character: " + characterId);
        return gameRoleService.setCharacterName(gameRoleId, characterId, name);
    }

    public Character setCharacterLook(String gameRoleId, String characterId, Look look) {
        log.info("Setting a Look for for Character: " + characterId);
        return gameRoleService.setCharacterLook(gameRoleId, characterId, look);
    }

    public Character setCharacterStats(String gameRoleId, String characterId, String statsOptionId) {
        log.info("Setting CharacterStats for Character: " + characterId);
        return gameRoleService.setCharacterStats(gameRoleId, characterId, statsOptionId);
    }

    public Character setCharacterGear(String gameRoleId, String characterId, List<String> gear) {
        log.info("Setting CharacterStats for Character: " + characterId);
        return gameRoleService.setCharacterGear(gameRoleId, characterId, gear);
    }

    public Character setCharacterMoves(String gameRoleId, String characterId, List<String> moveIds) {
        log.info("Setting Moves for Character: " + characterId);
        return gameRoleService.setCharacterMoves(gameRoleId, characterId, moveIds);
    }

    public Character setCharacterHx(String gameRoleId, String characterId, List<HxStat> hxStats) {
        log.info("Setting Hx for Character: " + characterId);
        return gameRoleService.setCharacterHx(gameRoleId, characterId, hxStats);
    }

    public Character finishCharacterCreation(String gameRoleId, String characterId) {
        log.info("Finishing character creation for Character: " + characterId);
        return gameRoleService.finishCharacterCreation(gameRoleId, characterId);
    }

    // --------------------------------------- Setting Playbook Uniques ------------------------------------- //

    public Character setAngelKit(String gameRoleId, String characterId, int stock, Boolean hasSupplier) {
        log.info("Setting AngelKit for Character: " + characterId);
        return gameRoleService.setAngelKit(gameRoleId, characterId, stock, hasSupplier);
    }

    public Character setBrainerGear(String gameRoleId, String characterId, List<String> brainerGear) {
        log.info("Setting setBrainerGear for Character: " + characterId);
        return gameRoleService.setBrainerGear(gameRoleId, characterId, brainerGear);
    }

    public Character setCustomWeapons(String gameRoleId, String characterId, List<String> weapons) {
        log.info("Setting CustomWeapons for Character: " + characterId);
        return gameRoleService.setCustomWeapons(gameRoleId, characterId, weapons);
    }

    public Character setEstablishment(String gameRoleId, String characterId, Establishment establishment) {
        log.info("Setting Establishment for Character: " + characterId);
        return gameRoleService.setEstablishment(gameRoleId, characterId, establishment);
    }

    public Character setFollowers(String gameRoleId, String characterId, Followers followers) {
        log.info("Setting Holding for Character: " + characterId);
        return gameRoleService.setFollowers(gameRoleId, characterId, followers);
    }

    public Character setGang(String gameRoleId, String characterId, Gang gang) {
        log.info("Setting Gang for Character: " + characterId);
        return gameRoleService.setGang(gameRoleId, characterId, gang);
    }

    public Character setHolding(String gameRoleId, String characterId, Holding holding, int vehicleCount, int battleVehicleCount) {
        log.info("Setting Holding for Character: " + characterId);
        return gameRoleService.setHolding(gameRoleId, characterId, holding, vehicleCount, battleVehicleCount);
    }

    public Character setSkinnerGear(String gameRoleId, String characterId, SkinnerGear skinnerGear) {
        log.info("Setting SkinnerGear for Character: " + characterId);
        return gameRoleService.setSkinnerGear(gameRoleId, characterId, skinnerGear);
    }

    public Character setWeapons(String gameRoleId, String characterId, List<String> weapons) {
        log.info("Setting Weapons for Character: " + characterId);
        return gameRoleService.setWeapons(gameRoleId, characterId, weapons);
    }

    public Character setWorkspace(String gameRoleId, String characterId, Workspace workspace) {
        log.info("Setting Workspace for Character: " + characterId);
        return gameRoleService.setWorkspace(gameRoleId, characterId, workspace);
    }

    // ------------------------------------------ Setting Vehicles ---------------------------------------- //

    public Character setVehicleCount(String gameRoleId, String characterId, int vehicleCount) {
        log.info("Setting vehicleCount for Character: " + characterId);
        return gameRoleService.setVehicleCount(gameRoleId, characterId, vehicleCount);
    }

    public Character setBattleVehicleCount(String gameRoleId, String characterId, int battleVehicleCount) {
        log.info("Setting battleVehicleCount for Character: " + characterId);
        return gameRoleService.setBattleVehicleCount(gameRoleId, characterId, battleVehicleCount);
    }

    public Character setVehicle(String gameRoleId, String characterId, Vehicle vehicle) {
        log.info("Setting Vehicle for Character: " + characterId);
        return gameRoleService.setVehicle(gameRoleId, characterId, vehicle);
    }

    public Character setBattleVehicle(String gameRoleId, String characterId, BattleVehicle battleVehicle) {
        log.info("Setting BattleVehicle for Character: " + characterId);
        return gameRoleService.setBattleVehicle(gameRoleId, characterId, battleVehicle);
    }


    // ------------------------------------- Adjusting from PlaybookPanel ----------------------------------- //

    public Character adjustCharacterHx(String gameRoleId, String characterId, HxStat hxStat) {
        log.info("Adjusting Hx for Character: " + characterId);
        return gameRoleService.adjustCharacterHx(gameRoleId, characterId, hxStat);
    }

    public Character setCharacterHarm(String gameRoleId, String characterId, CharacterHarm harm) {
        log.info("Setting harm for Character: " + characterId);
        return gameRoleService.setCharacterHarm(gameRoleId, characterId, harm);
    }

    public Character toggleStatHighlight(String gameRoleId, String characterId, StatType stat) {
        log.info("Setting harm for Character: " + characterId);
        return gameRoleService.toggleStatHighlight(gameRoleId, characterId, stat);
    }

    public Character setCharacterBarter(String gameRoleId, String characterId, int amount) {
        log.info("Setting barter for Character: " + characterId);
        return gameRoleService.setCharacterBarter(gameRoleId, characterId, amount);
    }

    public Character setHoldingBarter(String gameRoleId, String characterId, int amount) {
        log.info("Setting Holding barter for Character: " + characterId);
        return gameRoleService.setHoldingBarter(gameRoleId, characterId, amount);
    }

    public Character updateFollowers(String gameRoleId, String characterId, int barter, int followers, String description) {
        log.info("Updating Followers for Character: " + characterId);
        return gameRoleService.updateFollowers(gameRoleId, characterId, barter, followers, description);
    }

    public Character addProject(String gameRoleId, String characterId, Project project) {
        log.info("Adding Project for Character: " + characterId);
        return gameRoleService.addProject(gameRoleId, characterId, project);
    }

    public Character removeProject(String gameRoleId, String characterId, Project project) {
        log.info("Removing Project for Character: " + characterId);
        return gameRoleService.removeProject(gameRoleId, characterId, project);
    }

    public Character adjustImprovements(String gameRoleId, String characterId, List<String> improvementIDs, List<String> futureImprovementIDs) {
        log.info("Adjusting improvements for Character: " + characterId);
        return gameRoleService.adjustImprovements(gameRoleId, characterId, improvementIDs, futureImprovementIDs);
    }

    public Character spendExperience(String gameRoleId, String characterId) {
        log.info("Spending experience points for Character: " + characterId);
        return gameRoleService.spendExperience(gameRoleId, characterId);
    }

    // ------------------------------------------ Move Categories --------------------------------------- //

    public Game performPrintMove(String gameId, String gameroleId, String characterId, String moveId, boolean isGangMove) {
        log.info("Performing print move for Character: " + characterId);
        return gameService.performPrintMove(gameId, gameroleId, characterId, moveId, isGangMove);
    }

    public Game performBarterMove(String gameId, String gameroleId, String characterId, String moveId, int barter) {
        log.info("Performing barter move for Character: " + characterId);
        return gameService.performBarterMove(gameId, gameroleId, characterId, moveId, barter);
    }

    public Game performStockMove(String gameId, String gameroleId, String characterId, String moveName, int stockSpent) {
        log.info("Performing SPEED RECOVERY OF SOMEONE move for Character: " + characterId);
        return gameService.performStockMove(gameId, gameroleId, characterId, moveName, stockSpent);
    }

    // ------------------------------------------ Roll Move Categories --------------------------------------- //

    public Game performStatRollMove(String gameId, String gameroleId, String characterId, String moveId, boolean isGangMove) {
        log.info("Performing stat roll move for Character: " + characterId);
        return gameService.performStatRollMove(gameId, gameroleId, characterId, moveId, isGangMove);
    }

    public Game performSpeedRollMove(String gameId, String gameroleId, String characterId, String moveId, int modifier) {
        log.info("Performing stat roll move for Character: " + characterId);
        return gameService.performSpeedRollMove(gameId, gameroleId, characterId, moveId, modifier);
    }

    // ------------------------------------------ Specific Moves --------------------------------------- //

    public Game performWealthMove(String gameId, String gameroleId, String characterId) {
        log.info("Performing Wealth roll move for Character: " + characterId);
        return gameService.performWealthMove(gameId, gameroleId, characterId);
    }

    public Game performFortunesMove(String gameId, String gameroleId, String characterId) {
        log.info("Performing Fortunes roll move for Character: " + characterId);
        return gameService.performFortunesMove(gameId, gameroleId, characterId);
    }

    public Game performHelpOrInterfereMove(String gameId, String gameroleId, String characterId, String moveId, String targetId) {
        log.info("Performing hx roll move for Character: " + characterId);
        return gameService.performHelpOrInterfereMove(gameId, gameroleId, characterId, moveId, targetId);
    }

    public Game performMakeWantKnownMove(String gameId, String gameroleId, String characterId, String moveId, int barter) {
        log.info("Performing barter roll move for Character: " + characterId);
        return gameService.performMakeWantKnownMove(gameId, gameroleId, characterId, moveId, barter);
    }

    public Game performSufferHarmMove(String gameId, String gameroleId, String characterId, String moveId, int harm) {
        log.info("Performing SUFFER HARM move for Character: " + characterId);
        return gameService.performSufferHarmMove(gameId, gameroleId, characterId, moveId, harm);
    }

    public Game performSufferVHarmMove(String gameId, String gameroleId, String characterId, int harm) {
        log.info("Performing SUFFER V-HARM move for Character: " + characterId);
        return gameService.performSufferVHarmMove(gameId, gameroleId, characterId, harm);
    }

    public Game performInflictHarmMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, int harm) {
        log.info("Performing INFLICT HARM ON PC move for Character: " + characterId);
        return gameService.performInflictHarmMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId, harm);
    }

    public Game performHealHarmMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, int harm) {
        log.info("Performing HEAL PC HARM move for Character: " + characterId);
        return gameService.performHealHarmMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId, harm);
    }

    public Game performAngelSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId) {
        log.info("Performing ANGEL SPECIAL move for Character: " + characterId);
        return gameService.performAngelSpecialMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId);
    }

    public Game performChopperSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, int hxChange) {
        log.info("Performing CHOPPER SPECIAL move for Character: " + characterId);
        return gameService.performChopperSpecialMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId, hxChange);
    }

    public Game performGunluggerSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, boolean addPlus1Forward) {
        log.info("Performing GUNLUGGER SPECIAL move for Character: " + characterId);
        return gameService.performGunluggerSpecialMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId, addPlus1Forward);
    }

    public Game performHocusSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId) {
        log.info("Performing HOCUS SPECIAL move for Character: " + characterId);
        return gameService.performHocusSpecialMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId);
    }

    public Game performSkinnerSpecialMove(String gameId,
                                          String gameroleId,
                                          String otherGameroleId,
                                          String characterId,
                                          String otherCharacterId,
                                          boolean plus1ForUser,
                                          boolean plus1ForOther) {
        log.info("Performing SKINNER SPECIAL move for Character: " + characterId);
        return gameService.performSkinnerSpecialMove(gameId, gameroleId, otherGameroleId, characterId, otherCharacterId, plus1ForUser, plus1ForOther);
    }



    public Game performStabilizeAndHealMove(String gameId, String gameroleId, String characterId, int stockSpent) {
        log.info("Performing SUFFER HARM move for Character: " + characterId);
        return gameService.performStabilizeAndHealMove(gameId, gameroleId, characterId, stockSpent);
    }

    public Game performJustGiveMotivationMove(String gameId, String gameroleId, String characterId, String targetId) {
        log.info("Performing SUFFER HARM move for Character: " + characterId);
        return gameService.performJustGiveMotivationMove(gameId, gameroleId, characterId, targetId);
    }

    // ------------------------------------------ Other --------------------------------------- //

    public Game spendHold(String gameId, String gameroleId, String characterId, Hold hold) {
        log.info("Spending hold for Character: " + characterId);
        return gameService.spendHold(gameId, gameroleId, characterId, hold);
    }

    public Character removeHold(String gameRoleId, String characterId, Hold hold) {
        log.info("Removing Hold for Character: " + characterId);
        return gameRoleService.removeHold(gameRoleId, characterId, hold);
    }

    public Game playXCard(String gameId) {
        log.info("Playing X card in Game: " + gameId);
        return gameService.playXCard(gameId);
    }

}
