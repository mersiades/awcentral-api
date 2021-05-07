package com.mersiades.awcdata.services.impl;

import com.mersiades.awccontent.content.MovesContent;
import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.Look;
import com.mersiades.awccontent.services.MoveService;
import com.mersiades.awccontent.services.PlaybookCreatorService;
import com.mersiades.awccontent.services.StatModifierService;
import com.mersiades.awccontent.services.StatsOptionService;
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

import java.util.List;
import java.util.Optional;

import static com.mersiades.awccontent.content.LooksContent.*;
import static com.mersiades.awccontent.content.MovesContent.*;
import static com.mersiades.awccontent.content.PlaybookCreatorsContent.*;
import static com.mersiades.awccontent.content.StatOptionsContent.statsOptionAngel1;
import static com.mersiades.awccontent.content.StatOptionsContent.statsOptionAngel2;
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

        mockCharacterStatCool = CharacterStat.builder().stat(StatType.COOL).value(1).build();
        mockCharacterStatHard = CharacterStat.builder().stat(StatType.HARD).value(1).build();
        mockCharacterStatHot = CharacterStat.builder().stat(StatType.HOT).value(1).build();
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
        verifyMockServices();
    }

    @Test
    void shouldSetCharacterPlaybook_Battlebabe() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(playbookCreatorService.findByPlaybookType(any(PlaybookType.class))).thenReturn(playbookCreatorBattlebabe);

        // When
        Character returnedCharacter = gameRoleService.setCharacterPlaybook(MOCK_GAMEROLE_ID, mockCharacter.getId(), PlaybookType.BATTLEBABE);

        // Then
        assertEquals(PlaybookType.BATTLEBABE, returnedCharacter.getPlaybook());
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
        mockCharacter.setBattleVehicles(List.of(mockBattleVehicle));
        mockCharacter.setVehicles(List.of(mockVehicle));
        mockCharacter.setHxBlock(List.of(hxStat1, hxStat2));
        mockCharacter.setGear(List.of("item1", "item2"));
        mockCharacter.setLooks(List.of(lookBattlebabe1, lookBattlebabe2));
        mockCharacter.setCharacterMoves(List.of(angelSpecialAsCM, sixthSenseAsCM, infirmaryAsCM, professionalCompassionAsCM));
        mockCharacter.setImprovementMoves(List.of(addVehicleAsCM));
        mockCharacter.setFutureImprovementMoves(List.of(retireAsCM));
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
        assertEquals(0, returnedCharacter.getHolds().size());

        assertEquals(0, returnedCharacter.getPlaybookUniques().getBrainerGear().getBrainerGear().size());
        assertEquals(BRAINER_GEAR, returnedCharacter.getPlaybookUniques().getBrainerGear().getUniqueType());
        assertEquals(brainerGearCreator.getDefaultItemCount(), returnedCharacter.getPlaybookUniques().getBrainerGear().getAllowedItemsCount());

        verifyMockServices();
        verify(playbookCreatorService, times(1)).findByPlaybookType(any(PlaybookType.class));
        verify(moveService, times(1)).findAllByPlaybookAndKind(PlaybookType.BRAINER, MoveType.DEFAULT_CHARACTER);
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
                .filter(characterStat -> characterStat.getStat().equals(StatType.COOL)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel1.getHARD(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.HARD)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel1.getHOT(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.HOT)).findFirst().orElseThrow().getValue());
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
                .filter(characterStat -> characterStat.getStat().equals(StatType.COOL)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel2.getHARD(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.HARD)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel2.getHOT(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.HOT)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel2.getSHARP(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.SHARP)).findFirst().orElseThrow().getValue());
        assertEquals(statsOptionAngel2.getWEIRD(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.WEIRD)).findFirst().orElseThrow().getValue());

        verify(statsOptionService, times(1)).findById(anyString());
        verifyMockServices();
    }

    @Test
    public void shouldSetCharacterGear() {
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
    public void shouldSetCharacterMoves_Angel() {
        // Given
        mockCharacter.setPlaybook(PlaybookType.ANGEL);
        mockGameRole.getCharacters().add(mockCharacter);

        addStatsBlockToCharacter(mockCharacter);

        when(moveService.findAllById(anyList())).thenReturn(List.of(angelSpecial, sixthSense, infirmary));
        setupMockServices();

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterMoves(mockGameRole.getId(), mockCharacter.getId(),
                        List.of(angelSpecial.getId(), sixthSense.getId(), infirmary.getId()));

        // Then
        assertEquals(3, returnedCharacter.getCharacterMoves().size());
        List<CharacterMove> returnedMoves = returnedCharacter.getCharacterMoves();

        returnedMoves.forEach(characterMove -> {
            assertTrue(characterMove.getName().equals(angelSpecial.getName()) ||
                    characterMove.getName().equals(sixthSense.getName()) ||
                    characterMove.getName().equals(infirmary.getName()));
        });
        verify(moveService, times(1)).findAllById(
                anyList()
        );
        verifyMockServices();
    }

    @Test
    public void shouldSetCharacterMoves_Angel_withSomeMovesFromOtherPlaybooks() {
        // Given
        mockCharacter.setPlaybook(PlaybookType.ANGEL);
        mockGameRole.getCharacters().add(mockCharacter);

        addStatsBlockToCharacter(mockCharacter);

        when(moveService.findAllById(anyList())).thenReturn(List.of(angelSpecial, bonefeel, infirmary));
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
        verify(moveService, times(1)).findAllById(
                anyList()
        );
        verifyMockServices();
    }

    @Test
    public void shouldSetCharacterMoves_Gunlugger_withPreparedForTheInevitableMove() {
        // Given
        mockCharacter.setPlaybook(PlaybookType.GUNLUGGER);
        mockGameRole.getCharacters().add(mockCharacter);

        addStatsBlockToCharacter(mockCharacter);

        addPlaybookUniquesToCharacter(mockCharacter, UniqueType.WEAPONS);

        Weapons weapons = Weapons.builder()
                .id(new ObjectId().toString())
                .build();

        mockCharacter.getPlaybookUniques().setWeapons(weapons);

        when(moveService.findAllById(anyList())).thenReturn(List.of(gunluggerSpecial,
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

        verify(moveService, times(1)).findAllById(
                anyList()
        );
        verifyMockServices();
    }

    @Test
    public void shouldRemoveAngelKit_Gunlugger_whenRemovingPreparedForTheInevitableMove() {
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

        when(moveService.findAllById(anyList())).thenReturn(List.of(gunluggerSpecial,
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

        verify(moveService, times(1)).findAllById(
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
    public void shouldSetCharacterHx() {
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
    public void shouldFinishCharacterCreation() {
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
    public void shouldSetAngelKit() {
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
    public void shouldSetCustomWeapons() {
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
                .stat(StatType.COOL)
                .build();
        CharacterStat mockHard = CharacterStat.builder()
                .isHighlighted(false)
                .value(1)
                .stat(StatType.HARD)
                .build();
        CharacterStat mockHot = CharacterStat.builder()
                .isHighlighted(false)
                .value(1)
                .stat(StatType.HOT)
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
                .toggleStatHighlight(mockGameRole.getId(), mockCharacter.getId(), StatType.COOL);

        // Then
        assertTrue(returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.COOL))
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
                .stat(StatType.COOL)
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
                .stat(StatType.COOL)
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
                .stat(StatType.COOL)
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
        // Given
        CharacterMove mockAdjustAngelUnique1 = CharacterMove.createFromMove(MovesContent.adjustAngelUnique1);
        mockCharacter.setAllowedImprovements(1);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(mockAdjustAngelUnique1);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(mockAdjustAngelUnique1.getId()), List.of());

        // Then
        assertTrue(returnedCharacter.getPlaybookUniques().getAngelKit().isHasSupplier());
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(mockAdjustAngelUnique1.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldRemoveSupplier_onRemoveCharacterImprovement() {
        // Given
        CharacterStat mockCoolStat = CharacterStat.builder()
                .id("mock-cool-stat-id")
                .stat(StatType.COOL)
                .value(2)
                .build();

        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stat-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCoolStat))
                .build();
        mockCharacter.setStatsBlock(mockStatsBlock);
        mockCharacter.setAllowedImprovements(1);
        CharacterMove mockAdjustAngelUnique1 = CharacterMove.createFromMove(MovesContent.adjustAngelUnique1);
        mockCharacter.setImprovementMoves(List.of(mockAdjustAngelUnique1));
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertFalse(returnedCharacter.getPlaybookUniques().getAngelKit().isHasSupplier());
        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(mockAdjustAngelUnique1.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldIncreaseBrainerGearItems_onAddCharacterImprovement() {
        // Given
        mockCharacter.setAllowedImprovements(1);
        addPlaybookUniquesToCharacter(mockCharacter, UniqueType.BRAINER_GEAR);

        BrainerGear brainerGear = BrainerGear.builder()
                .id(new ObjectId().toString())
                .allowedItemsCount(brainerGearCreator.getDefaultItemCount())
                .brainerGear(List.of("item 1", "item 2"))
                .build();
        mockCharacter.getPlaybookUniques().setBrainerGear(brainerGear);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(adjustBrainerUnique1);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(adjustBrainerUnique1.getId()), List.of());

        // Then
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(adjustBrainerUnique1.getName())));
        assertEquals(brainerGearCreator.getDefaultItemCount() +2, returnedCharacter.getPlaybookUniques().getBrainerGear().getAllowedItemsCount());
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldDecreaseBrainerGearItems_onRemoveCharacterImprovement() {
        // Given
        addStatsBlockToCharacter(mockCharacter);
        mockCharacter.setAllowedImprovements(1);
        addPlaybookUniquesToCharacter(mockCharacter, UniqueType.BRAINER_GEAR);

        BrainerGear brainerGear = BrainerGear.builder()
                .id(new ObjectId().toString())
                .allowedItemsCount(4)
                .brainerGear(List.of("item 1", "item 2", "item 3", "item 4"))
                .build();
        mockCharacter.getPlaybookUniques().setBrainerGear(brainerGear);
        CharacterMove mockAdjustBrainerUnique1 = CharacterMove.createFromMove(adjustBrainerUnique1);
        mockCharacter.setImprovementMoves(List.of(mockAdjustBrainerUnique1));

        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertEquals(2, returnedCharacter.getPlaybookUniques().getBrainerGear().getAllowedItemsCount());
        assertEquals(2, returnedCharacter.getPlaybookUniques().getBrainerGear().getBrainerGear().size());

        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(mockAdjustBrainerUnique1.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldIncreaseGangStrengthOptions_onAddCharacterImprovement() {
        // Given
        mockCharacter.setAllowedImprovements(1);
        addPlaybookUniquesToCharacter(mockCharacter, UniqueType.GANG);

        Gang gang = Gang.builder()
                .id(new ObjectId().toString())
                .allowedStrengths(gangCreator.getStrengthChoiceCount())
                .build();
        mockCharacter.getPlaybookUniques().setGang(gang);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(adjustChopperUnique1);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(adjustChopperUnique1.getId()), List.of());

        // Then
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(adjustChopperUnique1.getName())));
        assertEquals(gangCreator.getStrengthChoiceCount() + 1, returnedCharacter.getPlaybookUniques().getGang().getAllowedStrengths());
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldDecreaseGangStrengthOptions_onRemoveCharacterImprovement() {
        // Given
        addStatsBlockToCharacter(mockCharacter);
        mockCharacter.setAllowedImprovements(1);
        addPlaybookUniquesToCharacter(mockCharacter, UniqueType.GANG);

        Gang gang = Gang.builder()
                .id(new ObjectId().toString())
                .allowedStrengths(gangCreator.getStrengthChoiceCount() + 1)
                .strengths(List.of(gangOption1, gangOption2, gangOption3))
                .build();
        mockCharacter.getPlaybookUniques().setGang(gang);
        CharacterMove mockAdjustChopperUnique1 = CharacterMove.createFromMove(adjustChopperUnique1);
        mockCharacter.setImprovementMoves(List.of(mockAdjustChopperUnique1));

        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertEquals(2, returnedCharacter.getPlaybookUniques().getGang().getAllowedStrengths());
        assertEquals(2, returnedCharacter.getPlaybookUniques().getGang().getStrengths().size());

        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(mockAdjustChopperUnique1.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
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
        assertEquals(initialVehiclesCount -1 , returnedCharacter.getVehicleCount());
        assertEquals(initialVehiclesCount -1 , returnedCharacter.getVehicles().size());

        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(addVehicleAsCM.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldAddGangAndLeadership_onAddCharacterImprovement() {
        // Given
        CharacterMove addGangLeadershipAsCM = CharacterMove.createFromMove(addGangLeadership);
        mockCharacter.setAllowedImprovements(1);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(addGangLeadershipAsCM);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(addGangLeadershipAsCM.getId()), List.of());

        // Then
        assertNotNull(returnedCharacter.getPlaybookUniques().getAngelKit());
        assertEquals(gangCreator.getDefaultSize(), returnedCharacter.getPlaybookUniques().getGang().getSize());
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(addGangLeadership.getName())));
        assertTrue(returnedCharacter.getCharacterMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(leadership.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldRemoveGangAndLeadership_onRemoveCharacterImprovement() {
        // Given
        CharacterStat mockCoolStat = CharacterStat.builder()
                .id("mock-cool-stat-id")
                .stat(StatType.COOL)
                .value(2)
                .build();

        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stat-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCoolStat))
                .build();

        Gang mockGang = Gang.builder().id(new ObjectId().toString()).build();
        mockCharacter.setStatsBlock(mockStatsBlock);
        mockCharacter.setAllowedImprovements(1);
        CharacterMove addGangLeadershipAsCM = CharacterMove.createFromMove(addGangLeadership);
        CharacterMove leadershipAsCM = CharacterMove.createFromMove(leadership);
        mockCharacter.setImprovementMoves(List.of(addGangLeadershipAsCM));
        mockCharacter.setCharacterMoves(List.of(leadershipAsCM));
        mockPlaybookUnique.setGang(mockGang);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertNotNull(returnedCharacter.getPlaybookUniques().getAngelKit());
        assertNull(returnedCharacter.getPlaybookUniques().getGang());
        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(addGangLeadership.getName())));
        assertFalse(returnedCharacter.getCharacterMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(leadership.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldAddHoldingAndWealth_onAddCharacterImprovement() {
        // Given
        CharacterMove addHoldingAsCM = CharacterMove.createFromMove(addHolding);
        mockCharacter.setAllowedImprovements(1);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(addHoldingAsCM);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(addHoldingAsCM.getId()), List.of());

        // Then
        assertNotNull(returnedCharacter.getPlaybookUniques().getHolding());
        assertEquals(holdingCreator.getDefaultHoldingSize(), returnedCharacter.getPlaybookUniques().getHolding().getHoldingSize());
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(addHolding.getName())));
        assertTrue(returnedCharacter.getCharacterMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(wealth.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldRemoveHoldingAndWealth_onRemoveCharacterImprovement() {
        // Given
        CharacterStat mockCoolStat = CharacterStat.builder()
                .id("mock-cool-stat-id")
                .stat(StatType.COOL)
                .value(2)
                .build();

        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stat-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCoolStat))
                .build();

        Holding mockHolding = Holding.builder().id(new ObjectId().toString()).build();
        mockCharacter.setStatsBlock(mockStatsBlock);
        mockCharacter.setAllowedImprovements(1);
        CharacterMove addHoldingAsCM = CharacterMove.createFromMove(addHolding);
        CharacterMove wealthAsCM = CharacterMove.createFromMove(wealth);
        mockCharacter.setImprovementMoves(List.of(addHoldingAsCM));
        mockCharacter.setCharacterMoves(List.of(wealthAsCM));
        mockPlaybookUnique.setHolding(mockHolding);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertNotNull(returnedCharacter.getPlaybookUniques().getAngelKit());
        assertNull(returnedCharacter.getPlaybookUniques().getGang());
        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(addHolding.getName())));
        assertFalse(returnedCharacter.getCharacterMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(wealth.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldAddWorkspace_onAddCharacterImprovement() {
        // Given
        CharacterMove addWorkspaceAsCM = CharacterMove.createFromMove(addWorkspace);
        mockCharacter.setAllowedImprovements(1);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(addWorkspaceAsCM);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(addWorkspaceAsCM.getId()), List.of());

        // Then
        assertNotNull(returnedCharacter.getPlaybookUniques().getWorkspace());
        assertEquals(workspaceCreator.getWorkspaceInstructions(), returnedCharacter.getPlaybookUniques().getWorkspace().getWorkspaceInstructions());
        assertEquals(workspaceCreator.getProjectInstructions(), returnedCharacter.getPlaybookUniques().getWorkspace().getProjectInstructions());
        assertTrue(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(addWorkspace.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
    }

    @Test
    void shouldRemoveWorkspace_onRemoveCharacterImprovement() {
        // Given
        CharacterStat mockCoolStat = CharacterStat.builder()
                .id("mock-cool-stat-id")
                .stat(StatType.COOL)
                .value(2)
                .build();

        StatsBlock mockStatsBlock = StatsBlock.builder()
                .id("mock-stat-block-id")
                .statsOptionId("mock-stats-option-id")
                .stats(List.of(mockCoolStat))
                .build();

        Workspace mockWorkspace = Workspace.builder().id(new ObjectId().toString()).build();
        mockCharacter.setStatsBlock(mockStatsBlock);
        mockCharacter.setAllowedImprovements(1);
        CharacterMove addHoldingAsCM = CharacterMove.createFromMove(addWorkspace);
        mockCharacter.setImprovementMoves(List.of(addHoldingAsCM));
        mockPlaybookUnique.setWorkspace(mockWorkspace);
        mockCharacter.setPlaybookUniques(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        setupMockServices();
        when(moveService.findById(anyString())).thenReturn(MovesContent.coolMax2);

        // When
        Character returnedCharacter = gameRoleService.adjustImprovements(mockGameRole.getGameId(),
                mockCharacter.getId(), List.of(MovesContent.coolMax2.getId()), List.of());

        // Then
        assertNotNull(returnedCharacter.getPlaybookUniques().getAngelKit());
        assertNull(returnedCharacter.getPlaybookUniques().getWorkspace());
        assertFalse(returnedCharacter.getImprovementMoves().stream()
                .anyMatch(characterMove -> characterMove.getName().equals(addWorkspace.getName())));
        verifyMockServices();
        verify(moveService, times(1)).findById(anyString());
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
        CharacterStat mockCharacterStatCool = CharacterStat.builder().stat(StatType.COOL).value(1).build();
        CharacterStat mockCharacterStatHard = CharacterStat.builder().stat(StatType.HARD).value(1).build();
        CharacterStat mockCharacterStatHot = CharacterStat.builder().stat(StatType.HOT).value(1).build();
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