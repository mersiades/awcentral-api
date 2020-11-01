package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;

import java.util.List;

public interface GameRoleService extends CrudService<GameRole, String> {
    List<GameRole> findAllByUser(User user);

    List<GameRole> findAllByGame(Game game);

    GameRole findByGameIdAndUserId(String gameId, String userId);
}
