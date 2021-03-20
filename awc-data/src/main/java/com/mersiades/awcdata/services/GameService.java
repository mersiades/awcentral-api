package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.Hold;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameService extends ReactiveCrudService<Game, String> {
    Mono<Game> findByGameRoles(GameRole gameRole);

    Game findAndDeleteById(String gameId);

    Flux<Game> findAllByInvitee(String email);

    // ---------------------------------------------- Game-related -------------------------------------------- //

    Game createGameWithMC(String userId, String displayName, String email, String name) throws Exception;

    Mono<Game> setGameName(String gameId, String name);

    Game addInvitee(String gameId, String email);

    Game removeInvitee(String gameId, String email);

    Game addCommsApp(String gameId, String app);

    Game addCommsUrl(String gameId, String url);

    Game addUserToGame(String gameId, String userId, String displayName, String email) throws Exception;

    // ---------------------------------------------- MC stuff -------------------------------------------- //

    Mono<Game> finishPreGame(String gameId);

    // ---------------------------------------------- Move categories -------------------------------------------- //

    Mono<Game> performPrintMove(String gameId, String gameroleId, String characterId, String moveId, boolean isGangMove);

    Mono<Game> performBarterMove(String gameId, String gameroleId, String characterId, String moveId, int barter);

    Mono<Game> performStockMove(String gameId, String gameroleId, String characterId, String moveName, int stockSpent);

    // ---------------------------------------------- Roll move categories -------------------------------------------- //

    Mono<Game> performStatRollMove(String gameId, String gameroleId, String characterId, String moveId, boolean isGangMove);

    Mono<Game> performSpeedRollMove(String gameId, String gameroleId, String characterId, String moveId, int modifier);

    // ---------------------------------------------- Specific moves -------------------------------------------- //

    Mono<Game> performWealthMove(String gameId, String gameroleId, String characterId);

    Mono<Game> performFortunesMove(String gameId, String gameroleId, String characterId);

    Mono<Game> performHelpOrInterfereMove(String gameId, String gameroleId, String characterId, String moveId, String targetId);

    Mono<Game> performMakeWantKnownMove(String gameId, String gameroleId, String characterId, String moveId, int barter);

    Mono<Game> performSufferHarmMove(String gameId, String gameroleId, String characterId, String moveId, int harm);

    Mono<Game> performInflictHarmMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, int harm);

    Mono<Game> performHealHarmMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, int harm);

    Mono<Game> performAngelSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId);

    Mono<Game> performChopperSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, int hxChange);

    Mono<Game> performGunluggerSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, boolean addPlus1Forward);

    Mono<Game> performHocusSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId);

    Mono<Game> performSkinnerSpecialMove(String gameId, String gameroleId, String otherGameroleId, String characterId, String otherCharacterId, boolean plus1ForUser, boolean plus1ForOther);

    Mono<Game> performStabilizeAndHealMove(String gameId, String gameroleId, String characterId, int stockSpent);

    Mono<Game> performJustGiveMotivationMove(String gameId, String gameroleId, String characterId, String targetId);

    // ---------------------------------------------- Other -------------------------------------------- //

    Mono<Game> spendHold(String gameId, String gameroleId, String characterId, Hold hold);


}
