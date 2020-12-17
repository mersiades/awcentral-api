package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.enums.Threats;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.repositories.*;
import com.mersiades.awcdata.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@Order(value = 1)
@Profile("dev")
public class MockDataLoader implements CommandLineRunner {

    private final GameService gameService;
    private final GameRoleService gameRoleService;
    private final ThreatService threatService;
    private final NpcService npcService;
    private final UserService userService;

    final String KEYCLOAK_ID_1 = System.getenv("DAVE_ID");
    final String KEYCLOAK_ID_2 = System.getenv("SARA_ID");
    final String MOCK_GAME_1_ID = "0ca6cc54-77a5-4d6e-ba2e-ee1543d6a249";
    final String MOCK_GAME_2_ID = "ecb645d2-06d3-46dc-ad7f-20bbd167085d";
    final String DAVE_AS_PLAYER_ID = "2a7aba8d-f6e8-4880-8021-99809c800acc";

    @Autowired
    UserRepository userRepository;

    @Autowired
    ThreatRepository threatRepository;

    @Autowired
    NpcRepository npcRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameRoleRepository gameRoleRepository;

    public MockDataLoader(GameService gameService,
                          GameRoleService gameRoleService,
                          ThreatService threatService,
                          NpcService npcService,
                          UserService userService) {
        this.gameService = gameService;
        this.gameRoleService = gameRoleService;
        this.threatService = threatService;
        this.npcService = npcService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
         loadMockData();

        System.out.println("Character count: " + Objects.requireNonNull(characterRepository.count().block()).toString());
        System.out.println("Game count: " + Objects.requireNonNull(gameRepository.count().block()).toString());
        System.out.println("GameRole count: " + Objects.requireNonNull(gameRoleRepository.count().block()).toString());
        System.out.println("Npc count: " + Objects.requireNonNull(npcRepository.count().block()).toString());
        System.out.println("Threat count: " + Objects.requireNonNull(threatRepository.count().block()).toString());
        System.out.println("User count: " + Objects.requireNonNull(userRepository.count().block()).toString());
    }

    private void loadMockData() {

        // -------------------------------------- Set up mock Users -------------------------------------- //
        User mockUser1 = User.builder().id(KEYCLOAK_ID_1).build();

        User mockUser2 = User.builder().id(KEYCLOAK_ID_2).build();

        // ------------------------------ Set up mock Game 1 with Game Roles ----------------------------- //
        Game mockGame1 = Game.builder().id(MOCK_GAME_1_ID).name("Mock Game 1").build();

        GameRole daveAsMC = GameRole.builder().id(DAVE_AS_PLAYER_ID).role(Roles.MC).build();
        GameRole sarahAsPlayer = GameRole.builder().id(UUID.randomUUID().toString()).role(Roles.PLAYER).build();

        Npc mockNpc1 = new Npc(daveAsMC, "Vision", "Badass truck driver");
        Npc mockNpc2 = new Npc(daveAsMC, "Nbeke");

        Threat mockThreat1 = new Threat("Tum Tum", Threats.WARLORD, "Slaver: to own and sell people");
        Threat mockThreat2 = new Threat("Gnarly", Threats.GROTESQUE, "Cannibal: craves satiety and plenty");


        daveAsMC.getNpcs().add(mockNpc1);
        daveAsMC.getNpcs().add(mockNpc2);
        daveAsMC.getThreats().add(mockThreat1);
        daveAsMC.getThreats().add(mockThreat2);

        mockGame1.getGameRoles().add(daveAsMC);
        mockGame1.getGameRoles().add(sarahAsPlayer);
        gameService.save(mockGame1).block();

        mockUser1.getGameRoles().add(daveAsMC);
//        userService.save(mockUser1);

        mockUser2.getGameRoles().add(sarahAsPlayer);
//        userService.save(mockUser2);


        daveAsMC.setUser(mockUser1);
        daveAsMC.setGame(mockGame1);
        sarahAsPlayer.setGame(mockGame1);
        sarahAsPlayer.setUser(mockUser2);
        gameRoleService.saveAll(Flux.just(daveAsMC, sarahAsPlayer)).blockLast();

        threatService.saveAll(Flux.just(mockThreat1, mockThreat2)).blockLast();
        npcService.saveAll(Flux.just(mockNpc1, mockNpc2)).blockLast();

        // ------------------------------ Set up mock Game 2 with Game Roles ----------------------------- //
        Game mockGame2 = Game.builder().id(MOCK_GAME_2_ID).name("Mock Game 2").build();

        GameRole daveAsPlayer = GameRole.builder().id(UUID.randomUUID().toString()).role(Roles.PLAYER).build();
        GameRole sarahAsMC =  GameRole.builder().id(UUID.randomUUID().toString()).role(Roles.MC).build();

        Npc mockNpc3 = new Npc(sarahAsMC, "Batty", "Overly polite gun for hire");
        Npc mockNpc4 = new Npc(sarahAsMC, "Farley");

        Threat mockThreat3 = new Threat("Fleece", Threats.BRUTE, "Hunting pack: to victimize anyone vulnerable");
        Threat mockThreat4 = new Threat("Wet Rot", Threats.AFFLICTION, "Condition: to expose people to danger");

        sarahAsMC.getNpcs().add(mockNpc3);
        sarahAsMC.getNpcs().add(mockNpc4);
        sarahAsMC.getThreats().add(mockThreat3);
        sarahAsMC.getThreats().add(mockThreat4);

        mockGame2.getGameRoles().add(daveAsPlayer);
        mockGame2.getGameRoles().add(sarahAsMC);
        gameService.save(mockGame2).block();

        mockUser1.getGameRoles().add(daveAsPlayer);
        mockUser2.getGameRoles().add(sarahAsMC);
        userService.saveAll(Flux.just(mockUser1, mockUser2)).blockLast();


        daveAsPlayer.setUser(mockUser1);
        daveAsPlayer.setGame(mockGame2);
        sarahAsMC.setGame(mockGame2);
        sarahAsMC.setUser(mockUser2);
        gameRoleService.saveAll(Flux.just(daveAsPlayer, sarahAsMC)).blockLast();

        threatService.saveAll(Flux.just(mockThreat3, mockThreat4)).blockLast();
        npcService.saveAll(Flux.just(mockNpc3, mockNpc4)).blockLast();

        // ---------------------------------- Add Characters to Players --------------------------------- //
//        Character mockCharacter1 = new Character("October", sarahAsPlayer, Playbooks.ANGEL, "not much gear");
//        sarahAsPlayer.getCharacters().add(mockCharacter1);
//
//        Character mockCharacter2 = new Character("Leah", daveAsPlayer, Playbooks.SAVVYHEAD, "workshop with tools");
//        daveAsPlayer.getCharacters().add(mockCharacter2);
//
//        characterService.save(mockCharacter1);
//        characterService.save(mockCharacter2);

        // ----------------------------------------------------------------------------------------------- //
//        npcService.save(mockNpc1);
//        npcService.save(mockNpc2);
//        npcService.save(mockNpc3);
//        npcService.save(mockNpc4);
//        threatService.save(mockThreat1);
//        threatService.save(mockThreat2);
//        threatService.save(mockThreat3);
//        threatService.save(mockThreat4);


        // -------------------------------------- Print MockUser1 -------------------------------------- //
        printUser(mockUser1);
        System.out.println("\t ********** Game Role 1 **********");
        printGameRole(daveAsMC);
        System.out.println("\t ********** Game Role 2 **********");
        printGameRole(daveAsPlayer);
        // -------------------------------------- Print MockUser2 -------------------------------------- //
        printUser(mockUser2);
        System.out.println("\t ********** Game Role 1 **********");
        printGameRole(sarahAsPlayer);
        System.out.println("\t ********** Game Role 2 **********");
        printGameRole(sarahAsMC);
        // -------------------------------------- Print MockGame1 -------------------------------------- //
        // -------------------------------------- Print MockGame2 -------------------------------------- //
    }

    private void printGameRole(GameRole role) {
        System.out.println("\t Game: " + role.getGame().getName());
        Roles roleType = role.getRole();
        System.out.println("\t Role: " + role.getRole());
        if (roleType == Roles.MC) {
            List<Npc> npcs = role.getNpcs();
            List<Threat> threats = role.getThreats();
            System.out.println("\t This role has " + npcs.size() + " NPCs");
            if (npcs.size() > 0) {
                for (Npc npc : npcs) {
                    System.out.println("\t\t NPC name: " + npc.getName());
                    if (npc.getDescription() != null) {
                        System.out.println("\t\t NPC description: " + npc.getDescription());
                    }
                    System.out.println("\n");
                }
            }
            System.out.println("\t This role has " + threats.size() + " threats");
            if (threats.size() > 0) {
                for (Threat threat : threats) {
                    System.out.println("\t\t Threat name: " + threat.getName());
                    System.out.println("\t\t Threat kind: " + threat.getThreatKind());
                    System.out.println("\t\t Threat impulse: " + threat.getImpulse());
                    System.out.println("\n");
                }
            }
        } else if (roleType == Roles.PLAYER) {
            List<Character> characters = role.getCharacters();
            System.out.println("\t This role has " + characters.size() + " characters");
            if (characters.size() > 0) {
                for (Character character : characters) {
                    System.out.println("\t\t Character name: " + character.getName());
                    System.out.println("\t\t Playbook: " + character.getPlaybook());
                    System.out.println("\t\t Gear: " + character.getGear());
                    System.out.println("\n");
                }
            }
        }
    }

    private void printUser(User user) {
        System.out.println("| ------------- " + user.getId() + " -------------- |");
        System.out.println("ID: " + user.getId());
        System.out.println("GameRoles (#): " + user.getGameRoles().size());
        System.out.println("\n");
    }
}
