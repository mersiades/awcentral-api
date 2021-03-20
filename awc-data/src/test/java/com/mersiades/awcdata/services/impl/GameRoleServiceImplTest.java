package com.mersiades.awcdata.services.impl;

import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.models.uniquecreators.AngelKitCreator;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mersiades.awccontent.constants.MoveNames.*;
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

    StatsOption mockStatsOption;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Game mockGame1 = Game.builder().id(MOCK_GAME_ID).build();
        mockUser = User.builder().id(MOCK_USER_ID).build();
        mockCharacter = Character.builder().id(UUID.randomUUID().toString()).build();
        mockGameRole = GameRole.builder().id(MOCK_GAMEROLE_ID).role(RoleType.MC).game(mockGame1).user(mockUser).build();
        mockGame1.getGameRoles().add(mockGameRole);
        mockUser.getGameRoles().add(mockGameRole);
        gameRoleService = new GameRoleServiceImpl(gameRoleRepository, characterService, statsOptionService, moveService, playbookCreatorService, statModifierService);
        mockGameRole2 = new GameRole();
        mockStatsOption = StatsOption.builder()
                .id("mock-statsoption-id")
                .COOL(2)
                .HARD(2)
                .HOT(2)
                .SHARP(2)
                .WEIRD(2)
                .playbookType(PlaybookType.ANGEL)
                .build();
    }


    @Test
    void shouldFindAllGameRoles() {
        // Given
        when(gameRoleRepository.findAll()).thenReturn(Flux.just(mockGameRole, mockGameRole2));

        // When
        List<GameRole> gameRoles = gameRoleService.findAll().collectList().block();

        // Then
        assert gameRoles != null;
        assertEquals(2, gameRoles.size());
        verify(gameRoleRepository, times(1)).findAll();
    }

    @Test
    void shouldFindGameRoleById() {
        // Given
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));

        // When
        GameRole gameRole = gameRoleService.findById(MOCK_GAMEROLE_ID).block();

        // Then
        assertNotNull(gameRole, "Null GameRole returned");
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(gameRoleRepository, never()).findAll();
    }

    @Test
    void shouldSaveGameRole() {
        // Given
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        GameRole savedGameRole = gameRoleService.save(mockGameRole).block();

        // Then
        assert savedGameRole != null;
        assertEquals(MOCK_GAMEROLE_ID, savedGameRole.getId());
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldSaveGameRoles() {
        // Given
        when(gameRoleRepository.saveAll(any(Publisher.class)))
                .thenReturn(Flux.just(mockGameRole, mockGameRole2));

        // When
        List<GameRole> savedGameRoles = gameRoleService.saveAll(Flux.just(mockGameRole, mockGameRole2))
                .collectList().block();

        // Then
        assert savedGameRoles != null;
        assertEquals(2, savedGameRoles.size());
        verify(gameRoleRepository, times(1)).saveAll(any(Publisher.class));
    }

    // This test stopped working when I added .block() to gameRoleRepository.delete()
    @Test
    @Disabled
    void shouldDeleteGameRole() {
        System.out.println("mockGameRole = " + mockGameRole);
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
    void shouldFindAllGameRolesByUser() {
        // Given
        Game mockGame2 = new Game();
        GameRole mockGameRole2 = GameRole.builder().id("mock-gamerole-id2").role(RoleType.MC).game(mockGame2).user(mockUser).build();
        when(gameRoleRepository.findAllByUser(any())).thenReturn(Flux.just(mockGameRole, mockGameRole2));

        // When
        List<GameRole> returnedGameRoles = gameRoleService.findAllByUser(mockUser).collectList().block();

        // Then
        assert returnedGameRoles != null;
        assertEquals(2, returnedGameRoles.size());
        verify(gameRoleRepository, times(1)).findAllByUser(any(User.class));
    }

    @Test
    void shouldFindAllGameRolesByUserId() {
        // Given
        Game mockGame2 = new Game();
        GameRole mockGameRole2 = GameRole.builder().id("mock-gamerole-id2").role(RoleType.MC).game(mockGame2).user(mockUser).build();
        when(gameRoleRepository.findAllByUserId(anyString())).thenReturn(Flux.just(mockGameRole, mockGameRole2));

        // When
        List<GameRole> returnedGameRoles = gameRoleService.findAllByUserId(mockUser.getId()).collectList().block();

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
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        GameRole returnedGameRole = gameRoleService.addThreat(mockGameRole.getId(), mockThreat).block();

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
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        GameRole returnedGameRole = gameRoleService.addNpc(mockGameRole.getId(), mockNpc).block();

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
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService.addNewCharacter(MOCK_GAMEROLE_ID);

        // Then
        assertNotNull(returnedCharacter, "Null Character returned");
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldSetCharacterPlaybook() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService.setCharacterPlaybook(MOCK_GAMEROLE_ID, mockCharacter.getId(), PlaybookType.BATTLEBABE);

        // Then
        assertEquals(PlaybookType.BATTLEBABE, returnedCharacter.getPlaybook());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldSetCharacterName() {
        // Given
        String mockCharacterName = "Mock Character Name";
        mockGameRole.getCharacters().add(mockCharacter);
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService.setCharacterName(MOCK_GAMEROLE_ID, mockCharacter.getId(), mockCharacterName);

        // Then
        assertEquals(mockCharacterName, returnedCharacter.getName());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldSetNewCharacterLook() {
        // Given
        Look mockLook = Look.builder()
                .id("mock-look-id")
                .look("handsome face")
                .category(LookType.FACE)
                .build();
        mockGameRole.getCharacters().add(mockCharacter);
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService.setCharacterLook(MOCK_GAMEROLE_ID, mockCharacter.getId(), mockLook);
        Optional<Look> lookOptional = returnedCharacter.getLookByCategory(LookType.FACE);

        // Then
        assertTrue(lookOptional.isPresent());
        Look savedLook = lookOptional.get();
        assertEquals(mockLook, savedLook);
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldUpdateCharacterLook() {
        // Given
        Look mockLook = Look.builder()
                .id("mock-look-id")
                .look("ugly face")
                .category(LookType.FACE)
                .build();
        Look existingLook = Look.builder().id("mock-look-id").category(LookType.FACE).look("handsome face").build();
        mockCharacter.getLooks().add(existingLook);
        mockGameRole.getCharacters().add(mockCharacter);
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService.setCharacterLook(MOCK_GAMEROLE_ID, mockCharacter.getId(), mockLook);

        System.out.println("returnedCharacter = " + returnedCharacter);
        Optional<Look> lookOptional = returnedCharacter.getLookByCategory(LookType.FACE);

        // Then
        assertTrue(lookOptional.isPresent());
        Look savedLook = lookOptional.get();
        assertEquals(mockLook, savedLook);
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldAddNewCharacterStat() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));
        when(statsOptionService.findById(anyString())).thenReturn(Mono.just(mockStatsOption));

        // When
        Character returnedCharacter = gameRoleService.setCharacterStats(mockGameRole.getId(), mockCharacter.getId(), mockStatsOption.getId());

        // Then
        assertEquals(mockStatsOption.getCOOL(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.COOL)).findFirst().orElseThrow().getValue());
        assertEquals(mockStatsOption.getHARD(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.HARD)).findFirst().orElseThrow().getValue());
        assertEquals(mockStatsOption.getHOT(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.HOT)).findFirst().orElseThrow().getValue());
        assertEquals(mockStatsOption.getSHARP(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.SHARP)).findFirst().orElseThrow().getValue());
        assertEquals(mockStatsOption.getWEIRD(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.WEIRD)).findFirst().orElseThrow().getValue());

        verify(statsOptionService, times(1)).findById(anyString());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldUpdateCharacterStat() {
        // Given
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
        mockGameRole.getCharacters().add(mockCharacter);
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));
        when(statsOptionService.findById(anyString())).thenReturn(Mono.just(mockStatsOption));

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterStats(mockGameRole.getId(), mockCharacter.getId(), mockStatsOption.getId());

        // Then
        assertEquals(mockStatsOption.getCOOL(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.COOL)).findFirst().orElseThrow().getValue());
        assertEquals(mockStatsOption.getHARD(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.HARD)).findFirst().orElseThrow().getValue());
        assertEquals(mockStatsOption.getHOT(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.HOT)).findFirst().orElseThrow().getValue());
        assertEquals(mockStatsOption.getSHARP(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.SHARP)).findFirst().orElseThrow().getValue());
        assertEquals(mockStatsOption.getWEIRD(), returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.WEIRD)).findFirst().orElseThrow().getValue());

        verify(statsOptionService, times(1)).findById(anyString());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    public void shouldSetCharacterGear() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);

        String item1 = "A big knife";
        String item2 = "Mechanic's toolkit";

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterGear(mockGameRole.getId(), mockCharacter.getId(), List.of(item1, item2));

        // Then
        assertEquals(2, returnedCharacter.getGear().size());
        assertTrue(returnedCharacter.getGear().contains(item1));
        assertTrue(returnedCharacter.getGear().contains(item2));
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    public void shouldSetCharacterMoves() {
        // Given
        mockCharacter.setPlaybook(PlaybookType.ANGEL);
        mockGameRole.getCharacters().add(mockCharacter);

        String moveId1 = "angel-special-id";
        String moveId2 = "sixth-sense-id";
        String moveId3 = "infirmary-id";

        RollModifier sixthSenseMod = RollModifier.builder().id(UUID.randomUUID().toString()).statToRollWith(StatType.SHARP).build();
        Move angelSpecial = Move.builder()
                .id(moveId1)
                .name("ANGEL SPECIAL")
                .description("If you and")
                .stat(null)
                .kind(MoveType.CHARACTER)
                .playbook(PlaybookType.ANGEL)
                .build();
        Move sixthSense = Move.builder()
                .id(moveId2)
                .name("SIXTH SENSE")
                .description("_**Sixth sense**_: ")
                .rollModifier(sixthSenseMod)
                .kind(MoveType.CHARACTER)
                .playbook(PlaybookType.ANGEL)
                .build();
        Move infirmary = Move.builder()
                .id(moveId3)
                .name("INFIRMARY")
                .description("_**Infirmary**_:")
                .stat(null)
                .kind(MoveType.CHARACTER)
                .playbook(PlaybookType.ANGEL)
                .build();

        List<Move> angelMoves = List.of(angelSpecial, sixthSense, infirmary);

        AngelKitCreator angelKitCreator = AngelKitCreator.builder()
                .id(UUID.randomUUID().toString())
                .angelKitInstructions("Your angel kit has...")
                .startingStock(6)
                .build();

        PlaybookUniqueCreator angelUniqueCreator = PlaybookUniqueCreator.builder()
                .id(UUID.randomUUID().toString())
                .type(UniqueType.ANGEL_KIT)
                .angelKitCreator(angelKitCreator)
                .build();

        GearInstructions angelGearInstructions = GearInstructions.builder()
                .id(UUID.randomUUID().toString())
                .build();

        PlaybookCreator angelCreator = PlaybookCreator.builder()
                .playbookType(PlaybookType.ANGEL)
                .gearInstructions(angelGearInstructions)
                .improvementInstructions("Whenever you roll ")
                .movesInstructions("You get all the basic moves.")
                .hxInstructions("Everyone introduces their")
                .playbookUniqueCreator(angelUniqueCreator)
                .optionalMoves(angelMoves)
                .defaultMoveCount(1)
                .moveChoiceCount(2)
                .build();

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

        when(playbookCreatorService.findByPlaybookType(any(PlaybookType.class))).thenReturn(Mono.just(angelCreator));
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));
        when(statModifierService.findById(anyString())).thenReturn(Mono.empty());

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterMoves(mockGameRole.getId(), mockCharacter.getId(), List.of(moveId1, moveId2, moveId3));

        // Then
        assertEquals(3, returnedCharacter.getCharacterMoves().size());
        List<CharacterMove> returnedMoves = returnedCharacter.getCharacterMoves();

        returnedMoves.forEach(characterMove -> {
            assertTrue(characterMove.getName().equals("ANGEL SPECIAL") ||
                    characterMove.getName().equals("SIXTH SENSE") ||
                    characterMove.getName().equals("INFIRMARY"));
        });
        verify(playbookCreatorService, times(1)).findByPlaybookType(any(PlaybookType.class));
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    public void shouldSetCharacterHx() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        Character mockCharacter2 = Character.builder()
                .id("mock-character-2-id")
                .name("Mock Character 2")
                .playbook(PlaybookType.DRIVER)
                .build();

        Character mockCharacter3 = Character.builder()
                .id("mock-character-3-id")
                .name("Mock Character 3")
                .playbook(PlaybookType.HARDHOLDER)
                .build();

        HxStat hxStat1 = HxStat.builder()
                .characterId(mockCharacter2.getId())
                .characterName(mockCharacter2.getName())
                .hxValue(1)
                .build();

        HxStat hxStat2 = HxStat.builder()
                .characterId(mockCharacter3.getId())
                .characterName(mockCharacter3.getName())
                .hxValue(-1)
                .build();

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterHx(mockGameRole.getId(), mockCharacter.getId(), List.of(hxStat1, hxStat2));

        // Then
        assertEquals(2, returnedCharacter.getHxBlock().size());
        assertTrue(returnedCharacter.getHxBlock().contains(hxStat1));
        assertTrue(returnedCharacter.getHxBlock().contains(hxStat2));
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    public void shouldFinishCharacterCreation() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .finishCharacterCreation(mockGameRole.getId(), mockCharacter.getId());

        // Then
        assertTrue(returnedCharacter.getHasCompletedCharacterCreation());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));

    }


    // --------------------------------------- Setting Playbook Uniques ------------------------------------- //



    @Test
    public void shouldSetAngelKit() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);

        MoveAction stabilizeAndHealAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.ROLL)
                .rollType(RollType.STOCK)
                .statToRollWith(null)
                .build();
        Move stabilizeAndHeal = Move.builder().name(stabilizeAndHealName)
                .description("_**stabilize and heal someone at 9:00")
                .moveAction(stabilizeAndHealAction)
                .playbook(PlaybookType.ANGEL)
                .kind(MoveType.UNIQUE).build();
        MoveAction speedTheRecoveryOfSomeoneAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.STOCK)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move speedTheRecoveryOfSomeone = Move.builder().name(speedRecoveryName)
                .description("_**speed the recovery of someone at 3:00 or 6:0")
                .playbook(PlaybookType.ANGEL)
                .moveAction(speedTheRecoveryOfSomeoneAction)
                .kind(MoveType.UNIQUE).build();
        MoveAction reviveSomeoneAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.STOCK)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move reviveSomeone = Move.builder().name(reviveSomeoneName)
                .description("_**revive someone whose life")
                .playbook(PlaybookType.ANGEL)
                .moveAction(reviveSomeoneAction)
                .kind(MoveType.UNIQUE).build();
        MoveAction treatAnNpcAction = MoveAction.builder()
                .id(UUID.randomUUID().toString())
                .actionType(MoveActionType.STOCK)
                .rollType(null)
                .statToRollWith(null)
                .build();
        Move treatAnNpc = Move.builder().name(treatNpcName)
                .description("_**treat an NPC ")
                .playbook(PlaybookType.ANGEL)
                .moveAction(treatAnNpcAction)
                .kind(MoveType.UNIQUE).build();

        int stock = 6;

        Boolean hasSupplier = false;

        when(moveService.findAllByPlaybookAndKind(any(PlaybookType.class), any(MoveType.class)))
                .thenReturn(Flux.just(stabilizeAndHeal, speedTheRecoveryOfSomeone, reviveSomeone, treatAnNpc));
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setAngelKit(mockGameRole.getId(), mockCharacter.getId(), stock, hasSupplier);

        // Then
        assertEquals(List.of(stabilizeAndHeal, speedTheRecoveryOfSomeone, reviveSomeone, treatAnNpc),
                returnedCharacter.getPlaybookUnique().getAngelKit().getAngelKitMoves());

        verify(moveService, times(1)).findAllByPlaybookAndKind(any(PlaybookType.class), any(MoveType.class));
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    public void shouldSetBrainerGear() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);

        String item1 = "Weird glove";
        String item2 = "Funny hat";

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setBrainerGear(mockGameRole.getId(), mockCharacter.getId(), List.of(item1, item2));

        // Then
        assertEquals(2, returnedCharacter.getPlaybookUnique().getBrainerGear().getBrainerGear().size());
        assertTrue(returnedCharacter.getPlaybookUnique().getBrainerGear().getBrainerGear().contains(item1));
        assertTrue(returnedCharacter.getPlaybookUnique().getBrainerGear().getBrainerGear().contains(item2));
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    public void shouldSetCustomWeapons() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        String weapon1 = "Crossbow with scope";
        String weapon2 = "Ornate staff";

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setCustomWeapons(mockGameRole.getId(), mockCharacter.getId(), List.of(weapon1, weapon2));

        // Then
        assertEquals(2, returnedCharacter.getPlaybookUnique().getCustomWeapons().getWeapons().size());
        assertTrue(returnedCharacter.getPlaybookUnique().getCustomWeapons().getWeapons().contains(weapon1));
        assertTrue(returnedCharacter.getPlaybookUnique().getCustomWeapons().getWeapons().contains(weapon2));
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));

    }

    @Test
    void shouldSetEstablishment() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);
        Establishment mockEstablishment = Establishment.builder().id("mock-establishment-id").build();

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setEstablishment(mockGameRole.getId(), mockCharacter.getId(), mockEstablishment);

        // Then
        assertEquals(mockEstablishment.getId(), returnedCharacter.getPlaybookUnique().getEstablishment().getId());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    // TODO: shouldUpdateEstablishment

    @Test
    void shouldSetFollowers() {
        mockGameRole.getCharacters().add(mockCharacter);
        Followers mockFollowers = Followers.builder().id("mock-followers-id").build();

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setFollowers(mockGameRole.getId(), mockCharacter.getId(), mockFollowers);

        // Then
        assertEquals(mockFollowers.getId(), returnedCharacter.getPlaybookUnique().getFollowers().getId());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    // TODO: shouldUpdateFollowers

    @Test
    void shouldSetGang() {
        mockGameRole.getCharacters().add(mockCharacter);
        Gang mockGang = Gang.builder().id("mock-gang-id").build();

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setGang(mockGameRole.getId(), mockCharacter.getId(), mockGang);

        // Then
        assertEquals(mockGang.getId(), returnedCharacter.getPlaybookUnique().getGang().getId());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    // TODO: shouldUpdateGang

    @Test
    void shouldSetHolding() {
        mockGameRole.getCharacters().add(mockCharacter);
        Holding mockHolding = Holding.builder().id("mock-holding-id").build();

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setHolding(mockGameRole.getId(), mockCharacter.getId(), mockHolding, 7, 6);

        // Then
        assertEquals(mockHolding.getId(), returnedCharacter.getPlaybookUnique().getHolding().getId());
        assertEquals(7, returnedCharacter.getVehicleCount());
        assertEquals(6, returnedCharacter.getBattleVehicleCount());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    // TODO: shouldUpdateHolding

    @Test
    void shouldSetSkinnerGear() {
        mockGameRole.getCharacters().add(mockCharacter);
        SkinnerGear mockSkinnerGear = SkinnerGear.builder().id("mock-skinner-gear-id").build();

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setSkinnerGear(mockGameRole.getId(), mockCharacter.getId(), mockSkinnerGear);

        // Then
        assertEquals(mockSkinnerGear.getId(), returnedCharacter.getPlaybookUnique().getSkinnerGear().getId());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
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

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setWeapons(mockGameRole.getId(), mockCharacter.getId(), List.of(weapon1, weapon2, weapon3, weapon4));

        // Then
        assertEquals(4, returnedCharacter.getPlaybookUnique().getWeapons().getWeapons().size());
        assertTrue(returnedCharacter.getPlaybookUnique().getWeapons().getWeapons().contains(weapon1));
        assertTrue(returnedCharacter.getPlaybookUnique().getWeapons().getWeapons().contains(weapon2));
        assertTrue(returnedCharacter.getPlaybookUnique().getWeapons().getWeapons().contains(weapon3));
        assertTrue(returnedCharacter.getPlaybookUnique().getWeapons().getWeapons().contains(weapon4));
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    // TODO: shouldUpdateWeapons

    @Test
    void shouldSetWorkspace() {
        mockGameRole.getCharacters().add(mockCharacter);
        Workspace mockWorkspace = Workspace.builder().id("mock-workspace-id").build();

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setWorkspace(mockGameRole.getId(), mockCharacter.getId(), mockWorkspace);

        // Then
        assertEquals(mockWorkspace.getId(), returnedCharacter.getPlaybookUnique().getWorkspace().getId());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    // TODO: shouldUpdateWorkspace

    // ------------------------------------------ Setting Vehicles ---------------------------------------- //

    @Test
    void shouldSetVehicleCount() {
        mockGameRole.getCharacters().add(mockCharacter);
        int mockVehicleCount = 3;

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setVehicleCount(mockGameRole.getId(), mockCharacter.getId(), mockVehicleCount);

        // Then
        assertEquals(mockVehicleCount, returnedCharacter.getVehicleCount());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldSetBattleVehicleCount() {
        mockGameRole.getCharacters().add(mockCharacter);
        int mockBattleVehicleCount = 3;

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setBattleVehicleCount(mockGameRole.getId(), mockCharacter.getId(), mockBattleVehicleCount);

        // Then
        assertEquals(mockBattleVehicleCount, returnedCharacter.getBattleVehicleCount());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldSetVehicle() {
        mockGameRole.getCharacters().add(mockCharacter);
        Vehicle mockVehicle = Vehicle.builder().id("mock-vehicle-id").build();

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setVehicle(mockGameRole.getId(), mockCharacter.getId(), mockVehicle);

        // Then
        assertTrue(returnedCharacter.getVehicles().contains(mockVehicle));
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    // TODO: shouldUpdateVehicle

    @Test
    void shouldSetBattleVehicle() {
        mockGameRole.getCharacters().add(mockCharacter);
        BattleVehicle mockBattleVehicle = BattleVehicle.builder().id("mock-battle-vehicle-id").build();

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setBattleVehicle(mockGameRole.getId(), mockCharacter.getId(), mockBattleVehicle);

        // Then
        assertTrue(returnedCharacter.getBattleVehicles().contains(mockBattleVehicle));
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
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


        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .adjustCharacterHx(mockGameRole.getId(), mockCharacter.getId(), newHxStat);

        // Then
        assertEquals(newHxStat.getHxValue(),
                returnedCharacter.getHxBlock().stream()
                        .filter(hxStat -> hxStat.getCharacterId().equals(newHxStat.getCharacterId()))
                        .findFirst().orElseThrow().getHxValue());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldSetCharacterHarm() {
        mockGameRole.getCharacters().add(mockCharacter);
        CharacterHarm mockCharacterHarm = CharacterHarm.builder()
                .id("mock-character-harm-id")
                .value(3)
                .isStabilized(true)
                .build();

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterHarm(mockGameRole.getId(), mockCharacter.getId(), mockCharacterHarm);

        // Then
        assertEquals(mockCharacterHarm.getValue(), returnedCharacter.getHarm().getValue());
        assertEquals(mockCharacterHarm.getIsStabilized(), returnedCharacter.getHarm().getIsStabilized());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
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

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .toggleStatHighlight(mockGameRole.getId(), mockCharacter.getId(), StatType.COOL);

        // Then
        assertTrue(returnedCharacter.getStatsBlock().getStats().stream()
                .filter(characterStat -> characterStat.getStat().equals(StatType.COOL))
                .findFirst().orElseThrow().getIsHighlighted());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldSetCharacterBarter() {
        mockGameRole.getCharacters().add(mockCharacter);
        int mockBarter = 3;

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setCharacterBarter(mockGameRole.getId(), mockCharacter.getId(), mockBarter);

        // Then
        assertEquals(mockBarter, returnedCharacter.getBarter());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldSetHoldingBarter() {
        Holding mockHolding = Holding.builder().id("mock-holding-id").barter(0).build();
        PlaybookUnique mockPlaybookUnique = PlaybookUnique.builder().holding(mockHolding).build();
        mockCharacter.setPlaybookUnique(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);


        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .setHoldingBarter(mockGameRole.getId(), mockCharacter.getId(), 2);

        // Then
        assertEquals(2, returnedCharacter.getPlaybookUnique().getHolding().getBarter());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldUpdateFollowers() {
        Followers mockFollowers = Followers.builder()
                .id("mock-followers-id")
                .description("old description")
                .followers(100)
                .barter(0).build();
        PlaybookUnique mockPlaybookUnique = PlaybookUnique.builder().followers(mockFollowers).build();
        mockCharacter.setPlaybookUnique(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);

        String newDescription = "new description";
        int newFollowers = 50;
        int newBarter = 2;

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .updateFollowers(mockGameRole.getId(), mockCharacter.getId(), newBarter, newFollowers, newDescription);

        // Then
        assertEquals(newDescription, returnedCharacter.getPlaybookUnique().getFollowers().getDescription());
        assertEquals(newBarter, returnedCharacter.getPlaybookUnique().getFollowers().getBarter());
        assertEquals(newFollowers, returnedCharacter.getPlaybookUnique().getFollowers().getFollowers());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldAddProject() {
        Workspace mockWorkspace = Workspace.builder().id("mock-workspace-id").build();
        PlaybookUnique mockPlaybookUnique = PlaybookUnique.builder().workspace(mockWorkspace).build();
        mockCharacter.setPlaybookUnique(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);
        Project mockProject = Project.builder()
                .id("mock-project-id").build();

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .addProject(mockGameRole.getId(), mockCharacter.getId(), mockProject);

        // Then
        assertTrue(returnedCharacter.getPlaybookUnique().getWorkspace().getProjects().contains(mockProject));
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldRemoveProject() {
        Project mockProject = Project.builder()
                .id("mock-project-id").build();
        Workspace mockWorkspace = Workspace.builder()
                .id("mock-workspace-id")
                .projects(List.of(mockProject))
                .build();
        PlaybookUnique mockPlaybookUnique = PlaybookUnique.builder().workspace(mockWorkspace).build();
        mockCharacter.setPlaybookUnique(mockPlaybookUnique);
        mockGameRole.getCharacters().add(mockCharacter);

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .removeProject(mockGameRole.getId(), mockCharacter.getId(), mockProject);

        // Then
        assertFalse(returnedCharacter.getPlaybookUnique().getWorkspace().getProjects().contains(mockProject));
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldRemoveHold() {
        Hold mockHold = Hold.builder()
                .id("mock-hold-id")
                .rollResult(12)
                .moveDescription("When you read a...")
                .moveName("READ A PERSON")
                .build();
        mockCharacter.getHolds().add(mockHold);
        mockGameRole.getCharacters().add(mockCharacter);

        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService
                .removeHold(mockGameRole.getId(), mockCharacter.getId(), mockHold);

        // Then
        assertFalse(returnedCharacter.getHolds().contains(mockHold));
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

}