package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;

public interface GameService extends CrudService<Game, Long> {
    GameRole findGameRoleByUserId(Long id);
}
