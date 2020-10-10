package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface GameRoleRepository extends CrudRepository<GameRole, Long> {
    Set<GameRole> findAllByUser(User user);
}
