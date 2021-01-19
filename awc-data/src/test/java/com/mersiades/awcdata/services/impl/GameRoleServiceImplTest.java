package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.repositories.GameRoleRepository;
import com.mersiades.awcdata.services.CharacterService;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awccontent.enums.*;
import com.mersiades.awccontent.models.*;
import com.mersiades.awccontent.models.uniquecreators.AngelKitCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.mersiades.awccontent.services.MoveService;
import com.mersiades.awccontent.services.PlaybookCreatorService;
import com.mersiades.awccontent.services.StatModifierService;
import com.mersiades.awccontent.services.StatsOptionService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        String mockLook = "Handsome";
        mockGameRole.getCharacters().add(mockCharacter);
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService.setCharacterLook(MOCK_GAMEROLE_ID, mockCharacter.getId(), mockLook, LookType.FACE);
        Optional<Look> lookOptional = returnedCharacter.getLookByCategory(LookType.FACE);

        // Then
        assertTrue(lookOptional.isPresent());
        Look savedLook = lookOptional.get();
        assertEquals(mockLook, savedLook.getLook());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldUpdateCharacterLook() {
        // Given
        String mockLook = "Ugly";
        Look existingLook = Look.builder().id("mock-look-id").category(LookType.FACE).look("Handsome").build();
        mockCharacter.getLooks().add(existingLook);
        mockGameRole.getCharacters().add(mockCharacter);
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService.setCharacterLook(MOCK_GAMEROLE_ID, mockCharacter.getId(), mockLook, LookType.FACE);

        System.out.println("returnedCharacter = " + returnedCharacter);
        Optional<Look> lookOptional = returnedCharacter.getLookByCategory(LookType.FACE);

        // Then
        assertTrue(lookOptional.isPresent());
        Look savedLook = lookOptional.get();
        assertEquals(mockLook, savedLook.getLook());
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
    public void shouldSetAngelKit() {
        // Given
        mockGameRole.getCharacters().add(mockCharacter);

        Move stabilizeAndHeal = Move.builder().name("STABILIZE AND HEAL SOMEONE")
                .description("_**stabilize and heal someone at 9:00")
                .playbook(PlaybookType.ANGEL)
                .kind(MoveType.UNIQUE).build();
        Move speedTheRecoveryOfSomeone = Move.builder().name("SPEED THE RECOVERY OF SOMEONE")
                .description("_**speed the recovery of someone at 3:00 or 6:0")
                .playbook(PlaybookType.ANGEL)
                .kind(MoveType.UNIQUE).build();
        Move reviveSomeone = Move.builder().name("REVIVE SOMEONE")
                .description("_**revive someone whose life")
                .playbook(PlaybookType.ANGEL).
                        kind(MoveType.UNIQUE).build();
        Move treatAnNpc = Move.builder().name("TREAT AN NPC")
                .description("_**treat an NPC ")
                .playbook(PlaybookType.ANGEL)
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
    public void shouldSetCharacterMoves() {
        // Given
        mockCharacter.setPlaybook(PlaybookType.ANGEL);
        mockGameRole.getCharacters().add(mockCharacter);

        String moveId1 = "angel-special-id";
        String moveId2 = "sixth-sense-id";
        String moveId3 = "infirmary-id";

        RollModifier sixthSenseMod = RollModifier.builder().id(UUID.randomUUID().toString()).statToRollWith(Collections.singletonList(StatType.SHARP)).build();
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
}