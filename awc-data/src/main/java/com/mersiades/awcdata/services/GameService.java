package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import reactor.core.publisher.Mono;

public interface GameService extends ReactiveCrudService<Game, String> {
    Mono<Game> findByGameRoles(GameRole gameRole);

    Game createGameWithMC(String discordId, String name);

    Mono<Game> deleteGameByTextChannelId(String textChannelId);

    Mono<Game> findGameByTextChannelId(String textChannelId);

    Mono<Game> appendChannels(String id, String textChannelId, String voiceChannelId);
}
