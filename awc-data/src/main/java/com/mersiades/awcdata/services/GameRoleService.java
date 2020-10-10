package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;

import java.util.List;

public interface GameRoleService extends CrudService<GameRole, Long> {
    List<GameRole> findAllByUser(User user);
}
