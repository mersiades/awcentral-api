package com.mersiades.awcdata.services;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;

import java.util.List;

public interface GameRoleService extends CrudService<GameRole, String> {
    List<GameRole> findAllByUser(User user);

    Character addNewCharacter(String gameRoleId);

    Character setCharacterPlaybook(String gameRoleId, String characterId, Playbooks playbookType);
}
