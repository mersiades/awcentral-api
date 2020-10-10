package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;

import java.util.List;

public interface GameService extends CrudService<Game, Long> {
    List<Game> findAllByUsers(User user);

    Game findByGameRoles(GameRole gameRole);

    Game createGameWithMC(String discordId, String name, String textChannelId, String voiceChannelId);
}
