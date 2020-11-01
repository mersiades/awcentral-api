package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRoleRepository extends CrudRepository<GameRole, String> {
    List<GameRole> findAllByUser(User user);

    List<GameRole> findAllByGame(Game game);

    GameRole findByGameIdAndUserId(String gameId, String userId);
}
