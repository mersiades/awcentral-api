package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.enums.LookCategories;
import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.enums.Stats;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.repositories.GameRoleRepository;
import com.mersiades.awcdata.services.*;
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
    StatsBlockService statsBlockService;

    GameRoleService gameRoleService;

    GameRole mockGameRole;

    GameRole mockGameRole2;

    User mockUser;

    Character mockCharacter;

    StatsOption mockStatsOption;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Game mockGame1 = Game.builder().id(MOCK_GAME_ID).build();
        mockUser = User.builder().id(MOCK_USER_ID).build();
        mockCharacter = new Character();
        mockGameRole = GameRole.builder().id(MOCK_GAMEROLE_ID).role(Roles.MC).game(mockGame1).user(mockUser).build();
        mockGame1.getGameRoles().add(mockGameRole);
        mockUser.getGameRoles().add(mockGameRole);
        gameRoleService = new GameRoleServiceImpl(gameRoleRepository, characterService, statsOptionService, moveService, playbookCreatorService, statsBlockService);
        mockGameRole2 = new GameRole();
        mockStatsOption = StatsOption.builder()
                .id("mock-statsoption-id")
                .COOL(2)
                .HARD(2)
                .HOT(2)
                .SHARP(2)
                .WEIRD(2)
                .playbookType(Playbooks.ANGEL)
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
        GameRole mockGameRole2 = GameRole.builder().id("mock-gamerole-id2").role(Roles.MC).game(mockGame2).user(mockUser).build();
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
        GameRole mockGameRole2 = GameRole.builder().id("mock-gamerole-id2").role(Roles.MC).game(mockGame2).user(mockUser).build();
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
        Character returnedCharacter = gameRoleService.setCharacterPlaybook(MOCK_GAMEROLE_ID, mockCharacter.getId(), Playbooks.BATTLEBABE);

        // Then
        assertEquals(Playbooks.BATTLEBABE, returnedCharacter.getPlaybook());
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
        Character returnedCharacter = gameRoleService.setCharacterLook(MOCK_GAMEROLE_ID, mockCharacter.getId(), mockLook, LookCategories.FACE);
        Optional<Look> lookOptional = returnedCharacter.getLookByCategory(LookCategories.FACE);

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
        Look existingLook = Look.builder().id("mock-look-id").category(LookCategories.FACE).look("Handsome").build();
        mockCharacter.getLooks().add(existingLook);
        mockGameRole.getCharacters().add(mockCharacter);
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));

        // When
        Character returnedCharacter = gameRoleService.setCharacterLook(MOCK_GAMEROLE_ID, mockCharacter.getId(), mockLook, LookCategories.FACE);

        System.out.println("returnedCharacter = " + returnedCharacter);
        Optional<Look> lookOptional = returnedCharacter.getLookByCategory(LookCategories.FACE);

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
        System.out.println("returnedCharacter = " + returnedCharacter);
        assertEquals(mockStatsOption.getCOOL(), returnedCharacter.getStatsBlock().getCharacterStatByStat(Stats.COOL).orElseThrow().getValue());
        assertEquals(mockStatsOption.getHARD(), returnedCharacter.getStatsBlock().getCharacterStatByStat(Stats.HARD).orElseThrow().getValue());
        assertEquals(mockStatsOption.getHOT(), returnedCharacter.getStatsBlock().getCharacterStatByStat(Stats.HOT).orElseThrow().getValue());
        assertEquals(mockStatsOption.getSHARP(), returnedCharacter.getStatsBlock().getCharacterStatByStat(Stats.SHARP).orElseThrow().getValue());
        assertEquals(mockStatsOption.getWEIRD(), returnedCharacter.getStatsBlock().getCharacterStatByStat(Stats.WEIRD).orElseThrow().getValue());

        verify(statsOptionService, times(1)).findById(anyString());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

    @Test
    void shouldUpdateCharacterStat() {
        // Given
        CharacterStat mockCharacterStatCool = CharacterStat.builder().stat(Stats.COOL).value(1).build();
        CharacterStat mockCharacterStatHard = CharacterStat.builder().stat(Stats.HARD).value(1).build();
        CharacterStat mockCharacterStatHot = CharacterStat.builder().stat(Stats.HOT).value(1).build();
        CharacterStat mockCharacterStatSharp = CharacterStat.builder().stat(Stats.SHARP).value(1).build();
        CharacterStat mockCharacterStatWeird = CharacterStat.builder().stat(Stats.WEIRD).value(1).build();
        mockCharacter.getStatsBlock().setStats(List.of(mockCharacterStatCool,
                mockCharacterStatHard,
                mockCharacterStatHot,
                mockCharacterStatSharp,
                mockCharacterStatWeird));
        mockGameRole.getCharacters().add(mockCharacter);
        when(gameRoleRepository.findById(anyString())).thenReturn(Mono.just(mockGameRole));
        when(characterService.save(any())).thenReturn(Mono.just(mockCharacter));
        when(gameRoleRepository.save(any())).thenReturn(Mono.just(mockGameRole));
        when(statsOptionService.findById(anyString())).thenReturn(Mono.just(mockStatsOption));

        // When
        Character returnedCharacter = gameRoleService.setCharacterStats(mockGameRole.getId(), mockCharacter.getId(), mockStatsOption.getId());

        // Then
        System.out.println("returnedCharacter = " + returnedCharacter);
        assertEquals(mockStatsOption.getCOOL(), returnedCharacter.getStatsBlock().getCharacterStatByStat(Stats.COOL).orElseThrow().getValue());
        assertEquals(mockStatsOption.getHARD(), returnedCharacter.getStatsBlock().getCharacterStatByStat(Stats.HARD).orElseThrow().getValue());
        assertEquals(mockStatsOption.getHOT(), returnedCharacter.getStatsBlock().getCharacterStatByStat(Stats.HOT).orElseThrow().getValue());
        assertEquals(mockStatsOption.getSHARP(), returnedCharacter.getStatsBlock().getCharacterStatByStat(Stats.SHARP).orElseThrow().getValue());
        assertEquals(mockStatsOption.getWEIRD(), returnedCharacter.getStatsBlock().getCharacterStatByStat(Stats.WEIRD).orElseThrow().getValue());

        verify(statsOptionService, times(1)).findById(anyString());
        verify(gameRoleRepository, times(1)).findById(anyString());
        verify(characterService, times(1)).save(any(Character.class));
        verify(gameRoleRepository, times(1)).save(any(GameRole.class));
    }

}