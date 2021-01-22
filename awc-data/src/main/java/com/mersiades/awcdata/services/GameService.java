package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameService extends ReactiveCrudService<Game, String> {
    Mono<Game> findByGameRoles(GameRole gameRole);

    Game createGameWithMC(String userId, String displayName, String email, String name) throws Exception;

    Mono<Game> setGameName(String gameId, String name);

    Game addUserToGame(String gameId, String userId, String displayName, String email) throws Exception;

    Game findAndDeleteById(String gameId);

    Game addInvitee(String gameId, String email);

    Game addCommsApp(String gameId, String app);

    Game addCommsUrl(String gameId, String url);

    Game removeInvitee(String gameId, String email);

    Flux<Game> findAllByInvitee(String email);

    Mono<Game> finishPreGame(String gameId);

    Mono<Game> performPrintMove(String gameId, String gameroleId, String characterId, String moveId);

    Mono<Game> findByIdWithLimit(String gameId, Integer skip, Integer limit);

    Mono<Game> performStatRollMove(String gameId, String gameroleId, String characterId, String moveId);

    Mono<Game> performHxRollMove(String gameId, String gameroleId, String characterId, String moveId, String targetId);

    Mono<Game> performBarterMove(String gameId, String gameroleId, String characterId, String moveId, int barter);

    Mono<Game> performBarterRollMove(String gameId, String gameroleId, String characterId, String moveId, int barter);
}
