package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awccontent.enums.LookType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.enums.StatType;
import reactor.core.publisher.Flux;

import java.util.List;

public interface GameRoleService extends ReactiveCrudService<GameRole, String> {
    Flux<GameRole> findAllByUser(User user);

    Flux<GameRole> findAllByUserId(String userId);

    Character addNewCharacter(String gameRoleId);

    Character setCharacterPlaybook(String gameRoleId, String characterId, PlaybookType playbookType);

    Character setCharacterName(String gameRoleId, String characterId, String name);

    Character setCharacterLook(String gameRoleId, String characterId, String look, LookType category);

    Character setCharacterStats(String gameRoleId, String characterId, String statsOptionId);

    Character setCharacterGear(String gameRoleId, String characterId, List<String> gear);

    Character setBrainerGear(String gameRoleId, String characterId, List<String> brainerGear);

    Character setAngelKit(String gameRoleId, String characterId, int stock, Boolean hasSupplier);

    Character setCharacterMoves(String gameRoleId, String characterId, List<String> moveIds);

    Character setCustomWeapons(String gameRoleId, String characterId, List<String> weapons);

    Character setCharacterHx(String gameRoleId, String characterId, List<HxStat> hxStats);

    Character adjustCharacterHx(String gameRoleId, String characterId, String hxId, int value);

    Character setCharacterBarter(String gameRoleId, String characterId, int amount);

    Character setCharacterHarm(String gameRoleId, String characterId, CharacterHarm harm);

    Character toggleStatHighlight(String gameRoleId, String characterId, StatType stat);

    Character finishCharacterCreation(String gameRoleId, String characterId);

}
