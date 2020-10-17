package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;

public interface GameService extends CrudService<Game, Long> {
    Game findByGameRoles(GameRole gameRole);

    Game createGameWithMC(String discordId, String name, String textChannelId, String voiceChannelId);

    Game deleteGameByTextChannelId(String textChannelId);
}
