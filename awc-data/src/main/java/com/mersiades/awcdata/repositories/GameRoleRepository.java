package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface GameRoleRepository extends CrudRepository<GameRole, String> {
    Set<GameRole> findAllByUser(User user);

    Set<GameRole> findAllByGame(Game game);
}
