package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import reactor.core.publisher.Mono;

public interface GameService extends ReactiveCrudService<Game, String> {
    Mono<Game> findByGameRoles(GameRole gameRole);

    Game createGameWithMC(String discordId, String name, String textChannelId, String voiceChannelId);

    Mono<Game> deleteGameByTextChannelId(String textChannelId);

    Mono<Game> findGameByTextChannelId(String textChannelId);
}
