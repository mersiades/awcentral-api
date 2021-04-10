package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.Hold;

import java.util.List;

public interface GameService extends CrudService<Game, String> {
    Game findByGameRoles(GameRole gameRole);

    Game findAndDeleteById(String gameId);

    List<Game> findAllByInvitee(String email);

    // ---------------------------------------------- Game-related -------------------------------------------- //

    Game createGameWithMC(String userId, String displayName, String email, String name) throws Exception;

    Game setGameName(String gameId, String name);

    Game addInvitee(String gameId, String email);

    Game removeInvitee(String gameId, String email);

    Game removePlayer(String gameId, String playerId);

    Game addCommsApp(String gameId, String app);

    Game addCommsUrl(String gameId, String url);

    Game addUserToGame(String gameId, String userId, String displayName, String email) throws Exception;

    // ---------------------------------------------- MC stuff -------------------------------------------- //

    Game finishPreGame(String gameId);

    Game closeFirstSession(String gameId);

    // ---------------------------------------------- Move categories -------------------------------------------- //

    Game performPrintMove(String gameId, String gameroleId, String characterId, String moveId, boolean isGangMove);

    Game performBarterMove(String gameId, String gameroleId, String characterId, String moveId, int barter);

    Game performStockMove(String gameId, String gameroleId, String characterId, String moveName, int stockSpent);

    // ---------------------------------------------- Roll move categories -------------------------------------------- //

    Game performStatRollMove(String gameId, String gameroleId, String characterId, String moveId, boolean isGangMove);

    Game performSpeedRollMove(String gameId, String gameroleId, String characterId, String moveId, int modifier);

    // ---------------------------------------------- Specific moves -------------------------------------------- //

    Game performWealthMove(String gameId, String gameroleId, String characterId);

    Game performFortunesMove(String gameId, String gameroleId, String characterId);

    Game performHelpOrInterfereMove(String gameId, String gameroleId, String characterId, String moveId, String targetId);

    Game performMakeWantKnownMove(String gameId, String gameroleId, String characterId, String moveId, int barter);

    Game performSufferHarmMove(String gameId, String gameroleId, String characterId, String moveId, int harm);

    Game performSufferVHarmMove(String gameId, String gameroleId, String characterId, int harm);

    Game performInflictHarmMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, int harm);

    Game performHealHarmMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, int harm);

    Game performAngelSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId);

    Game performChopperSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, int hxChange);

    Game performGunluggerSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, boolean addPlus1Forward);

    Game performHocusSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId);

    Game performSkinnerSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, boolean plus1ForUser, boolean plus1ForOther);

    Game performStabilizeAndHealMove(String gameId, String gameroleId, String characterId, int stockSpent);

    Game performJustGiveMotivationMove(String gameId, String gameroleId, String characterId, String targetId);

    // ---------------------------------------------- Other -------------------------------------------- //

    Game spendHold(String gameId, String gameroleId, String characterId, Hold hold);

    Game playXCard(String gameId);
}
