package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.GameRole;

import java.util.Set;

public interface GameRoleService extends CrudService<GameRole, Long> {

    Set<GameRole> findByUserId(Long id);
}
