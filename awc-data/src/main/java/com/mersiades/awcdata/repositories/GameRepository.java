package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, String> {
    Game findByGameRoles(GameRole gameRole);

    void deleteGameByTextChannelId(String textChannelId);

    Game findGameByTextChannelId(String textChannelId);
}
