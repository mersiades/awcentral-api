package com.mersiades.awcdata.services.impl;

import com.mersiades.awccontent.content.MovesContent;
import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.services.MoveService;
import com.mersiades.awccontent.services.PlaybookCreatorService;
import com.mersiades.awccontent.services.StatModifierService;
import com.mersiades.awccontent.services.StatsOptionService;
import com.mersiades.awcdata.enums.ThreatMapLocation;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.models.uniques.*;
import com.mersiades.awcdata.repositories.GameRoleRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mersiades.awccontent.content.LooksContent.*;
import static com.mersiades.awccontent.content.MovesContent.*;
import static com.mersiades.awccontent.content.PlaybookCreatorsContent.*;
import static com.mersiades.awccontent.content.StatOptionsContent.*;
import static com.mersiades.awccontent.enums.PlaybookType.BATTLEBABE;
import static com.mersiades.awccontent.enums.StatType.*;
import static com.mersiades.awccontent.enums.UniqueType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameRoleServiceImplTest {

    public static final String MOCK_GAMEROLE_ID = "mock-gamerole-id";
    public static final String MOCK_GAME_ID = "mock-game-id";
    public static final String MOCK_USER_ID = "mock-user-id";

    @Mock
    GameRoleRepository gameRoleRepository;

    @Mock
    CharacterService characterService;

    @Mock
    StatsOptionService statsOptionService;

    @Mock
    MoveService moveService;

    @Mock
    PlaybookCreatorService playbookCreatorService;

    @Mock
    StatModifierService statModifierService;

    GameRoleService gameRoleService;

    GameRole mockGameRole;
    GameRole mockGameRole2;
    User mockUser;
    Character mockCharacter;
    AngelKit mockAngelKit;
    PlaybookUniques mockPlaybookUnique;
    CharacterHarm mockCharacterHarm;
    CharacterStat mockCharacterStatCool;
    CharacterStat mockCharacterStatHard;
    CharacterStat mockCharacterStatHot;
    CharacterStat mockCharacterStatSharp;
    CharacterStat mockCharacterStatWeird;
    StatsBlock mockStatsBlock;
    BattleVehicle mockBattleVehicle;
    Vehicle mockVehicle;
    Character mockCharacter2;
    Character mockCharacter3;
    HxStat hxStat1;
    HxStat hxStat2;
    Hold mockHold;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Game mockGame1 = Game.builder().id(MOCK_GAME_ID).build();
        mockUser = User.builder().id(MOCK_USER_ID).build();
        mockCharacter = Character.builder().id(new ObjectId().toString()).build();
        mockGameRole = GameRole.builder().id(MOCK_GAMEROLE_ID).role(RoleType.MC).gameId(mockGame1.getId()).userId(mockUser.getId()).build();
        mockGame1.getGameRoles().add(mockGameRole);
        mockUser.getGameRoles().add(mockGameRole);
        gameRoleService = new GameRoleServiceImpl(gameRoleRepository, characterService, statsOptionService, moveService, playbookCreatorService, statModifierService);
        mockGameRole2 = new GameRole();

        mockAngelKit = AngelKit.builder()
                .id("mock-angel-kit-id")
                .hasSupplier(false)
                .build();

        mockPlaybookUnique = PlaybookUniques.builder()
                .id("mock-playbook-unique-id")
                .angelKit(mockAngelKit)
                .build();

        mockCharacterHarm = CharacterHarm.builder()
                .id("mock-character-harm-id")
                .value(3)
                .isStabilized(true)
                .build();

        mockCharacterStatCool = CharacterStat.builder().stat(COOL).value(1).build();
        mockCharacterStatHard = CharacterStat.builder().stat(StatType.HARD).value(1).build();
        mockCharacterStatHot = CharacterStat.builder().stat(HOT).value(1).build();
        mockCharacterStatSharp = CharacterStat.builder().stat(StatType.SHARP).value(1).build();
        mockCharacterStatWeird = CharacterStat.builder().stat(StatType.WEIRD).value(1).build();
        mockStatsBlock = StatsBlock.builder()
                .id("mock-stats-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCharacterStatCool,
                        mockCharacterStatHard,
                        mockCharacterStatHot,
                        mockCharacterStatSharp,
                        mockCharacterStatWeird))
                .build();

        mockBattleVehicle = BattleVehicle.builder().id("mock-battle-vehicle-id").build();
        mockVehicle = Vehicle.builder().id("mock-vehicle-id").build();

        mockCharacter2 = Character.builder()
                .id("mock-character-2-id")
                .name("Mock Character 2")
                .playbook(PlaybookType.DRIVER)
                .build();

        mockCharacter3 = Character.builder()
                .id("mock-character-3-id")
                .name("Mock Character 3")
                .playbook(PlaybookType.HARDHOLDER)
                .build();

        hxStat1 = HxStat.builder()
                .characterId(mockCharacter2.getId())
                .characterName(mockCharacter2.getName())
                .hxValue(1)
                .build();

        hxStat2 = HxStat.builder()
                .characterId(mockCharacter3.getId())
                .characterName(mockCharacter3.getName())
                .hxValue(-1)
                .build();

        mockHold = Hold.builder()
                .id("mock-hold-id")
                .rollResult(12)
                .moveDescription("When you read a...")
                .moveName("READ A PERSON")
                .build();
    }


    @Test
    void shouldFindAllGameRoles() {
        // Given
        when(gameRoleRepository.findAll()).thenReturn(List.of(mockGameRole, mockGameRole2));

        // When
        List<GameRole> gameRoles = gameRoleService.findAll();

        // Then
        assert gameRoles != null;
        assertEquals(2, gameRoles.size());
        verify(gameRoleRepository, times(1)).findAll();
    }

    @Test
    void shouldFindGameRoleById() {
        // Given
        when(gameRoleRepository.findById(anyString())).thenReturn(Optional.of(mockGameRole));

        // When
        GameRole gameRole = gameRoleService.findById(MOCK_GAMEROLE_ID);

        // Then
        assertNotNull(gameRole, "Null GameRole returned");
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(gameRoleRepository, never()).findAll();
    }

    @Test
    void shouldSaveGameRole() {
        // Given
        when(gameRoleRepository.save(any())).thenReturn(mockGameRole);

        // When
        GameRole savedGameRole = gameRoleService.save(mockGameRole);

        // Then
        assert savedGameRole != null;
        assertEquals(MOCK_GAMEROLE_ID, savedGameRole.getId());
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldSaveGameRoles() {
        // Given
        when(gameRoleRepository.saveAll(anyIterable()))
                .thenReturn(List.of(mockGameRole, mockGameRole2));

        // When
        List<GameRole> savedGameRoles = gameRoleService.saveAll(List.of(mockGameRole, mockGameRole2));

        // Then
        assert savedGameRoles != null;
        assertEquals(2, savedGameRoles.size());
        verify(gameRoleRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void shouldDeleteGameRole() {
        // When
        gameRoleService.delete(mockGameRole);

        // Then
        verify(gameRoleRepository, times(1)).delete(any(GameRole.class));
    }

    @Test
    void shouldDeleteGameRoleById() {
        // When
        gameRoleService.deleteById(MOCK_GAMEROLE_ID);

        // Then
        verify(gameRoleRepository, times(1)).deleteById(anyString());
    }

    // ---------------------------------------------- Game-related -------------------------------------------- //

    @Test
    void shouldFindAllGameRolesByUserId() {
        // Given
        Game mockGame2 = new Game();
        GameRole mockGameRole2 = GameRole.builder().id("mock-gamerole-id2").role(RoleType.MC).gameId(mockGame2.getId()).userId(mockUser.getId()).build();
        when(gameRoleRepository.findAllByUserId(anyString())).thenReturn(List.of(mockGameRole, mockGameRole2));

        // When
        List<GameRole> returnedGameRoles = gameRoleService.findAllByUserId(mockUser.getId());

        // Then
        assert returnedGameRoles != null;
        assertEquals(2, returnedGameRoles.size());
        verify(gameRoleRepository, times(1)).findAllByUserId(anyString());
    }

    // ---------------------------------------------- MC stuff -------------------------------------------- //

    @Test
    void shouldAddThreat() {
        // Given
        Threat mockThreat = Threat.builder().id("mock-threat-id").threatKind(ThreatType.BRUTE).build();
        when(gameRoleRepository.findById(anyString())).thenReturn(Optional.of(mockGameRole));
        when(gameRoleRepository.save(any())).thenReturn(mockGameRole);

        // When
        GameRole returnedGameRole = gameRoleService.addThreat(mockGameRole.getId(), mockThreat);

        // Then
        assert returnedGameRole != null;
        assertEquals(mockThreat.getId(), returnedGameRole.getThreats().stream().findFirst().orElseThrow().getId());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    // TODO: shouldUpdateThreat
    // TODO: shouldAddThreatIdIfNoId

    @Test
    void shouldAddNpc() {
        // Given
        Npc mockNpc = Npc.builder().id("mock-npc-id").build();
        when(gameRoleRepository.findById(anyString())).thenReturn(Optional.of(mockGameRole));
        when(gameRoleRepository.save(any())).thenReturn(mockGameRole);

        // When
        GameRole returnedGameRole = gameRoleService.addNpc(mockGameRole.getId(), mockNpc);

        // Then
        assert returnedGameRole != null;
        assertEquals(mockNpc.getId(), returnedGameRole.getNpcs().stream().findFirst().orElseThrow().getId());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    // TODO: shouldUpdateNpc
    // TODO: shouldAddNpcIdIfNoId

    // ------------------------------------ Creating and editing characters ---------------------------------- //
    @Test
    void shouldAddNewCharacterToGameRole() {
        // Given
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService.addNewCharacter(MOCK_GAMEROLE_ID);

        // Then
        assertNotNull(returnedCharacter, "Null Character returned");
        assertFalse(returnedCharacter.getHasCompletedCharacterCreation());
        assertFalse(returnedCharacter.getHasPlusOneForward());
        assertEquals(-1, returnedCharacter.getBarter());
        assertEquals(0, returnedCharacter.getExperience());
        assertEquals(0, returnedCharacter.getAllowedOtherPlaybookMoves());
        assertEquals(0, returnedCharacter.getHarm().getValue());
        assertEquals(ThreatMapLocation.CENTER, returnedCharacter.getMapPosition());
        verifyMockServices();
    }

    @Test
    void shouldSetCharacterPlaybook_Battlebabe() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(playbookCreatorService.findByPlaybookType(any(PlaybookType.class))).thenReturn(playbookCreatorBattlebabe);

        // When
        Character returnedCharacter = gameRoleService.setCharacterPlaybook(MOCK_GAMEROLE_ID, mockCharacter.getId(), BATTLEBABE);

        // Then
        assertEquals(BATTLEBABE, returnedCharacter.getPlaybook());
        assertEquals(playbookCreatorBattlebabe.getMoveChoiceCount(), returnedCharacter.getAllowedPlaybookMoves());
        assertEquals(CUSTOM_WEAPONS, returnedCharacter.getPlaybookUniques().getCustomWeapons().getUniqueType());
        verifyMockServices();
        verify(playbookCreatorService, times(1)).findByPlaybookType(any(PlaybookType.class));
    }

    @Test
    void shouldSetCharacterPlaybook_Brainer() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(playbookCreatorService.findByPlaybookType(any(PlaybookType.class))).thenReturn(playbookCreatorBrainer);

        // When
        Character returnedCharacter = gameRoleService.setCharacterPlaybook(MOCK_GAMEROLE_ID, mockCharacter.getId(), PlaybookType.BRAINER);

        // Then
        assertEquals(PlaybookType.BRAINER, returnedCharacter.getPlaybook());
        assertEquals(playbookCreatorBrainer.getMoveChoiceCount(), returnedCharacter.getAllowedPlaybookMoves());
        assertEquals(BRAINER_GEAR, returnedCharacter.getPlaybookUniques().getBrainerGear().getUniqueType());
        assertEquals(brainerGearCreator.getDefaultItemCount(), returnedCharacter.getPlaybookUniques().getBrainerGear().getAllowedItemsCount());
        assertEquals(0, returnedCharacter.getPlaybookUniques().getBrainerGear().getBrainerGear().size());
        verifyMockServices();
        verify(playbookCreatorService, times(1)).findByPlaybookType(any(PlaybookType.class));
    }

    @Test
    void shouldResetAndChangePlaybook() {
        // Given
        CharacterMove angelSpecialAsCM = CharacterMove.createFromMove(angelSpecial);
        CharacterMove sixthSenseAsCM = CharacterMove.createFromMove(sixthSense);
        CharacterMove infirmaryAsCM = CharacterMove.createFromMove(infirmary);
        CharacterMove professionalCompassionAsCM = CharacterMove.createFromMove(profCompassion);
        CharacterMove addVehicleAsCM = CharacterMove.createFromMove(addVehicle);
        CharacterMove retireAsCM = CharacterMove.createFromMove(retire);
        CharacterMove dieAsCM = CharacterMove.createFromMove(die);

        mockCharacter.setName("mock-name");
        mockCharacter.setPlaybook(PlaybookType.ANGEL);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockCharacter.setHasCompletedCharacterCreation(true);
        mockCharacter.setHasPlusOneForward(true);
        mockCharacter.setBarter(2);
        mockCharacter.setHarm(mockCharacterHarm);
        mockCharacter.setStatsBlock(mockStatsBlock);
        mockCharacter.setVehicleCount(1);
        mockCharacter.setBattleVehicleCount(1);
        mockCharacter.setExperience(3);
        mockCharacter.setAllowedImprovements(2);
        mockCharacter.setAllowedPlaybookMoves(4);
        mockCharacter.setAllowedOtherPlaybookMoves(1);
        mockCharacter.setIsDead(true);
        mockCharacter.setBattleVehicles(List.of(mockBattleVehicle));
        mockCharacter.setVehicles(List.of(mockVehicle));
        mockCharacter.setHxBlock(List.of(hxStat1, hxStat2));
        mockCharacter.setGear(List.of("item1", "item2"));
        mockCharacter.setLooks(List.of(lookBattlebabe1, lookBattlebabe2));
        mockCharacter.setCharacterMoves(List.of(angelSpecialAsCM, sixthSenseAsCM, infirmaryAsCM, professionalCompassionAsCM));
        mockCharacter.setImprovementMoves(List.of(addVehicleAsCM));
        mockCharacter.setFutureImprovementMoves(List.of(retireAsCM));
        mockCharacter.setDeathMoves(List.of(dieAsCM));
        mockCharacter.setHolds(List.of(mockHold));

        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(playbookCreatorService.findByPlaybookType(any(PlaybookType.class))).thenReturn(playbookCreatorBrainer);
        when(moveService.findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.DEFAULT_CHARACTER)).thenReturn(List.of(brainerSpecial));

        // When
        Character returnedCharacter = gameRoleService.setCharacterPlaybook(MOCK_GAMEROLE_ID, mockCharacter.getId(), PlaybookType.BRAINER);

        // Then
        assertNull(returnedCharacter.getName());
        assertEquals(PlaybookType.BRAINER, returnedCharacter.getPlaybook());
        assertFalse(returnedCharacter.getHasCompletedCharacterCreation());
        assertFalse(returnedCharacter.getHasPlusOneForward());
        assertEquals(-1, returnedCharacter.getBarter());
        assertEquals(0, returnedCharacter.getHarm().getValue());
        assertNull(returnedCharacter.getStatsBlock());
        assertEquals(0, returnedCharacter.getVehicleCount());
        assertEquals(0, returnedCharacter.getBattleVehicleCount());
        assertEquals(0, returnedCharacter.getExperience());
        assertEquals(0, returnedCharacter.getAllowedImprovements());
        assertEquals(playbookCreatorBrainer.getMoveChoiceCount(), returnedCharacter.getAllowedPlaybookMoves());
        assertEquals(0, returnedCharacter.getAllowedOtherPlaybookMoves());
        assertEquals(0, returnedCharacter.getBattleVehicles().size());
        assertEquals(0, returnedCharacter.getVehicles().size());
        assertEquals(0, returnedCharacter.getHxBlock().size());
        assertEquals(0, returnedCharacter.getGear().size());
        assertEquals(0, returnedCharacter.getLooks().size());
        assertNotEquals(0, returnedCharacter.getCharacterMoves().size()); // Should have at least one default CharacterMove set
        assertEquals(0, returnedCharacter.getImprovementMoves().size());
        assertEquals(0, returnedCharacter.getFutureImprovementMoves().size());
        assertEquals(0, returnedCharacter.getDeathMoves().size());
        assertEquals(0, returnedCharacter.getHolds().size());
        assertFalse(returnedCharacter.getIsDead());

        assertEquals(0, returnedCharacter.getPlaybookUniques().getBrainerGear().getBrainerGear().size());
        assertEquals(BRAINER_GEAR, returnedCharacter.getPlaybookUniques().getBrainerGear().getUniqueType());
        assertEquals(brainerGearCreator.getDefaultItemCount(), returnedCharacter.getPlaybookUniques().getBrainerGear().getAllowedItemsCount());

        verifyMockServices();
        verify(playbookCreatorService, times(1)).findByPlaybookType(any(PlaybookType.class));
        verify(moveService, times(1)).findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.DEFAULT_CHARACTER);
    }

    @Test
    void shouldSetPlaybook_onChangePlaybook_fromGunluggerToBattlebabe() {
        int initialBarter = 3;
        int initialVehicleCount = 1;
        int initialBattleVehicleCount = 1;
        int initialExperience = 27;
        int initialAllowedImprovements = 5;
        int initialAllowedOtherPlaybookMoves = 3;
        int initialAllowedPlaybookMoves = 0; // original optional count + no extras

        String initialName = "name from previous playbook";

        List<Look> initialLooks = List.of(lookGunlugger1, lookGunlugger6, lookGunlugger12, lookGunlugger18, lookGunlugger24);

        Holding mockHolding = Holding.builder().id(new ObjectId().toString()).build();
        Gang mockGang = Gang.builder().id(new ObjectId().toString()).build();
        BattleVehicle mockBattleVehicle = BattleVehicle.builder().id(new ObjectId().toString()).build();
        Vehicle mockVehicle = Vehicle.builder().id(new ObjectId().toString()).build();

        CharacterMove dangerousAndSexyAsCM = CharacterMove.createFromMove(dangerousAndSexy, true);
        CharacterMove packAlphaAsCM = CharacterMove.createFromMove(packAlpha, true);
        CharacterMove wealthAsCM = CharacterMove.createFromMove(wealth, true);
        List<CharacterMove> initialCharacterMoves = new ArrayList<>(List.of(packAlphaAsCM, wealthAsCM, dangerousAndSexyAsCM));

        CharacterMove addGangPackAlphaAsCM = CharacterMove.createFromMove(addGangPackAlpha, true);
        CharacterMove addHoldingAsCM = CharacterMove.createFromMove(addHolding, true);
        CharacterMove coolMax2AsCM = CharacterMove.createFromMove(coolMax2, true);
        CharacterMove sharpMax2AsCM = CharacterMove.createFromMove(sharpMax2, true);
        CharacterMove addOtherPBMove1AsCM = CharacterMove.createFromMove(addOtherPBMove1, true);
        List<CharacterMove> initialImprovementMoves = List.of(addGangPackAlphaAsCM, addHoldingAsCM, coolMax2AsCM, sharpMax2AsCM, addOtherPBMove1AsCM);

        CharacterMove deathChangePlaybookAsCM = CharacterMove.createFromMove(deathChangePlaybook, true);
        List<CharacterMove> initialDeathMoves = List.of(deathChangePlaybookAsCM);

        mockPlaybookUnique.setHolding(mockHolding);
        mockPlaybookUnique.setGang(mockGang);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        Character resetCharacter = Character.builder()
                .id(new ObjectId().toString())
                .name(initialName)
                .playbook(null)
                .playbookUniques(mockPlaybookUnique)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(true)
                .barter(initialBarter)
                .harm(mockCharacterHarm)
                .statsBlock(null)
                .vehicleCount(initialVehicleCount)
                .battleVehicleCount(initialBattleVehicleCount)
                .experience(initialExperience)
                .allowedPlaybookMoves(initialAllowedPlaybookMoves)
                .allowedImprovements(initialAllowedImprovements)
                .allowedOtherPlaybookMoves(initialAllowedOtherPlaybookMoves)
                .isDead(false)
                .mustChangePlaybook(true)
                .battleVehicles(List.of(mockBattleVehicle))
                .vehicles(List.of(mockVehicle))
                .gear(new ArrayList<>())
                .looks(initialLooks)
                .characterMoves(initialCharacterMoves)
                .improvementMoves(initialImprovementMoves)
                .deathMoves(initialDeathMoves)
                .holds(new ArrayList<>())
                .build();

        mockGameRole.getCharacters().add(resetCharacter);
        setupMockServices();
        when(statsOptionService.findById(anyString())).thenReturn(statsOptionBattlebabe1);
        when(statsOptionService.findAllByPlaybookType(BATTLEBABE)).thenReturn(List.of(statsOptionBattlebabe1));
        when(playbookCreatorService.findByPlaybookType(any(PlaybookType.class))).thenReturn(playbookCreatorBattlebabe);
        when(moveService.findAllByPlaybookAndKind(BATTLEBABE, MoveType.DEFAULT_CHARACTER)).thenReturn(List.of(battlebabeSpecial));


        // When
        Character returnedCharacter = gameRoleService.changePlaybook(mockGameRole.getId(),
                resetCharacter.getId(),
                BATTLEBABE);

        // Check old character properties have been maintained
        assertEquals(initialName, returnedCharacter.getName());
        assertEquals(initialBarter, returnedCharacter.getBarter());
        assertEquals(initialExperience, returnedCharacter.getExperience());
        assertEquals(initialVehicleCount, returnedCharacter.getVehicleCount());
        assertEquals(initialVehicleCount, returnedCharacter.getVehicles().size());
        assertEquals(initialBattleVehicleCount, returnedCharacter.getBattleVehicleCount());
        assertEquals(initialBattleVehicleCount, returnedCharacter.getBattleVehicles().size());
        assertEquals(initialLooks, returnedCharacter.getLooks());
        assertEquals(initialImprovementMoves, returnedCharacter.getImprovementMoves());
        assertEquals(initialDeathMoves, returnedCharacter.getDeathMoves());

        assertNotNull(returnedCharacter.getPlaybookUniques().getHolding());
        assertNotNull(returnedCharacter.getPlaybookUniques().getGang());
        assertNotNull(returnedCharacter.getHarm());

        assertTrue(returnedCharacter.getHasPlusOneForward());
        assertFalse(returnedCharacter.getIsDead());

        // Check the new character properties have been added
        assertEquals(BATTLEBABE, returnedCharacter.getPlaybook());
        assertNotNull(returnedCharacter.getPlaybookUniques().getCustomWeapons());
        assertFalse(returnedCharacter.getMustChangePlaybook());
        CharacterMove battlebabeSpecialAsCM = CharacterMove.createFromMove(battlebabeSpecial, true);
        initialCharacterMoves.add(battlebabeSpecialAsCM);
        assertEquals(initialCharacterMoves, returnedCharacter.getCharacterMoves());
        assertEquals(initialCharacterMoves.size(),
                returnedCharacter.getCharacterMoves().size());
        assertEquals( playbookCreatorBattlebabe.getMoveChoiceCount(),
                returnedCharacter.getAllowedPlaybookMoves());

        assertEquals(initialAllowedPlaybookMoves + initialAllowedOtherPlaybookMoves,
                returnedCharacter.getAllowedOtherPlaybookMoves());
        assertFalse(returnedCharacter.getHasCompletedCharacterCreation());
        // At this stage, a new StatsBlock has been assigned with the playbooks first StatsOption, with bonuses added
        // Cool has a +1 modifier, but since it is already at 3 it can't increase any more
        assertEquals(statsOptionBattlebabe1.getCOOL(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(COOL)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionBattlebabe1.getSHARP() + 1, returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(SHARP)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionBattlebabe1.getHOT(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(HOT)).findFirst().orElseThrow().getValue());

        verifyMockServices();
        verify(statsOptionService, times(1)).findById(anyString());
        verify(statsOptionService, times(1)).findAllByPlaybookType(BATTLEBABE);
        verify(playbookCreatorService, times(1)).findByPlaybookType(any(PlaybookType.class));
        verify(moveService, times(1)).findAllByPlaybookAndKind(BATTLEBABE, MoveType.DEFAULT_CHARACTER);
    }

    @Test
    void shouldSetPlaybook_onChangePlaybook_fromAngelToBattlebabe() {
        int initialBarter = 3;
        int initialVehicleCount = 0;
        int initialBattleVehicleCount = 0;
        int initialExperience = 12;
        int initialAllowedImprovements = 2;
        int initialAllowedOtherPlaybookMoves = 1; // 1 from improvement
        int initialAllowedPlaybookMoves = 3; // 2 original + 1 extra from improvement

        String initialName = "name from previous playbook";

        List<Look> initialLooks = List.of(lookAngel1, lookAngel6, lookAngel12, lookAngel18, lookAngel24);

        CharacterMove sixthSenseAsCM = CharacterMove.createFromMove(sixthSense, true);
        CharacterMove infirmaryAsCM = CharacterMove.createFromMove(infirmary, true);
        CharacterMove profCompassionAsCM = CharacterMove.createFromMove(profCompassion, true);
        CharacterMove dangerousAndSexyAsCM = CharacterMove.createFromMove(dangerousAndSexy, true);
        List<CharacterMove> initialCharacterMoves = new ArrayList<>(List.of(profCompassionAsCM, sixthSenseAsCM, infirmaryAsCM, dangerousAndSexyAsCM));

        CharacterMove addAngelMove1AsCM = CharacterMove.createFromMove(addAngelMove1, true);
        CharacterMove addOtherPBMove1AsCM = CharacterMove.createFromMove(addOtherPBMove1, true);
        List<CharacterMove> initialImprovementMoves = List.of(addAngelMove1AsCM, addOtherPBMove1AsCM);

        CharacterMove deathChangePlaybookAsCM = CharacterMove.createFromMove(deathChangePlaybook, true);
        List<CharacterMove> initialDeathMoves = List.of(deathChangePlaybookAsCM);

        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        Character resetCharacter = Character.builder()
                .id(new ObjectId().toString())
                .name(initialName)
                .playbook(null)
                .playbookUniques(mockPlaybookUnique)
                .hasCompletedCharacterCreation(true)
                .hasPlusOneForward(true)
                .barter(initialBarter)
                .harm(mockCharacterHarm)
                .statsBlock(null)
                .vehicleCount(initialVehicleCount)
                .battleVehicleCount(initialBattleVehicleCount)
                .experience(initialExperience)
                .allowedPlaybookMoves(initialAllowedPlaybookMoves)
                .allowedImprovements(initialAllowedImprovements)
                .allowedOtherPlaybookMoves(initialAllowedOtherPlaybookMoves)
                .isDead(false)
                .mustChangePlaybook(true)
                .gear(new ArrayList<>())
                .looks(initialLooks)
                .characterMoves(initialCharacterMoves)
                .improvementMoves(initialImprovementMoves)
                .deathMoves(initialDeathMoves)
                .holds(new ArrayList<>())
                .build();

        mockGameRole.getCharacters().add(resetCharacter);
        when(gameRoleRepository.findById(anyString())).thenReturn(Optional.of(mockGameRole));
        when(characterService.save(any())).thenReturn(resetCharacter);
        when(gameRoleRepository.save(any())).thenReturn(mockGameRole);
        when(statsOptionService.findById(anyString())).thenReturn(statsOptionBattlebabe1);
        when(statsOptionService.findAllByPlaybookType(BATTLEBABE)).thenReturn(List.of(statsOptionBattlebabe1));
        when(playbookCreatorService.findByPlaybookType(any(PlaybookType.class))).thenReturn(playbookCreatorBattlebabe);
        when(moveService.findAllByPlaybookAndKind(BATTLEBABE, MoveType.DEFAULT_CHARACTER)).thenReturn(List.of(battlebabeSpecial));


        // When
        Character returnedCharacter = gameRoleService.changePlaybook(mockGameRole.getId(),
                resetCharacter.getId(),
                BATTLEBABE);

        // Check old character properties have been maintained
        assertEquals(initialName, returnedCharacter.getName());
        assertEquals(initialBarter, returnedCharacter.getBarter());
        assertEquals(initialExperience, returnedCharacter.getExperience());
        assertEquals(initialVehicleCount, returnedCharacter.getVehicleCount());
        assertEquals(initialVehicleCount, returnedCharacter.getVehicles().size());
        assertEquals(initialBattleVehicleCount, returnedCharacter.getBattleVehicleCount());
        assertEquals(initialBattleVehicleCount, returnedCharacter.getBattleVehicles().size());
        assertEquals(initialLooks, returnedCharacter.getLooks());
        assertEquals(initialImprovementMoves, returnedCharacter.getImprovementMoves());
        assertEquals(initialDeathMoves, returnedCharacter.getDeathMoves());

        assertNotNull(returnedCharacter.getHarm());

        assertTrue(returnedCharacter.getHasPlusOneForward());
        assertFalse(returnedCharacter.getIsDead());

        // Check the new character properties have been added
        assertEquals(BATTLEBABE, returnedCharacter.getPlaybook());
        assertNotNull(returnedCharacter.getPlaybookUniques().getCustomWeapons());
        assertFalse(returnedCharacter.getMustChangePlaybook());
        CharacterMove battlebabeSpecialAsCM = CharacterMove.createFromMove(battlebabeSpecial, true);
        initialCharacterMoves.add(battlebabeSpecialAsCM);
        assertEquals(initialCharacterMoves, returnedCharacter.getCharacterMoves());
        assertEquals(initialCharacterMoves.size(),
                returnedCharacter.getCharacterMoves().size());
        assertEquals(playbookCreatorBattlebabe.getMoveChoiceCount(),
                returnedCharacter.getAllowedPlaybookMoves());

        assertEquals(initialAllowedPlaybookMoves + initialAllowedOtherPlaybookMoves,
                returnedCharacter.getAllowedOtherPlaybookMoves());
        assertFalse(returnedCharacter.getHasCompletedCharacterCreation());
        assertNotNull(returnedCharacter.getStatsBlock());

        verifyMockServices();
        verify(statsOptionService, times(1)).findById(anyString());
        verify(statsOptionService, times(1)).findAllByPlaybookType(BATTLEBABE);
        verify(playbookCreatorService, times(1)).findByPlaybookType(any(PlaybookType.class));
        verify(moveService, times(1)).findAllByPlaybookAndKind(BATTLEBABE, MoveType.DEFAULT_CHARACTER);
    }

    @Test
    void shouldSetCharacterName() {
        // Given
        String mockCharacterName = "Mock Character Name";
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService.setCharacterName(MOCK_GAMEROLE_ID, mockCharacter.getId(), mockCharacterName);

        // Then
        assertEquals(mockCharacterName, returnedCharacter.getName());
        verifyMockServices();
    }

    @Test
    void shouldSetNewCharacterLook() {
        // Given

        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService.setCharacterLook(MOCK_GAMEROLE_ID, mockCharacter.getId(), lookBattlebabe1);
        Optional<Look> lookOptional = returnedCharacter.getLookByCategory(LookType.GENDER);

        // Then
        assertTrue(lookOptional.isPresent());
        Look savedLook = lookOptional.get();
        assertEquals(lookBattlebabe1, savedLook);
        verifyMockServices();
    }

    @Test
    void shouldUpdateCharacterLook() {
        // Given
        Look mockLook = Look.builder()
                .id(lookAngel9.getId())
                .look("ugly face")
                .category(LookType.FACE)
                .build();
        mockCharacter.getLooks().add(lookAngel9);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService.setCharacterLook(MOCK_GAMEROLE_ID, mockCharacter.getId(), mockLook);

        Optional<Look> lookOptional = returnedCharacter.getLookByCategory(LookType.FACE);

        // Then
        assertTrue(lookOptional.isPresent());
        Look savedLook = lookOptional.get();
        assertEquals(mockLook, savedLook);
        verifyMockServices();
    }

    @Test
    void shouldAddNewCharacterStat() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(statsOptionService.findById(anyString())).thenReturn(statsOptionAngel1);

        // When
        Character returnedCharacter = gameRoleService.setCharacterStats(mockGameRole.getId(), mockCharacter.getId(), statsOptionAngel1.getId());

        // Then
        assertEquals(statsOptionAngel1.getCOOL(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(COOL)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel1.getHARD(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.HARD)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel1.getHOT(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(HOT)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel1.getSHARP(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.SHARP)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel1.getWEIRD(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.WEIRD)).findFirst().orElseThrow().getValue());

        verify(statsOptionService, times(1)).findById(anyString());
        verifyMockServices();
    }

    @Test
    void shouldUpdateCharacterStat() {
        // Given

        mockCharacter.setStatsBlock(mockStatsBlock);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(statsOptionService.findById(anyString())).thenReturn(statsOptionAngel2);

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterStats(mockGameRole.getId(), mockCharacter.getId(), statsOptionAngel2.getId());

        // Then
        assertEquals(statsOptionAngel2.getCOOL(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(COOL)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel2.getHARD(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.HARD)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel2.getHOT(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(HOT)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel2.getSHARP(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.SHARP)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel2.getWEIRD(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.WEIRD)).findFirst().orElseThrow().getValue());

        verify(statsOptionService, times(1)).findById(anyString());
        verifyMockServices();
    }

    @Test
    void shouldSetCharacterGear() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);

        String item1 = "A big knife";
        String item2 = "Mechanic's toolkit";

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterGear(mockGameRole.getId(), mockCharacter.getId(), List.of(item1, item2));

        // Then
        assertEquals(2, returnedCharacter.getGear().size());
        assertTrue(returnedCharacter.getGear().contains(item1));
        assertTrue(returnedCharacter.getGear().contains(item2));
        verifyMockServices();
    }

    @Test
    void shouldSetCharacterMoves_Angel() {
        // Given
        mockCharacter.setPlaybook(PlaybookType.ANGEL);
        mockGameRole.getCharacters().add(mockCharacter);

        addStatsBlockToCharacter(mockCharacter);

        when(moveService.findAllByName(anyList())).thenReturn(List.of(angelSpecial, sixthSense, infirmary));
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterMoves(mockGameRole.getId(), mockCharacter.getId(),
                        List.of(angelSpecial.getName(), sixthSense.getName(), infirmary.getName()));

        // Then
        assertEquals(3, returnedCharacter.getCharacterMoves().size());
        List<CharacterMove> returnedMoves = returnedCharacter.getCharacterMoves();

        returnedMoves.forEach(characterMove -> {
            assertTrue(characterMove.getName().equals(angelSpecial.getName()) ||
                    characterMove.getName().equals(sixthSense.getName()) ||
                    characterMove.getName().equals(infirmary.getName()));
        });
        verify(moveService, times(1)).findAllByName(
                anyList()
        );
        verifyMockServices();
    }

//    @Disabled // Fix this when converting the setCharacterMoves mutation to use moves names instead of ids
    @Test
    void shouldSetCharacterMoves_whenHaveGainedAdditionalMovesFromAddingUnique() {
        // Given
        // Brainer has gained a Holding and the Wealth move through an improvement
        mockCharacter.setPlaybook(PlaybookType.BRAINER);
        addPlaybookUniquesToCharacter(mockCharacter, BRAINER_GEAR);
        BrainerGear mockBrainerGear = BrainerGear.builder().id(new ObjectId().toString()).build();
        Holding mockHolding = Holding.builder().id(new ObjectId().toString()).build();
        mockCharacter.getPlaybookUniques().setBrainerGear(mockBrainerGear);
        mockCharacter.getPlaybookUniques().setHolding(mockHolding);
        // Brainer has the standard two CharacterMoves
        CharacterMove unnaturalLustAsCM = CharacterMove.createFromMove(unnaturalLust, true);
        CharacterMove brainReceptivityAsCM = CharacterMove.createFromMove(brainReceptivity, true);
        CharacterMove wealthAsCM = CharacterMove.createFromMove(wealth, true);
        mockCharacter.setCharacterMoves(List.of(unnaturalLustAsCM, brainReceptivityAsCM, wealthAsCM));
        // Brainer has the improvement for getting the Holding and Wealth move
        CharacterMove addHoldingAsCM = CharacterMove.createFromMove(addHolding, true);
        mockCharacter.setImprovementMoves(List.of(addHoldingAsCM));
        addStatsBlockToCharacter(mockCharacter);
        mockGameRole.getCharacters().add(mockCharacter);
        when(moveService.findAllByName(anyList())).thenReturn(List.of(unnaturalLust, brainReceptivity, wealth, brainScan));
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterMoves(mockGameRole.getId(), mockCharacter.getId(),
                        List.of(unnaturalLust.getName(), brainReceptivity.getName(), brainScan.getName(), wealth.getName()));

        // Then
        assertEquals(4, returnedCharacter.getCharacterMoves().size());
        List<CharacterMove> returnedMoves = returnedCharacter.getCharacterMoves();

        returnedMoves.forEach(characterMove -> {
            assertTrue(characterMove.getName().equals(unnaturalLust.getName()) ||
                    characterMove.getName().equals(brainReceptivity.getName()) ||
                    characterMove.getName().equals(wealth.getName()) ||
                    characterMove.getName().equals(brainScan.getName())
                    );
        });

        verify(moveService, times(1)).findAllByName(
                anyList()
        );
        verifyMockServices();
    }

    @Test
    void shouldSetCharacterMoves_Angel_withSomeMovesFromOtherPlaybooks() {
        // Given
        mockCharacter.setPlaybook(PlaybookType.ANGEL);
        mockGameRole.getCharacters().add(mockCharacter);

        addStatsBlockToCharacter(mockCharacter);

        when(moveService.findAllByName(anyList())).thenReturn(List.of(angelSpecial, bonefeel, infirmary));
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterMoves(mockGameRole.getId(), mockCharacter.getId(),
                        List.of(angelSpecial.getId(), bonefeel.getId(), infirmary.getId()));

        // Then
        assertEquals(3, returnedCharacter.getCharacterMoves().size());
        List<CharacterMove> returnedMoves = returnedCharacter.getCharacterMoves();

        returnedMoves.forEach(characterMove -> {
            assertTrue(characterMove.getName().equals(angelSpecial.getName()) ||
                    characterMove.getName().equals(bonefeel.getName()) ||
                    characterMove.getName().equals(infirmary.getName()));
        });
        verify(moveService, times(1)).findAllByName(
                anyList()
        );
        verifyMockServices();
    }

    @Test
    void shouldSetCharacterMoves_Gunlugger_withPreparedForTheInevitableMove() {
        // Given
        mockCharacter.setPlaybook(PlaybookType.GUNLUGGER);
        mockGameRole.getCharacters().add(mockCharacter);

        addStatsBlockToCharacter(mockCharacter);

        addPlaybookUniquesToCharacter(mockCharacter, UniqueType.WEAPONS);

        Weapons weapons = Weapons.builder()
                .id(new ObjectId().toString())
                .build();

        mockCharacter.getPlaybookUniques().setWeapons(weapons);

        when(moveService.findAllByName(anyList())).thenReturn(List.of(gunluggerSpecial,
                battleHardened,
                preparedForTheInevitable,
                battlefieldInstincts
        ));
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterMoves(mockGameRole.getId(), mockCharacter.getId(),
                        List.of(gunluggerSpecial.getId(),
                                battleHardened.getId(),
                                preparedForTheInevitable.getId(),
                                battlefieldInstincts.getId()));

        // Then
        assertEquals(4, returnedCharacter.getCharacterMoves().size());
        List<CharacterMove> returnedMoves = returnedCharacter.getCharacterMoves();

        returnedMoves.forEach(characterMove -> {
            assertTrue(characterMove.getName().equals(gunluggerSpecial.getName()) ||
                    characterMove.getName().equals(battleHardened.getName()) ||
                    characterMove.getName().equals(preparedForTheInevitable.getName()) ||
                    characterMove.getName().equals(battlefieldInstincts.getName()));
        });

        assertNotNull(returnedCharacter.getPlaybookUniques().getAngelKit());
        assertNotNull(returnedCharacter.getPlaybookUniques().getWeapons());

        verify(moveService, times(1)).findAllByName(
                anyList()
        );
        verifyMockServices();
    }

    @Test
    void shouldRemoveAngelKit_Gunlugger_whenRemovingPreparedForTheInevitableMove() {
        // Given
        mockCharacter.setPlaybook(PlaybookType.GUNLUGGER);
        mockGameRole.getCharacters().add(mockCharacter);

        addStatsBlockToCharacter(mockCharacter);

        addPlaybookUniquesToCharacter(mockCharacter, UniqueType.WEAPONS);

        Weapons weapons = Weapons.builder()
                .id(new ObjectId().toString())
                .build();

        AngelKit angelKit = AngelKit.builder().id(new ObjectId().toString()).build();

        mockCharacter.getPlaybookUniques().setWeapons(weapons);
        mockCharacter.getPlaybookUniques().setAngelKit(angelKit);

        CharacterMove preparedForInevitableAsCM = CharacterMove.createFromMove(preparedForTheInevitable);

        mockCharacter.setCharacterMoves(List.of(preparedForInevitableAsCM));

        when(moveService.findAllByName(anyList())).thenReturn(List.of(gunluggerSpecial,
                battleHardened,
                insanoLikeDrano,
                battlefieldInstincts
        ));
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterMoves(mockGameRole.getId(), mockCharacter.getId(),
                        List.of(gunluggerSpecial.getId(),
                                battleHardened.getId(),
                                insanoLikeDrano.getId(),
                                battlefieldInstincts.getId()));

        // Then
        assertEquals(4, returnedCharacter.getCharacterMoves().size());
        List<CharacterMove> returnedMoves = returnedCharacter.getCharacterMoves();

        returnedMoves.forEach(characterMove -> {
            assertTrue(characterMove.getName().equals(gunluggerSpecial.getName()) ||
                    characterMove.getName().equals(battleHardened.getName()) ||
                    characterMove.getName().equals(insanoLikeDrano.getName()) ||
                    characterMove.getName().equals(battlefieldInstincts.getName()));
        });

        assertNull(returnedCharacter.getPlaybookUniques().getAngelKit());
        assertNotNull(returnedCharacter.getPlaybookUniques().getWeapons());

        verify(moveService, times(1)).findAllByName(
                anyList()
        );
        verifyMockServices();
    }

    private void addPlaybookUniquesToCharacter(Character mockCharacter, UniqueType type) {
        PlaybookUniques playbookUniques = PlaybookUniques.builder()
                .id(new ObjectId().toString())
                .type(type)
                .build();

        mockCharacter.setPlaybookUniques(playbookUniques);
    }

    @Test
    void shouldSetCharacterHx() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);


        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterHx(mockGameRole.getId(), mockCharacter.getId(), List.of(hxStat1, hxStat2));

        // Then
        assertEquals(2, returnedCharacter.getHxBlock().size());
        assertTrue(returnedCharacter.getHxBlock().contains(hxStat1));
        assertTrue(returnedCharacter.getHxBlock().contains(hxStat2));
        verifyMockServices();
    }

    @Test
    void shouldFinishCharacterCreation() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .finishCharacterCreation(mockGameRole.getId(), mockCharacter.getId());

        // Then
        assertTrue(returnedCharacter.getHasCompletedCharacterCreation());
        verifyMockServices();

    }

    // --------------------------------------- Setting Playbook Uniques ------------------------------------- //

    @Test
    void shouldSetAngelKit() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        int stock = 6;
        Boolean hasSupplier = false;
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setAngelKit(mockGameRole.getId(), mockCharacter.getId(), stock, hasSupplier);

        // Then
        assertEquals(List.of(stabilizeAndHeal, speedTheRecoveryOfSomeone, reviveSomeone, treatAnNpc),
                returnedCharacter.getPlaybookUniques().getAngelKit().getAngelKitMoves());

        verifyMockServices();

    }

    @Test
    public void shouldSetBrainerGear() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);

        String item1 = "Weird glove";
        String item2 = "Funny hat";

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setBrainerGear(mockGameRole.getId(), mockCharacter.getId(), List.of(item1, item2));

        // Then
        assertEquals(2, returnedCharacter.getPlaybookUniques().getBrainerGear().getBrainerGear().size());
        assertTrue(returnedCharacter.getPlaybookUniques().getBrainerGear().getBrainerGear().contains(item1));
        assertTrue(returnedCharacter.getPlaybookUniques().getBrainerGear().getBrainerGear().contains(item2));
        verifyMockServices();
    }

    @Test
    void shouldSetCustomWeapons() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        String weapon1 = "Crossbow with scope";
        String weapon2 = "Ornate staff";

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setCustomWeapons(mockGameRole.getId(), mockCharacter.getId(), List.of(weapon1, weapon2));

        // Then
        assertEquals(2, returnedCharacter.getPlaybookUniques().getCustomWeapons().getWeapons().size());
        assertTrue(returnedCharacter.getPlaybookUniques().getCustomWeapons().getWeapons().contains(weapon1));
        assertTrue(returnedCharacter.getPlaybookUniques().getCustomWeapons().getWeapons().contains(weapon2));
        verifyMockServices();

    }

    @Test
    void shouldSetEstablishment() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        Establishment mockEstablishment = Establishment.builder().id("mock-establishment-id").build();

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setEstablishment(mockGameRole.getId(), mockCharacter.getId(), mockEstablishment);

        // Then
        assertEquals(mockEstablishment.getId(), returnedCharacter.getPlaybookUniques().getEstablishment().getId());
        verifyMockServices();
    }

    // TODO: shouldUpdateEstablishment

    @Test
    void shouldSetFollowers() {
        mockGameRole.getCharacters().add(mockCharacter);
        Followers mockFollowers = Followers.builder().id("mock-followers-id").build();

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setFollowers(mockGameRole.getId(), mockCharacter.getId(), mockFollowers);

        // Then
        assertEquals(mockFollowers.getId(), returnedCharacter.getPlaybookUniques().getFollowers().getId());
        verifyMockServices();
    }

    // TODO: shouldUpdateFollowers

    @Test
    void shouldSetGang() {
        mockGameRole.getCharacters().add(mockCharacter);
        Gang mockGang = Gang.builder().id("mock-gang-id").build();

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setGang(mockGameRole.getId(), mockCharacter.getId(), mockGang);

        // Then
        assertEquals(mockGang.getId(), returnedCharacter.getPlaybookUniques().getGang().getId());
        verifyMockServices();
    }

    // TODO: shouldUpdateGang

    @Test
    void shouldSetHolding() {
        mockGameRole.getCharacters().add(mockCharacter);
        Holding mockHolding = Holding.builder().id("mock-holding-id").build();

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setHolding(mockGameRole.getId(), mockCharacter.getId(), mockHolding, 7, 6);

        // Then
        assertEquals(mockHolding.getId(), returnedCharacter.getPlaybookUniques().getHolding().getId());
        assertEquals(7, returnedCharacter.getVehicleCount());
        assertEquals(6, returnedCharacter.getBattleVehicleCount());
        verifyMockServices();
    }

    // TODO: shouldUpdateHolding

    @Test
    void shouldSetSkinnerGear() {
        mockGameRole.getCharacters().add(mockCharacter);
        SkinnerGear mockSkinnerGear = SkinnerGear.builder().id("mock-skinner-gear-id").build();

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setSkinnerGear(mockGameRole.getId(), mockCharacter.getId(), mockSkinnerGear);

        // Then
        assertEquals(mockSkinnerGear.getId(), returnedCharacter.getPlaybookUniques().getSkinnerGear().getId());
        verifyMockServices();
    }

    // TODO: shouldUpdateSkinnerGear

    @Test
    void shouldSetWeapons() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        String weapon1 = "big-gun-1";
        String weapon2 = "serious-gun-1";
        String weapon3 = "serious-gun-2";
        String weapon4 = "backup-weapon-1";

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setWeapons(mockGameRole.getId(), mockCharacter.getId(), List.of(weapon1, weapon2, weapon3, weapon4));

        // Then
        assertEquals(4, returnedCharacter.getPlaybookUniques().getWeapons().getWeapons().size());
        assertTrue(returnedCharacter.getPlaybookUniques().getWeapons().getWeapons().contains(weapon1));
        assertTrue(returnedCharacter.getPlaybookUniques().getWeapons().getWeapons().contains(weapon2));
        assertTrue(returnedCharacter.getPlaybookUniques().getWeapons().getWeapons().contains(weapon3));
        assertTrue(returnedCharacter.getPlaybookUniques().getWeapons().getWeapons().contains(weapon4));
        verifyMockServices();
    }

    // TODO: shouldUpdateWeapons

    @Test
    void shouldSetWorkspace() {
        mockGameRole.getCharacters().add(mockCharacter);
        Workspace mockWorkspace = Workspace.builder().id("mock-workspace-id")
                .workspaceInstructions(workspaceCreator.getWorkspaceInstructions())
                .uniqueType(WORKSPACE)
                .build();

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setWorkspace(mockGameRole.getId(), mockCharacter.getId(), mockWorkspace);

        // Then
        assertEquals(mockWorkspace.getId(), returnedCharacter.getPlaybookUniques().getWorkspace().getId());
        assertEquals(workspaceCreator.getWorkspaceInstructions(), returnedCharacter.getPlaybookUniques().getWorkspace().getWorkspaceInstructions());
        assertEquals(WORKSPACE, returnedCharacter.getPlaybookUniques().getWorkspace().getUniqueType());
        verifyMockServices();
    }

    // TODO: shouldUpdateWorkspace

    @Test
    void shouldResolveEstablishmentInterest() {
        // Given
        String wantsInOnItName = "Rolfball";
        String oweForItName = "Gams";
        Establishment mockEstablishment = Establishment.builder()
                .id("mock-establishment-id")
                .wantsInOnIt(wantsInOnItName)
                .oweForIt(oweForItName)
                .wantsItGone("Been")
                .build();
        addPlaybookUniquesToCharacter(mockCharacter, ESTABLISHMENT);
        mockCharacter.getPlaybookUniques().setEstablishment(mockEstablishment);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .resolveEstablishmentInterest(mockGameRole.getId(), mockCharacter.getId(), oweForItName, wantsInOnItName, "");

        // Then
        assertEquals(mockEstablishment.getId(), returnedCharacter.getPlaybookUniques().getEstablishment().getId());
        assertEquals(wantsInOnItName, returnedCharacter.getPlaybookUniques().getEstablishment().getWantsInOnIt());
        assertEquals(oweForItName, returnedCharacter.getPlaybookUniques().getEstablishment().getOweForIt());
        assertNull(returnedCharacter.getPlaybookUniques().getEstablishment().getWantsItGone());

        verifyMockServices();
    }

    // ------------------------------------------ Setting Vehicles ---------------------------------------- //

    @Test
    void shouldSetVehicleCount() {
        mockGameRole.getCharacters().add(mockCharacter);
        int mockVehicleCount = 3;

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setVehicleCount(mockGameRole.getId(), mockCharacter.getId(), mockVehicleCount);

        // Then
        assertEquals(mockVehicleCount, returnedCharacter.getVehicleCount());
        verifyMockServices();
    }

    @Test
    void shouldSetBattleVehicleCount() {
        mockGameRole.getCharacters().add(mockCharacter);
        int mockBattleVehicleCount = 3;

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setBattleVehicleCount(mockGameRole.getId(), mockCharacter.getId(), mockBattleVehicleCount);

        // Then
        assertEquals(mockBattleVehicleCount, returnedCharacter.getBattleVehicleCount());
        verifyMockServices();
    }

    @Test
    void shouldSetVehicle() {
        mockGameRole.getCharacters().add(mockCharacter);


        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setVehicle(mockGameRole.getId(), mockCharacter.getId(), mockVehicle);

        // Then
        assertTrue(returnedCharacter.getVehicles().contains(mockVehicle));
        verifyMockServices();
    }

    // TODO: shouldUpdateVehicle

    @Test
    void shouldSetBattleVehicle() {
        mockGameRole.getCharacters().add(mockCharacter);

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setBattleVehicle(mockGameRole.getId(), mockCharacter.getId(), mockBattleVehicle);

        // Then
        assertTrue(returnedCharacter.getBattleVehicles().contains(mockBattleVehicle));
        verifyMockServices();
    }

    // TODO: shouldUpdateBattleVehicle

    // ------------------------------------- Adjusting from PlaybookPanel ----------------------------------- //

    @Test
    void shouldAdjustCharacterHx() {
        HxStat oldHxStat = HxStat.builder()
                .id("hx-stat-id")
                .hxValue(2)
                .characterName("Bob")
                .characterId("bob-id")
                .build();
        HxStat newHxStat = HxStat.builder()
                .id("hx-stat-id")
                .hxValue(3)
                .characterName("Bob")
                .characterId("bob-id")
                .build();
        mockCharacter.getHxBlock().add(oldHxStat);
        mockGameRole.getCharacters().add(mockCharacter);


        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .adjustCharacterHx(mockGameRole.getId(), mockCharacter.getId(), newHxStat);

        // Then
        assertEquals(newHxStat.getHxValue(),
                returnedCharacter.getHxBlock().stream()
                        .filter(hxStat -> hxStat.getCharacterId().equals(newHxStat.getCharacterId()))
                        .findFirst().orElseThrow().getHxValue());
        verifyMockServices();
    }

    @Test
    void shouldResetCharacterHxTo1AndIncrementExperience() {
        HxStat oldHxStat = HxStat.builder()
                .id("hx-stat-id")
                .hxValue(3)
                .characterName("Bob")
                .characterId("bob-id")
                .build();
        HxStat newHxStat = HxStat.builder()
                .id("hx-stat-id")
                .hxValue(4)
                .characterName("Bob")
                .characterId("bob-id")
                .build();
        mockCharacter.getHxBlock().add(oldHxStat);
        mockCharacter.setExperience(0);
        mockGameRole.getCharacters().add(mockCharacter);


        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .adjustCharacterHx(mockGameRole.getId(), mockCharacter.getId(), newHxStat);

        // Then
        assertEquals(1,
                returnedCharacter.getHxBlock().stream()
                        .filter(hxStat -> hxStat.getCharacterId().equals(newHxStat.getCharacterId()))
                        .findFirst().orElseThrow().getHxValue());
        assertEquals(1, returnedCharacter.getExperience());
        verifyMockServices();
    }

    @Test
    void shouldResetCharacterHxTo0AndIncrementExperience() {
        HxStat oldHxStat = HxStat.builder()
                .id("hx-stat-id")
                .hxValue(-2)
                .characterName("Bob")
                .characterId("bob-id")
                .build();
        HxStat newHxStat = HxStat.builder()
                .id("hx-stat-id")
                .hxValue(-3)
                .characterName("Bob")
                .characterId("bob-id")
                .build();
        mockCharacter.getHxBlock().add(oldHxStat);
        mockCharacter.setExperience(0);
        mockGameRole.getCharacters().add(mockCharacter);


        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .adjustCharacterHx(mockGameRole.getId(), mockCharacter.getId(), newHxStat);

        // Then
        assertEquals(0,
                returnedCharacter.getHxBlock().stream()
                        .filter(hxStat -> hxStat.getCharacterId().equals(newHxStat.getCharacterId()))
                        .findFirst().orElseThrow().getHxValue());
        assertEquals(1, returnedCharacter.getExperience());
        verifyMockServices();
    }

    @Test
    void shouldSetCharacterHarm() {
        mockGameRole.getCharacters().add(mockCharacter);

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterHarm(mockGameRole.getId(), mockCharacter.getId(), mockCharacterHarm);

        // Then
        assertEquals(mockCharacterHarm.getValue(), returnedCharacter.getHarm().getValue());
        assertEquals(mockCharacterHarm.getIsStabilized(), returnedCharacter.getHarm().getIsStabilized());
        verifyMockServices();
    }

    @Test
    void shouldToggleStatHighlight() {
        CharacterStat mockCool = CharacterStat.builder()
                .isHighlighted(false)
                .value(1)
                .stat(COOL)
                .build();
        CharacterStat mockHard = CharacterStat.builder()
                .isHighlighted(false)
                .value(1)
                .stat(StatType.HARD)
                .build();
        CharacterStat mockHot = CharacterStat.builder()
                .isHighlighted(false)
                .value(1)
                .stat(HOT)
                .build();
        CharacterStat mockSharp = CharacterStat.builder()
                .isHighlighted(false)
                .value(1)
                .stat(StatType.SHARP)
                .build();
        CharacterStat mockWeird = CharacterStat.builder()
                .isHighlighted(false)
                .value(1)
                .stat(StatType.WEIRD)
                .build();
        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stats-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCool, mockHard, mockHot, mockSharp, mockWeird))
                .build();
        mockCharacter.setStatsBlock(mockStatsBlock);
        mockGameRole.getCharacters().add(mockCharacter);

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .toggleStatHighlight(mockGameRole.getId(), mockCharacter.getId(), COOL);

        // Then
        assertTrue(returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(COOL))
                .findFirst().orElseThrow().getIsHighlighted());
        verifyMockServices();
    }

    @Test
    void shouldSetCharacterBarter() {
        mockGameRole.getCharacters().add(mockCharacter);
        int mockBarter = 3;

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterBarter(mockGameRole.getId(), mockCharacter.getId(), mockBarter);

        // Then
        assertEquals(mockBarter, returnedCharacter.getBarter());
        verifyMockServices();
    }

    @Test
    void shouldSetHoldingBarter() {
        Holding mockHolding = Holding.builder().id("mock-holding-id").barter(0).build();
        PlaybookUniques mockPlaybookUnique = PlaybookUniques.builder().holding(mockHolding).build();
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);


        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setHoldingBarter(mockGameRole.getId(), mockCharacter.getId(), 2);

        // Then
        assertEquals(2, returnedCharacter.getPlaybookUniques().getHolding().getBarter());
        verifyMockServices();
    }

    @Test
    void shouldUpdateFollowers() {
        Followers mockFollowers = Followers.builder()
                .id("mock-followers-id")
                .description("old description")
                .followers(100)
                .barter(0).build();
        PlaybookUniques mockPlaybookUnique = PlaybookUniques.builder().followers(mockFollowers).build();
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);

        String newDescription = "new description";
        int newFollowers = 50;
        int newBarter = 2;

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .updateFollowers(mockGameRole.getId(), mockCharacter.getId(), newBarter, newFollowers, newDescription);

        // Then
        assertEquals(newDescription, returnedCharacter.getPlaybookUniques().getFollowers().getDescription());
        assertEquals(newBarter, returnedCharacter.getPlaybookUniques().getFollowers().getBarter());
        assertEquals(newFollowers, returnedCharacter.getPlaybookUniques().getFollowers().getFollowers());
        verifyMockServices();
    }

    @Test
    void shouldAddProject() {
        Workspace mockWorkspace = Workspace.builder().id("mock-workspace-id").build();
        PlaybookUniques mockPlaybookUnique = PlaybookUniques.builder().workspace(mockWorkspace).build();
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        Project mockProject = Project.builder()
                .id("mock-project-id").build();

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .addProject(mockGameRole.getId(), mockCharacter.getId(), mockProject);

        // Then
        assertTrue(returnedCharacter.getPlaybookUniques().getWorkspace().getProjects().contains(mockProject));
        verifyMockServices();
    }

    @Test
    void shouldRemoveProject() {
        Project mockProject = Project.builder()
                .id("mock-project-id").build();
        Workspace mockWorkspace = Workspace.builder()
                .id("mock-workspace-id")
                .projects(List.of(mockProject))
                .build();
        PlaybookUniques mockPlaybookUnique = PlaybookUniques.builder().workspace(mockWorkspace).build();
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .removeProject(mockGameRole.getId(), mockCharacter.getId(), mockProject);

        // Then
        assertFalse(returnedCharacter.getPlaybookUniques().getWorkspace().getProjects().contains(mockProject));
        verifyMockServices();
    }

    @Test
    void shouldSpendPointsAndSetAllowedImprovementsTo1() {
        // Given
        int mockExperience = 5;
        mockCharacter.setExperience(mockExperience);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .spendExperience(mockGameRole.getId(), mockCharacter.getId());

        // Then
        assertEquals(0, returnedCharacter.getExperience());
        assertEquals(1, returnedCharacter.getAllowedImprovements());
        verifyMockServices();
    }

    @Test
    void shouldSpendPointsWithRemainderAndIncreaseAllowedImprovements() {
        // Given
        int mockExperience = 12;
        int mockAllowedImprovements = 2;
        mockCharacter.setExperience(mockExperience);
        mockCharacter.setAllowedImprovements(mockAllowedImprovements);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .spendExperience(mockGameRole.getId(), mockCharacter.getId());

        // Then
        assertEquals(2, returnedCharacter.getExperience());
        assertEquals(mockAllowedImprovements + 2, returnedCharacter.getAllowedImprovements());
        verifyMockServices();
    }

    @Test
    void shouldNotSpendImprovementPointsBecauseTooFew() {
        // Given
        int mockExperience = 3;
        mockCharacter.setExperience(mockExperience);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .spendExperience(mockGameRole.getId(), mockCharacter.getId());

        // Then
        assertEquals(mockExperience, returnedCharacter.getExperience());
        assertEquals(0, returnedCharacter.getAllowedImprovements());
        verifyMockServices();
    }

    @Test
    void shouldIncreaseStat_onAddCharacterImprovement() {
        // Given
        int mockSharpValue = 0;
        CharacterStat mockSharpStat = CharacterStat.builder()
                .id("mock-sharp-stat-id")
                .stat(StatType.SHARP)
                .value(mockSharpValue)
                .build();

        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stat-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockSharpStat))
                .build();
        mockCharacter.setStatsBlock(mockStatsBlock);
        mockCharacter.setAllowedImprovements(1);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.sharpMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.sharpMax2.getId()), List.of());

        // Then
        assertEquals(mockSharpValue + 1, returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.SHARP)).findFirst().orElseThrow().getValue()
        );
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(MovesContent.sharpMax2.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldDecreaseStat_onRemoveCharacterImprovement() {
        // Given
        int mockSharpValue = 2;
        CharacterStat mockSharpStat = CharacterStat.builder()
                .id("mock-sharp-stat-id")
                .stat(StatType.SHARP)
                .value(mockSharpValue)
                .build();

        CharacterStat mockCoolStat = CharacterStat.builder()
                .id("mock-cool-stat-id")
                .stat(COOL)
                .value(mockSharpValue)
                .build();

        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stat-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockSharpStat, mockCoolStat))
                .build();
        mockCharacter.setStatsBlock(mockStatsBlock);
        mockCharacter.setAllowedImprovements(1);
        CharacterMove mockSharpMax2CM = CharacterMove.createFromMove(MovesContent.sharpMax2);
        mockCharacter.setImprovementMoves(List.of(mockSharpMax2CM));
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertEquals(mockSharpValue - mockSharpMax2CM.getStatModifier().getModification(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.SHARP)).findFirst().orElseThrow().getValue()
        );
        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(MovesContent.sharpMax2.getName())));
        verifyMockServices();
    }

    @Test
    void shouldNotIncreaseStat_onAddCharacterImprovement_becauseAtMax() {
        // Given
        int mockSharpValue = 2;
        CharacterStat mockSharpStat = CharacterStat.builder()
                .id("mock-sharp-stat-id")
                .stat(StatType.SHARP)
                .value(mockSharpValue)
                .build();

        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stat-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockSharpStat))
                .build();
        mockCharacter.setStatsBlock(mockStatsBlock);
        mockCharacter.setAllowedImprovements(1);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.sharpMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.sharpMax2.getId()), List.of());

        // Then
        assertEquals(mockSharpValue, returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.SHARP)).findFirst().orElseThrow().getValue()
        );
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(MovesContent.sharpMax2.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldNotAdjustImprovements_ifTooManyIds() {
        // Given
        int mockSharpValue = 2;
        String mockImprovementId = "mock-sharp-max-2-id";
        CharacterStat mockSharpStat = CharacterStat.builder()
                .id("mock-sharp-stat-id")
                .stat(StatType.SHARP)
                .value(mockSharpValue)
                .build();

        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stat-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockSharpStat))
                .build();
        mockCharacter.setStatsBlock(mockStatsBlock);
        mockCharacter.setAllowedImprovements(0);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(mockImprovementId), List.of());

        // Then
        assertEquals(mockSharpValue, returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.SHARP)).findFirst().orElseThrow().getValue()
        );
        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(MovesContent.sharpMax2.getName())));
        verifyMockServices();
    }

    @Test
    void shouldIncreaseAllowedPlaybookMoves_by1_onAddCharacterImprovement() {
        // Given
        int initialAllowedPlaybookMoves = 2;
        mockCharacter.setAllowedImprovements(1);
        mockCharacter.setAllowedPlaybookMoves(initialAllowedPlaybookMoves);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.addAngelMove1);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.addAngelMove1.getId()), List.of());

        // Then
        assertEquals(initialAllowedPlaybookMoves + 1, returnedCharacter.getAllowedPlaybookMoves());
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(MovesContent.addAngelMove1.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldIncreaseAllowedPlaybookMoves_by2_onAddCharacterImprovement() {
        // Given
        int initialAllowedPlaybookMoves = 2;
        mockCharacter.setAllowedImprovements(2);
        mockCharacter.setAllowedPlaybookMoves(initialAllowedPlaybookMoves);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(addAngelMove1.getId())).thenReturn(addAngelMove1);
        when(moveService.findById(addAngelMove2.getId())).thenReturn(addAngelMove2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(addAngelMove1.getId(), addAngelMove2.getId()), List.of());

        // Then
        assertEquals(initialAllowedPlaybookMoves + 2, returnedCharacter.getAllowedPlaybookMoves());
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(addAngelMove1.getName())));
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(addAngelMove2.getName())));
        verifyMockServices();
        verify(moveService, times(2)).findById(anyString());
    }

    @Test
    void shouldDecreaseAllowedPlaybookMoves_onRemoveCharacterImprovement() {
        // Given
        int initialAllowedPlaybookMoves = 3;
        CharacterStat mockCoolStat = CharacterStat.builder()
                .id("mock-cool-stat-id")
                .stat(COOL)
                .value(2)
                .build();

        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stat-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCoolStat))
                .build();
        mockCharacter.setStatsBlock(mockStatsBlock);
        CharacterMove mockSixthSense = CharacterMove.createFromMove(sixthSense);
        CharacterMove mockInfirmary = CharacterMove.createFromMove(infirmary);
        CharacterMove mockProfCompassion = CharacterMove.createFromMove(profCompassion);
        CharacterMove mockAddAngelMoveAsCM = CharacterMove.createFromMove(MovesContent.addAngelMove1);
        mockCharacter.setAllowedImprovements(1);
        mockCharacter.setAllowedPlaybookMoves(initialAllowedPlaybookMoves);
        mockCharacter.setImprovementMoves(List.of(mockAddAngelMoveAsCM));
        mockCharacter.setCharacterMoves(List.of(mockSixthSense, mockInfirmary, mockProfCompassion));
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertEquals(initialAllowedPlaybookMoves - 1, returnedCharacter.getAllowedPlaybookMoves());
        assertEquals(initialAllowedPlaybookMoves - 1, returnedCharacter.getCharacterMoves().size());
        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(MovesContent.addAngelMove1.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldIncreaseAllowedOtherPlaybookMoves_onAddCharacterImprovement() {
        // Given
        int initialAllowedOtherPlaybookMoves = 0;
        mockCharacter.setAllowedImprovements(1);
        mockCharacter.setAllowedOtherPlaybookMoves(initialAllowedOtherPlaybookMoves);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.addOtherPBMove1);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.addOtherPBMove1.getId()), List.of());

        // Then
        assertEquals(initialAllowedOtherPlaybookMoves + 1, returnedCharacter.getAllowedOtherPlaybookMoves());
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(MovesContent.addOtherPBMove1.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldDecreaseAllowedOtherPlaybookMoves_onRemoveCharacterImprovement() {
        // Given
        int initialAllowedOtherPlaybookMoves = 1;
        CharacterStat mockCoolStat = CharacterStat.builder()
                .id("mock-cool-stat-id")
                .stat(COOL)
                .value(2)
                .build();

        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stat-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCoolStat))
                .build();
        mockCharacter.setStatsBlock(mockStatsBlock);
        CharacterMove mockAddOtherPBMoveAsCM = CharacterMove.createFromMove(MovesContent.addOtherPBMove1);
        mockCharacter.setAllowedImprovements(1);
        mockCharacter.setAllowedOtherPlaybookMoves(initialAllowedOtherPlaybookMoves);
        mockCharacter.setImprovementMoves(List.of(mockAddOtherPBMoveAsCM));
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertEquals(0, returnedCharacter.getAllowedOtherPlaybookMoves());
        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(MovesContent.addOtherPBMove1.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldAddSupplier_onAddCharacterImprovement() {
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        Character returnedCharacter = checkAdjustedUnique(adjustAngelUnique1);
        assertTrue(returnedCharacter.getPlaybookUniques().getAngelKit().isHasSupplier());
    }

    @Test
    void shouldRemoveSupplier_onRemoveCharacterImprovement() {
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        Character returnedCharacter = checkUnadjustedUnique(adjustAngelUnique1);
        assertFalse(returnedCharacter.getPlaybookUniques().getAngelKit().isHasSupplier());
    }

    @Test
    void shouldIncreaseBrainerGearItems_onAddCharacterImprovement() {
        addPlaybookUniquesToCharacter(mockCharacter, UniqueType.BRAINER_GEAR);
        BrainerGear brainerGear = BrainerGear.builder()
                .id(new ObjectId().toString())
                .allowedItemsCount(brainerGearCreator.getDefaultItemCount())
                .brainerGear(List.of("item 1", "item 2"))
                .build();
        mockCharacter.getPlaybookUniques().setBrainerGear(brainerGear);
        Character returnedCharacter = checkAdjustedUnique(adjustBrainerUnique1);
        assertEquals(brainerGearCreator.getDefaultItemCount() + 2,
                returnedCharacter.getPlaybookUniques().getBrainerGear().getAllowedItemsCount());
    }

    @Test
    void shouldDecreaseBrainerGearItems_onRemoveCharacterImprovement() {
        addPlaybookUniquesToCharacter(mockCharacter, UniqueType.BRAINER_GEAR);
        BrainerGear brainerGear = BrainerGear.builder()
                .id(new ObjectId().toString())
                .allowedItemsCount(4)
                .brainerGear(List.of("item 1", "item 2", "item 3", "item 4"))
                .build();
        mockCharacter.getPlaybookUniques().setBrainerGear(brainerGear);
        Character returnedCharacter = checkUnadjustedUnique(adjustBrainerUnique1);
        assertEquals(2, returnedCharacter.getPlaybookUniques().getBrainerGear().getAllowedItemsCount());
        assertEquals(2, returnedCharacter.getPlaybookUniques().getBrainerGear().getBrainerGear().size());
    }

    @Test
    void shouldIncreaseGangStrengthOptions_onAddCharacterImprovement() {
        addPlaybookUniquesToCharacter(mockCharacter, UniqueType.GANG);
        Gang gang = Gang.builder()
                .id(new ObjectId().toString())
                .allowedStrengths(gangCreator.getStrengthChoiceCount())
                .build();
        mockCharacter.getPlaybookUniques().setGang(gang);
        Character returnedCharacter = checkAdjustedUnique(adjustChopperUnique1);
        assertEquals(gangCreator.getStrengthChoiceCount() + 1,
                returnedCharacter.getPlaybookUniques().getGang().getAllowedStrengths());
    }

    @Test
    void shouldDecreaseGangStrengthOptions_onRemoveCharacterImprovement() {
        addPlaybookUniquesToCharacter(mockCharacter, GANG);
        Gang gang = Gang.builder()
                .id(new ObjectId().toString())
                .allowedStrengths(gangCreator.getStrengthChoiceCount() + 1)
                .strengths(List.of(gangOption1, gangOption2, gangOption3))
                .build();
        mockCharacter.getPlaybookUniques().setGang(gang);
        Character returnedCharacter = checkUnadjustedUnique(adjustChopperUnique1);
        assertEquals(2, returnedCharacter.getPlaybookUniques().getGang().getAllowedStrengths());
        assertEquals(2, returnedCharacter.getPlaybookUniques().getGang().getStrengths().size());
    }

    @Test
    void shouldIncreaseHoldingStrengthsCount_onAddCharacterImprovement() {
        addPlaybookUniquesToCharacter(mockCharacter, GANG);
        Holding mockHolding = Holding.builder()
                .id(new ObjectId().toString())
                .strengthsCount(holdingCreator.getDefaultStrengthsCount())
                .weaknessesCount(holdingCreator.getDefaultWeaknessesCount())
                .build();
        mockCharacter.getPlaybookUniques().setHolding(mockHolding);
        Character returnedCharacter = checkAdjustedUnique(adjustHardHolderUnique1);
        assertEquals(holdingCreator.getDefaultStrengthsCount() + 1,
                returnedCharacter.getPlaybookUniques().getHolding().getStrengthsCount());
    }

    @Test
    void shouldIncreaseHoldingStrengthsCount_onAddCharacterImprovement_twoAtOnce() {
        // Given
        int initialStrengthsCount = holdingCreator.getDefaultStrengthsCount();
        mockCharacter.setAllowedImprovements(2);
        Holding mockHolding = Holding.builder()
                .id(new ObjectId().toString())
                .strengthsCount(initialStrengthsCount)
                .weaknessesCount(holdingCreator.getDefaultWeaknessesCount())
                .build();
        mockPlaybookUnique.setHolding(mockHolding);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(adjustHardHolderUnique1.getId())).thenReturn(adjustHardHolderUnique1);
        when(moveService.findById(adjustHardHolderUnique2.getId())).thenReturn(adjustHardHolderUnique2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(adjustHardHolderUnique1.getId(), adjustHardHolderUnique2.getId()), List.of());

        // Then
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(adjustHardHolderUnique1.getName())));
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(adjustHardHolderUnique2.getName())));
        assertEquals(initialStrengthsCount + 2,
                returnedCharacter.getPlaybookUniques().getHolding().getStrengthsCount());
        verifyMockServices();
        verify(moveService, times(2)).findById(anyString());
    }

    @Test
    void shouldDecreaseHoldingStrengthsCount_onRemoveCharacterImprovement() {
        int initialStrengthsCount = 5;
        addPlaybookUniquesToCharacter(mockCharacter, HOLDING);
        HoldingOption mockHoldingOption1 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("item1")
                .build();
        HoldingOption mockHoldingOption2 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("item2")
                .build();
        HoldingOption mockHoldingOption3 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("item3")
                .build();
        HoldingOption mockHoldingOption4 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("item4")
                .build();
        HoldingOption mockHoldingOption5 = HoldingOption.builder()
                .id(new ObjectId().toString())
                .description("item5")
                .build();
        Holding mockHolding = Holding.builder()
                .id(new ObjectId().toString())
                .strengthsCount(initialStrengthsCount)
                .weaknessesCount(holdingCreator.getDefaultWeaknessesCount())
                .strengthsCount(initialStrengthsCount)
                .selectedStrengths(List.of(mockHoldingOption1,
                        mockHoldingOption2, mockHoldingOption3, mockHoldingOption4, mockHoldingOption5))
                .build();
        mockCharacter.getPlaybookUniques().setHolding(mockHolding);
        Character returnedCharacter = checkUnadjustedUnique(adjustHardHolderUnique1);
        assertEquals(initialStrengthsCount - 1,
                returnedCharacter.getPlaybookUniques().getHolding().getStrengthsCount());
        assertEquals(initialStrengthsCount - 1,
                returnedCharacter.getPlaybookUniques().getHolding().getSelectedStrengths().size());
    }

    @Test
    void shouldDecreaseHoldingWeaknessesCount_onAddCharacterImprovement() {
        int initialWeaknessesCount = holdingCreator.getDefaultWeaknessesCount();
        addPlaybookUniquesToCharacter(mockCharacter, HOLDING);
        Holding mockHolding = Holding.builder()
                .id(new ObjectId().toString())
                .strengthsCount(holdingCreator.getDefaultStrengthsCount())
                .weaknessesCount(initialWeaknessesCount)
                .build();
        mockCharacter.getPlaybookUniques().setHolding(mockHolding);
        Character returnedCharacter = checkAdjustedUnique(adjustHardHolderUnique3);
        assertEquals(initialWeaknessesCount - 1, returnedCharacter.getPlaybookUniques().getHolding().getWeaknessesCount());
    }

    @Test
    void shouldIncreaseHoldingWeaknessesCount_onRemoveCharacterImprovement() {
        int initialWeaknessesCount = 1;
        addPlaybookUniquesToCharacter(mockCharacter, HOLDING);
        Holding mockHolding = Holding.builder()
                .id(new ObjectId().toString())
                .weaknessesCount(initialWeaknessesCount)
                .build();
        mockCharacter.getPlaybookUniques().setHolding(mockHolding);
        Character returnedCharacter = checkUnadjustedUnique(adjustHardHolderUnique3);
        assertEquals(initialWeaknessesCount + 1,
                returnedCharacter.getPlaybookUniques().getHolding().getWeaknessesCount());
    }

    @Test
    void shouldIncreaseFollowersStrengthsCount_onAddCharacterImprovement() {
        int initialStrengthsCount = followersCreator.getDefaultStrengthsCount();
        addPlaybookUniquesToCharacter(mockCharacter, FOLLOWERS);
        Followers mockFollowers = Followers.builder()
                .id(new ObjectId().toString())
                .strengthsCount(initialStrengthsCount)
                .build();
        mockCharacter.getPlaybookUniques().setFollowers(mockFollowers);
        Character returnedCharacter = checkAdjustedUnique(adjustHocusUnique1);
        assertEquals(initialStrengthsCount + 1,
                returnedCharacter.getPlaybookUniques().getFollowers().getStrengthsCount());
    }

    @Test
    void shouldDecreaseFollowersStrengthsCount_onRemoveCharacterImprovement() {
        int initialStrengthsCount = 3;
        addPlaybookUniquesToCharacter(mockCharacter, FOLLOWERS);
        FollowersOption mockOption1 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("item1")
                .build();
        FollowersOption mockOption2 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("item2")
                .build();
        FollowersOption mockOption3 = FollowersOption.builder()
                .id(new ObjectId().toString())
                .description("item3")
                .build();
        Followers mockFollowers = Followers.builder()
                .id(new ObjectId().toString())
                .selectedStrengths(List.of(mockOption1, mockOption2, mockOption3))
                .strengthsCount(initialStrengthsCount)
                .build();
        mockCharacter.getPlaybookUniques().setFollowers(mockFollowers);
        Character returnedCharacter = checkUnadjustedUnique(adjustHocusUnique1);
        assertEquals(initialStrengthsCount - 1,
                returnedCharacter.getPlaybookUniques().getFollowers().getStrengthsCount());
        assertEquals(initialStrengthsCount - 1,
                returnedCharacter.getPlaybookUniques().getFollowers().getSelectedStrengths().size());
    }

    @Test
    void shouldIncreaseWorkspaceOptionsCount_onAddCharacterImprovement() {
        int initialItemsCount = workspaceCreator.getDefaultItemsCount();
        addPlaybookUniquesToCharacter(mockCharacter, WORKSPACE);
        Workspace mockWorkSpace = Workspace.builder()
                .id(new ObjectId().toString())
                .itemsCount(initialItemsCount)
                .build();
        mockCharacter.getPlaybookUniques().setWorkspace(mockWorkSpace);
        Character returnedCharacter = checkAdjustedUnique(adjustSavvyheadUnique1);
        assertEquals(initialItemsCount + 2,
                returnedCharacter.getPlaybookUniques().getWorkspace().getItemsCount());
    }

    @Test
    void shouldDecreaseWorkspaceOptionsCount_onRemoveCharacterImprovement() {
        int initialItemsCount = 5;
        addPlaybookUniquesToCharacter(mockCharacter, WORKSPACE);
        Workspace mockWorkSpace = Workspace.builder()
                .id(new ObjectId().toString())
                .workspaceItems(List.of("item1", "item2", "item3", "item4", "item5"))
                .itemsCount(initialItemsCount)
                .build();
        mockCharacter.getPlaybookUniques().setWorkspace(mockWorkSpace);
        Character returnedCharacter = checkUnadjustedUnique(adjustSavvyheadUnique1);
        assertEquals(initialItemsCount - 2,
                returnedCharacter.getPlaybookUniques().getWorkspace().getItemsCount());
        assertEquals(initialItemsCount - 2,
                returnedCharacter.getPlaybookUniques().getWorkspace().getWorkspaceItems().size());
    }

    @Test
    void shouldAddLifeSupportToWorkspace_onAddCharacterImprovement() {
        int initialItemsCount = workspaceCreator.getDefaultItemsCount();
        addPlaybookUniquesToCharacter(mockCharacter, WORKSPACE);
        Workspace mockWorkSpace = Workspace.builder()
                .id(new ObjectId().toString())
                .itemsCount(initialItemsCount)
                .workspaceItems(List.of("item1", "item2", "item3"))
                .build();
        mockCharacter.getPlaybookUniques().setWorkspace(mockWorkSpace);
        Character returnedCharacter = checkAdjustedUnique(adjustSavvyheadUnique2);
        assertEquals(initialItemsCount + 1,
                returnedCharacter.getPlaybookUniques().getWorkspace().getItemsCount());
        assertEquals(initialItemsCount + 1,
                returnedCharacter.getPlaybookUniques().getWorkspace().getWorkspaceItems().size());
        assertTrue(returnedCharacter.getPlaybookUniques().getWorkspace().getWorkspaceItems().contains(WORKSPACE_LIFE_SUPPORT_ITEM));
    }

    @Test
    void shouldRemoveLifeSupportFromWorkspace_onRemoveCharacterImprovement() {
        int initialItemsCount = 4;
        addPlaybookUniquesToCharacter(mockCharacter, WORKSPACE);
        Workspace mockWorkSpace = Workspace.builder()
                .id(new ObjectId().toString())
                .workspaceItems(List.of("item1", "item2", "item3", WORKSPACE_LIFE_SUPPORT_ITEM))
                .itemsCount(initialItemsCount)
                .build();
        mockCharacter.getPlaybookUniques().setWorkspace(mockWorkSpace);
        Character returnedCharacter = checkUnadjustedUnique(adjustSavvyheadUnique2);
        assertEquals(initialItemsCount - 1,
                returnedCharacter.getPlaybookUniques().getWorkspace().getItemsCount());
        assertEquals(initialItemsCount - 1,
                returnedCharacter.getPlaybookUniques().getWorkspace().getWorkspaceItems().size());
        assertFalse(returnedCharacter.getPlaybookUniques().getWorkspace().getWorkspaceItems().contains(WORKSPACE_LIFE_SUPPORT_ITEM));

    }

    @Test
    void shouldIncreaseEstablishmentSecurities_onAddCharacterImprovement() {
        int initialSecuritiesCount = establishmentCreator.getDefaultSecuritiesCount();
        addPlaybookUniquesToCharacter(mockCharacter, ESTABLISHMENT);
        Establishment mockEstablishment = Establishment.builder()
                .id(new ObjectId().toString())
                .securitiesCount(initialSecuritiesCount)
                .build();
        mockCharacter.getPlaybookUniques().setEstablishment(mockEstablishment);
        Character returnedCharacter = checkAdjustedUnique(adjustMaestroDUnique1);
        assertEquals(initialSecuritiesCount + 1,
                returnedCharacter.getPlaybookUniques().getEstablishment().getSecuritiesCount());
    }

    @Test
    void shouldDecreaseEstablishmentSecurities_onRemoveCharacterImprovement() {
        int initialSecuritiesCount = 3;
        addPlaybookUniquesToCharacter(mockCharacter, ESTABLISHMENT);
        SecurityOption mockSecurityOption1 = SecurityOption.builder()
                .id(new ObjectId().toString())
                .description("security-option-1")
                .build();
        SecurityOption mockSecurityOption2 = SecurityOption.builder()
                .id(new ObjectId().toString())
                .description("security-option-1")
                .build();
        SecurityOption mockSecurityOption3 = SecurityOption.builder()
                .id(new ObjectId().toString())
                .description("security-option-1")
                .build();
        Establishment mockEstablishment = Establishment.builder()
                .id(new ObjectId().toString())
                .securityOptions(List.of(mockSecurityOption1, mockSecurityOption2, mockSecurityOption3))
                .securitiesCount(initialSecuritiesCount)
                .build();
        mockCharacter.getPlaybookUniques().setEstablishment(mockEstablishment);
        Character returnedCharacter = checkUnadjustedUnique(adjustMaestroDUnique1);
        assertEquals(initialSecuritiesCount - 1,
                returnedCharacter.getPlaybookUniques().getEstablishment().getSecuritiesCount());
        assertEquals(initialSecuritiesCount - 1,
                returnedCharacter.getPlaybookUniques().getEstablishment().getSecurityOptions().size());
    }

    @Test
    void shouldEnableEstablishmentInterestResolution_onAddCharacterImprovement() {
        addPlaybookUniquesToCharacter(mockCharacter, ESTABLISHMENT);
        Establishment mockEstablishment = Establishment.builder()
                .id(new ObjectId().toString())
                .build();
        mockCharacter.getPlaybookUniques().setEstablishment(mockEstablishment);
        checkAdjustedUnique(adjustMaestroDUnique2);
    }

    @Test
    void shouldRestoreEstablishmentInterest_onRemoveCharacterImprovement() {
        addPlaybookUniquesToCharacter(mockCharacter, ESTABLISHMENT);
        Establishment mockEstablishment = Establishment.builder()
                .id(new ObjectId().toString())
                .build();
        mockCharacter.getPlaybookUniques().setEstablishment(mockEstablishment);
        checkUnadjustedUnique(adjustMaestroDUnique2);
    }

    @Test
    void shouldIncreaseVehiclesCount_onAddCharacterImprovement() {
        // Given
        mockCharacter.setAllowedImprovements(1);
        mockCharacter.setVehicleCount(0);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(addVehicle);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(addVehicle.getId()), List.of());

        // Then
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(addVehicle.getName())));
        assertEquals(1, returnedCharacter.getVehicleCount());
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldDecreaseVehiclesCount_onRemoveCharacterImprovement() {
        // Given
        int initialVehiclesCount = 2;
        addStatsBlockToCharacter(mockCharacter);
        mockCharacter.setAllowedImprovements(1);
        mockCharacter.setVehicleCount(initialVehiclesCount);
        Vehicle mockVehicle1 = Vehicle.builder().id(new ObjectId().toString()).build();
        Vehicle mockVehicle2 = Vehicle.builder().id(new ObjectId().toString()).build();
        mockCharacter.setVehicles(List.of(mockVehicle1, mockVehicle2));

        CharacterMove addVehicleAsCM = CharacterMove.createFromMove(addVehicle);
        mockCharacter.setImprovementMoves(List.of(addVehicleAsCM));

        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertEquals(initialVehiclesCount - 1, returnedCharacter.getVehicleCount());
        assertEquals(initialVehiclesCount - 1, returnedCharacter.getVehicles().size());

        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(addVehicleAsCM.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldAddGangAndLeadership_onAddCharacterImprovement() {
        Character returnedCharacter = checkAddedUniqueAndMove(addGangPackAlpha, packAlpha);
        assertNotNull(returnedCharacter.getPlaybookUniques().getAngelKit());
        assertEquals(gangCreator.getDefaultSize(), returnedCharacter.getPlaybookUniques().getGang().getSize());
        assertEquals(1, returnedCharacter.getAllowedOtherPlaybookMoves());
    }

    @Test
    void shouldRemoveGangAndLeadership_onRemoveCharacterImprovement() {
        Gang mockGang = Gang.builder().id(new ObjectId().toString()).build();
        mockPlaybookUnique.setGang(mockGang);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockCharacter.setAllowedOtherPlaybookMoves(1);
        Character returnedCharacter = checkRemovedUniqueAndMove(addGangLeadership, leadership);
        assertNull(returnedCharacter.getPlaybookUniques().getGang());
        assertEquals(0, returnedCharacter.getAllowedOtherPlaybookMoves());
    }

    @Test
    void shouldAddGangAndPackAlpha_onAddCharacterImprovement() {
        Character returnedCharacter = checkAddedUniqueAndMove(addGangPackAlpha, packAlpha);
        assertNotNull(returnedCharacter.getPlaybookUniques().getAngelKit());
        assertEquals(gangCreator.getDefaultSize(), returnedCharacter.getPlaybookUniques().getGang().getSize());
        assertEquals(1, returnedCharacter.getAllowedOtherPlaybookMoves());
    }

    @Test
    void shouldRemoveGangAndPackAlpha_onRemoveCharacterImprovement() {
        Gang mockGang = Gang.builder().id(new ObjectId().toString()).build();
        mockPlaybookUnique.setGang(mockGang);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockCharacter.setAllowedOtherPlaybookMoves(1);
        Character returnedCharacter = checkRemovedUniqueAndMove(addGangPackAlpha, packAlpha);
        assertNull(returnedCharacter.getPlaybookUniques().getGang());
        assertEquals(0, returnedCharacter.getAllowedOtherPlaybookMoves());
    }

    @Test
    void shouldAddHoldingAndWealth_onAddCharacterImprovement() {
        Character returnedCharacter = checkAddedUniqueAndMove(addHolding, wealth);
        assertNotNull(returnedCharacter.getPlaybookUniques().getHolding());
        assertEquals(holdingCreator.getDefaultHoldingSize(),
                returnedCharacter.getPlaybookUniques().getHolding().getHoldingSize());
        assertEquals(1, returnedCharacter.getAllowedOtherPlaybookMoves());
    }

    @Test
    void shouldRemoveHoldingAndWealth_onRemoveCharacterImprovement() {
        Holding mockHolding = Holding.builder().id(new ObjectId().toString()).build();
        mockPlaybookUnique.setHolding(mockHolding);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockCharacter.setAllowedOtherPlaybookMoves(1);
        Character returnedCharacter = checkRemovedUniqueAndMove(addGangPackAlpha, packAlpha);
        assertNull(returnedCharacter.getPlaybookUniques().getGang());
        assertEquals(0, returnedCharacter.getAllowedOtherPlaybookMoves());
    }

    @Test
    void shouldAddWorkspace_onAddCharacterImprovement() {
        Character returnedCharacter = checkAddedUnique(addWorkspace);
        assertNotNull(returnedCharacter.getPlaybookUniques().getWorkspace());
        assertEquals(workspaceCreator.getWorkspaceInstructions(), returnedCharacter.getPlaybookUniques().getWorkspace().getWorkspaceInstructions());
        assertEquals(workspaceCreator.getProjectInstructions(), returnedCharacter.getPlaybookUniques().getWorkspace().getProjectInstructions());
    }

    @Test
    void shouldRemoveWorkspace_onRemoveCharacterImprovement() {
        Workspace mockWorkspace = Workspace.builder().id(new ObjectId().toString()).build();
        mockPlaybookUnique.setWorkspace(mockWorkspace);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        Character returnedCharacter = checkRemovedUnique(addWorkspace);
        assertNull(returnedCharacter.getPlaybookUniques().getWorkspace());
    }

    @Test
    void shouldAddEstablishment_onAddCharacterImprovement() {
        Character returnedCharacter = checkAddedUnique(addEstablishment);
        assertNotNull(returnedCharacter.getPlaybookUniques().getEstablishment());
    }

    @Test
    void shouldRemoveEstablishment_onRemoveCharacterImprovement() {
        Establishment mockEstablishment = Establishment.builder().id(new ObjectId().toString()).build();
        mockPlaybookUnique.setEstablishment(mockEstablishment);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        Character returnedCharacter = checkRemovedUnique(addEstablishment);
        assertNull(returnedCharacter.getPlaybookUniques().getEstablishment());
    }

    @Test
    void shouldAddFollowersAndFortunes_onAddCharacterImprovement() {
        Character returnedCharacter = checkAddedUniqueAndMove(addFollowers, fortunes);
        assertNotNull(returnedCharacter.getPlaybookUniques().getFollowers());
        assertEquals(1, returnedCharacter.getAllowedOtherPlaybookMoves());
    }

    @Test
    void shouldRemoveFollowersAndFortunes_onRemoveCharacterImprovement() {
        Followers mockFollowers = Followers.builder().id(new ObjectId().toString()).build();
        mockPlaybookUnique.setFollowers(mockFollowers);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockCharacter.setAllowedOtherPlaybookMoves(1);
        Character returnedCharacter = checkRemovedUniqueAndMove(addFollowers, fortunes);
        assertNull(returnedCharacter.getPlaybookUniques().getEstablishment());
        assertEquals(0, returnedCharacter.getAllowedOtherPlaybookMoves());
    }

    @Test
    void shouldRemoveHold() {

        mockCharacter.getHolds().add(mockHold);
        mockGameRole.getCharacters().add(mockCharacter);

        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .removeHold(mockGameRole.getId(), mockCharacter.getId(), mockHold);

        // Then
        assertFalse(returnedCharacter.getHolds().contains(mockHold));
        verifyMockServices();
    }

    @Test
    void shouldIncreaseHardAndIncreaseWeird_onSwapDeathMove() {
        // Given
        CharacterStat mockCharacterStatHard = CharacterStat.builder().stat(StatType.HARD).value(0).build();
        CharacterStat mockCharacterStatWeird = CharacterStat.builder().stat(StatType.WEIRD).value(1).build();
        StatsBlock statsBlock = StatsBlock.builder()
                .id("mock-stats-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCharacterStatHard, mockCharacterStatWeird))
                .build();
        mockCharacter.setStatsBlock(statsBlock);
        CharacterMove hardMinus1AsCM = CharacterMove.createFromMove(hardMinus1, true);
        mockCharacter.setDeathMoves(List.of(hardMinus1AsCM));
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService.setDeathMoves(mockGameRole.getId(),
                mockCharacter.getId(),
                List.of(deathWeirdMax3.getName()));

        // Then
        CharacterStat hardStat = returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.HARD)).findFirst().orElseThrow();
        CharacterStat weirdStat = returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.WEIRD)).findFirst().orElseThrow();
        assertEquals(1, hardStat.getValue());
        assertEquals(2, weirdStat.getValue());
        assertFalse(returnedCharacter.getDeathMoves()
                .stream().anyMatch(characterMove -> characterMove.getName().equals(hardMinus1.getName())));
        assertTrue(returnedCharacter.getDeathMoves()
                .stream().anyMatch(characterMove -> characterMove.getName().equals(deathWeirdMax3.getName())));
        verifyMockServices();
    }

    @Test
    void shouldDecreaseWeirdAndKillCharacter_onSwapDeathMove() {
        // Given
        CharacterStat mockCharacterStatWeird = CharacterStat.builder().stat(StatType.WEIRD).value(2).build();
        StatsBlock statsBlock = StatsBlock.builder()
                .id("mock-stats-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCharacterStatWeird))
                .build();
        mockCharacter.setStatsBlock(statsBlock);
        CharacterMove deathWeirdMax3AsCM = CharacterMove.createFromMove(deathWeirdMax3, true);
        mockCharacter.setDeathMoves(List.of(deathWeirdMax3AsCM));
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService.setDeathMoves(mockGameRole.getId(),
                mockCharacter.getId(),
                List.of(die.getName()));

        // Then
        CharacterStat weirdStat = returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.WEIRD)).findFirst().orElseThrow();
        assertEquals(1, weirdStat.getValue());
        assertTrue(returnedCharacter.getIsDead());
        assertFalse(returnedCharacter.getDeathMoves()
                .stream().anyMatch(characterMove -> characterMove.getName().equals(deathWeirdMax3.getName())));
        assertTrue(returnedCharacter.getDeathMoves()
                .stream().anyMatch(characterMove -> characterMove.getName().equals(die.getName())));
        verifyMockServices();
    }

    @Test
    void shouldReviveCharacterAndChangePlaybook_onSwapDeathMove() {
        // Given
        int initialAllowedCharacterMoves = 0;
        int initialAllowedOtherPBMoves = 2;
        mockCharacter.setIsDead(true);
        mockCharacter.setPlaybook(PlaybookType.GUNLUGGER);
        Holding mockHolding = Holding.builder().id(new ObjectId().toString()).build();
        Gang mockGang = Gang.builder().id(new ObjectId().toString()).build();
        Weapons mockWeapons = Weapons.builder().id(new ObjectId().toString()).build();
        mockPlaybookUnique.setHolding(mockHolding);
        mockPlaybookUnique.setGang(mockGang);
        mockPlaybookUnique.setWeapons(mockWeapons);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        CharacterMove dieAsCM = CharacterMove.createFromMove(die, true);
        CharacterMove addGangPackAlphaAsCM = CharacterMove.createFromMove(addGangPackAlpha, true);
        CharacterMove addHoldingAsCM = CharacterMove.createFromMove(addHolding, true);
        CharacterMove packAlphaAsCM = CharacterMove.createFromMove(packAlpha, true);
        CharacterMove wealthAsCM = CharacterMove.createFromMove(wealth, true);
        CharacterMove gunluggerSpecialAsCM = CharacterMove.createFromMove(gunluggerSpecial, true);

        mockCharacter.setImprovementMoves(List.of(addGangPackAlphaAsCM, addHoldingAsCM));
        mockCharacter.setCharacterMoves(List.of(packAlphaAsCM,wealthAsCM, gunluggerSpecialAsCM ));
        mockCharacter.setDeathMoves(List.of(dieAsCM));
        mockCharacter.setGear(List.of("item1", "item2"));
        mockCharacter.setAllowedPlaybookMoves(initialAllowedCharacterMoves);
        mockCharacter.setAllowedOtherPlaybookMoves(initialAllowedOtherPBMoves);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService.setDeathMoves(mockGameRole.getId(),
                mockCharacter.getId(),
                List.of(deathChangePlaybook.getName()));

        // Then
        // Check the playbook's Unique has been removed
        assertNull(returnedCharacter.getPlaybookUniques().getWeapons());
        assertNull(returnedCharacter.getPlaybook());
        assertNull(returnedCharacter.getStatsBlock());
        assertTrue(returnedCharacter.getMustChangePlaybook());

        // Check that Uniques gained through improvements are still there
        assertNotNull(returnedCharacter.getPlaybookUniques().getHolding());
        assertNotNull(returnedCharacter.getPlaybookUniques().getGang());

        // Check the Gunlugger's special move has been removed
        assertEquals(initialAllowedCharacterMoves, returnedCharacter.getAllowedPlaybookMoves());
        assertEquals(initialAllowedCharacterMoves + initialAllowedOtherPBMoves,
                returnedCharacter.getCharacterMoves().size());
        assertFalse(returnedCharacter.getCharacterMoves()
                .stream().anyMatch(characterMove -> characterMove.getName().equals(gunluggerSpecial.getName())));

        // Check that moves gained by gaining Uniques through improvements are still there
        assertTrue(returnedCharacter.getCharacterMoves()
                .stream().anyMatch(characterMove -> characterMove.getName().equals(wealth.getName())));
        assertTrue(returnedCharacter.getCharacterMoves()
                .stream().anyMatch(characterMove -> characterMove.getName().equals(packAlpha.getName())));

        // Check that gear has been reset
        assertEquals(0, returnedCharacter.getGear().size());

        // Check character is no longer dead
        assertFalse(returnedCharacter.getIsDead());

        assertFalse(returnedCharacter.getDeathMoves()
                .stream().anyMatch(characterMove -> characterMove.getName().equals(die.getName())));
        assertTrue(returnedCharacter.getDeathMoves()
                .stream().anyMatch(characterMove -> characterMove.getName().equals(deathChangePlaybook.getName())));
        verifyMockServices();
    }

    @Test
    void shouldNOTUnChangePlaybookButShouldDecreaseHard_onSwapDeathMove() {
        // Given
        addStatsBlockToCharacter(mockCharacter);
        mockCharacter.setPlaybook(PlaybookType.GUNLUGGER);
        Holding mockHolding = Holding.builder().id(new ObjectId().toString()).build();
        Gang mockGang = Gang.builder().id(new ObjectId().toString()).build();
        Weapons mockWeapons = Weapons.builder().id(new ObjectId().toString()).build();
        mockPlaybookUnique.setHolding(mockHolding);
        mockPlaybookUnique.setGang(mockGang);
        mockPlaybookUnique.setWeapons(mockWeapons);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        CharacterMove deathChangePlaybookAsCM = CharacterMove.createFromMove(deathChangePlaybook, true);
        CharacterMove addGangPackAlphaAsCM = CharacterMove.createFromMove(addGangPackAlpha, true);
        CharacterMove addHoldingAsCM = CharacterMove.createFromMove(addHolding, true);
        CharacterMove packAlphaAsCM = CharacterMove.createFromMove(packAlpha, true);
        CharacterMove wealthAsCM = CharacterMove.createFromMove(wealth, true);
        CharacterMove gunluggerSpecialAsCM = CharacterMove.createFromMove(gunluggerSpecial, true);
        mockCharacter.setImprovementMoves(List.of(addGangPackAlphaAsCM, addHoldingAsCM));
        mockCharacter.setCharacterMoves(List.of(packAlphaAsCM,wealthAsCM, gunluggerSpecialAsCM ));
        mockCharacter.setDeathMoves(List.of(deathChangePlaybookAsCM));
        mockCharacter.setGear(List.of("item1", "item2"));
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService.setDeathMoves(mockGameRole.getId(),
                mockCharacter.getId(),
                List.of(hardMinus1.getName()));

        // Then
        // Check the playbook's Unique is still there
        assertNotNull(returnedCharacter.getPlaybookUniques().getWeapons());

        // Check the Gunlugger's special move is still there
        assertTrue(returnedCharacter.getCharacterMoves()
                .stream().anyMatch(characterMove -> characterMove.getName().equals(gunluggerSpecial.getName())));

        // Check that gear has NOT been reset
        assertNotEquals(0, returnedCharacter.getGear().size());

        CharacterStat hardStat = returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.HARD)).findFirst().orElseThrow();
        assertEquals(0, hardStat.getValue());

        assertFalse(returnedCharacter.getDeathMoves()
                .stream().anyMatch(characterMove -> characterMove.getName().equals(deathChangePlaybook.getName())));
        assertTrue(returnedCharacter.getDeathMoves()
                .stream().anyMatch(characterMove -> characterMove.getName().equals(hardMinus1.getName())));
        verifyMockServices();
    }

    private Character checkAddedUnique(Move improvementMove) {
        // Given
        CharacterMove improvementMoveAsCM = CharacterMove.createFromMove(improvementMove);
        mockCharacter.setAllowedImprovements(1);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(improvementMoveAsCM);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(improvementMoveAsCM.getId()), List.of());

        // Then
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(improvementMoveAsCM.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
        return returnedCharacter;
    }

    private Character checkRemovedUnique(Move improvementMove) {
        // Given
        CharacterStat mockCoolStat = CharacterStat.builder()
                .id("mock-cool-stat-id")
                .stat(COOL)
                .value(2)
                .build();

        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stat-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCoolStat))
                .build();


        mockCharacter.setStatsBlock(mockStatsBlock);
        mockCharacter.setAllowedImprovements(1);
        CharacterMove improvementMoveAsCM = CharacterMove.createFromMove(improvementMove);
        mockCharacter.setImprovementMoves(List.of(improvementMoveAsCM));

        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertNotNull(returnedCharacter.getPlaybookUniques().getAngelKit());

        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(improvementMove.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
        return returnedCharacter;
    }

    private Character checkAddedUniqueAndMove(Move improvementMove, Move move) {
        // Given
        CharacterMove improvementMoveAsCM = CharacterMove.createFromMove(improvementMove);
        mockCharacter.setAllowedImprovements(1);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(improvementMoveAsCM);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(improvementMoveAsCM.getId()), List.of());

        // Then
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(improvementMove.getName())));
        assertTrue(returnedCharacter.getCharacterMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(move.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
        return returnedCharacter;
    }

    private Character checkRemovedUniqueAndMove(Move improvementMove, Move move) {
//        Gang mockGang = Gang.builder().id(new ObjectId().toString()).build();
//        mockPlaybookUnique.setGang(mockGang);
//        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        // Given
        CharacterStat mockCoolStat = CharacterStat.builder()
                .id("mock-cool-stat-id")
                .stat(COOL)
                .value(2)
                .build();

        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stat-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCoolStat))
                .build();

        mockCharacter.setStatsBlock(mockStatsBlock);
        mockCharacter.setAllowedImprovements(1);
        CharacterMove improvementMoveAsCM = CharacterMove.createFromMove(improvementMove);
        CharacterMove moveAsCM = CharacterMove.createFromMove(move);
        mockCharacter.setImprovementMoves(List.of(improvementMoveAsCM));
        mockCharacter.setCharacterMoves(List.of(moveAsCM));

        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertNotNull(returnedCharacter.getPlaybookUniques().getAngelKit());

        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(improvementMove.getName())));
        assertFalse(returnedCharacter.getCharacterMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(move.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
        return returnedCharacter;
//        assertNull(returnedCharacter.getPlaybookUniques().getGang());
    }

    private Character checkAdjustedUnique(Move improvementMove) {
        // Given
        mockCharacter.setAllowedImprovements(1);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(improvementMove);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(improvementMove.getId()), List.of());

        // Then
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(improvementMove.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());

        return returnedCharacter;
    }

    private Character checkUnadjustedUnique(Move improvementMove) {
        // Given
        addStatsBlockToCharacter(mockCharacter);
        mockCharacter.setAllowedImprovements(1);
        CharacterMove improvementMoveAsCM = CharacterMove.createFromMove(improvementMove);
        mockCharacter.setImprovementMoves(List.of(improvementMoveAsCM));
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(improvementMoveAsCM.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());

        return returnedCharacter;
    }

    private void verifyMockServices() {
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    private void setupMockServices() {
        when(gameRoleRepository.findById(anyString())).thenReturn(Optional.of(mockGameRole));
        when(characterService.save(any())).thenReturn(mockCharacter);
        when(gameRoleRepository.save(any())).thenReturn(mockGameRole);
    }

    private void addStatsBlockToCharacter(Character mockCharacter) {
        CharacterStat mockCharacterStatCool = CharacterStat.builder().stat(COOL).value(1).build();
        CharacterStat mockCharacterStatHard = CharacterStat.builder().stat(StatType.HARD).value(1).build();
        CharacterStat mockCharacterStatHot = CharacterStat.builder().stat(HOT).value(1).build();
        CharacterStat mockCharacterStatSharp = CharacterStat.builder().stat(StatType.SHARP).value(1).build();
        CharacterStat mockCharacterStatWeird = CharacterStat.builder().stat(StatType.WEIRD).value(1).build();
        StatsBlock statsBlock = StatsBlock.builder()
                .id("mock-stats-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCharacterStatCool,
                        mockCharacterStatHard,
                        mockCharacterStatHot,
                        mockCharacterStatSharp,
                        mockCharacterStatWeird))
                .build();
        mockCharacter.setStatsBlock(statsBlock);
    }
}