package com.mersiades.awcdata.services;

import com.mersiades.awccontent.enums.LookType;
import com.mersiades.awccontent.enums.PlaybookType;
import com.mersiades.awccontent.enums.StatType;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    Character setHolding(String gameRoleId, String characterId, Holding holding, int vehicleCount, int battleVehicleCount);

    Character setFollowers(String gameRoleId, String characterId, Followers followers);

    Character setWeapons(String gameRoleId, String characterId, List<String> weapons);

    Character setSkinnerGear(String gameRoleId, String characterId, SkinnerGear skinnerGear);

    Character setCharacterHx(String gameRoleId, String characterId, List<HxStat> hxStats);

    Character adjustCharacterHx(String gameRoleId, String characterId, String hxId, int value);

    Character setCharacterBarter(String gameRoleId, String characterId, int amount);

    Character setHoldingBarter(String gameRoleId, String characterId, int amount);

    Character updateFollowers(String gameRoleId, String characterId, int barter, int followers, String description);

    Character setCharacterHarm(String gameRoleId, String characterId, CharacterHarm harm);

    Character toggleStatHighlight(String gameRoleId, String characterId, StatType stat);

    Character finishCharacterCreation(String gameRoleId, String characterId);

    Character setVehicleCount(String gameRoleId, String characterId, int vehicleCount);

    Character setBattleVehicleCount(String gameRoleId, String characterId, int battleVehicleCount);

    Character setVehicle(String gameRoleId, String characterId, Vehicle vehicle);

    Character setBattleVehicle(String gameRoleId, String characterId, BattleVehicle battleVehicle);

    Character setGang(String gameRoleId, String characterId, Gang gang);

    Character setEstablishment(String gameRoleId, String characterId, Establishment establishment);

    Character setWorkspace(String gameRoleId, String characterId, Workspace workspace);

    Mono<GameRole> addThreat(String gameRoleId, Threat threat);

    Mono<GameRole> addNpc(String gameRoleId, Npc npc);

}
