package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import reactor.core.publisher.Mono;

public interface GameService extends ReactiveCrudService<Game, String> {
    Mono<Game> findByGameRoles(GameRole gameRole);

    Game createGameWithMC(String userId, String name) throws Exception;

    Mono<Game> findAndDeleteById(String gameId);

    Game addInvitee(String gameId, String email);

    Game removeInvitee(String gameId, String email);



}
