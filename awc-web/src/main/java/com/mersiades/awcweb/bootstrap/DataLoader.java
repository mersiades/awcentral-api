package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.enums.Playbooks;
import com.mersiades.awcdata.enums.Roles;
import com.mersiades.awcdata.enums.Threats;
import com.mersiades.awcdata.models.Character;
import com.mersiades.awcdata.models.*;
import com.mersiades.awcdata.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    final String DISCORD_TEXT_CHANNEL_ID_1 = "741573502452105236";
    final String DISCORD_TEXT_CHANNEL_ID_2 = "823458920374529070";
    final String DISCORD_VOICE_CHANNEL_ID_1 = "741573503710527498";
    final String DISCORD_VOICE_CHANNEL_ID_2 = "123876129847590347";
    final String DISCORD_USER_ID_1 = "696484065859076146";
    final String DISCORD_USER_ID_2 = "134523465246534532";

    private final UserService userService;
    private final GameService gameService;
    private final NpcService npcService;
    private final ThreatService threatService;
    private final CharacterService characterService;
    private final PlaybookCreatorService playbookCreatorService;
    private final PlaybookService playbookService;
    private final NameService nameService;
    private final LookService lookService;
    private final StatsOptionService statsOptionService;

    public DataLoader(UserService userService, GameService gameService,
                      NpcService npcService, ThreatService threatService, CharacterService characterService, PlaybookCreatorService playbookCreatorService, PlaybookService playbookService, NameService nameService, LookService lookService, StatsOptionService statsOptionService) {
        this.userService = userService;
        this.gameService = gameService;
        this.npcService = npcService;
        this.threatService = threatService;
        this.characterService = characterService;
        this.playbookCreatorService = playbookCreatorService;
        this.playbookService = playbookService;
        this.nameService = nameService;
        this.lookService = lookService;
        this.statsOptionService = statsOptionService;
    }

    @Override
    public void run(String... args) {
        loadData();
    }

    private void loadData() {
        PlaybookCreator playbookCreatorAngel = playbookCreatorService.findByPlaybookType(Playbooks.ANGEL);
        Playbook playbookAngel = playbookService.findByPlaybookType(Playbooks.ANGEL);
        Set<Name> namesAngel = nameService.findAllByPlaybookType(Playbooks.ANGEL);
        Set<Look> looksAngel = lookService.findAllByPlaybookType(Playbooks.ANGEL);
        Set<StatsOption> statsOptionsAngel = statsOptionService.findAllByPlaybookType(Playbooks.ANGEL);

        for(StatsOption statsOption: statsOptionsAngel) {
            playbookCreatorAngel.getStatsOptions().add(statsOption);
            statsOption.setPlaybookCreator(playbookCreatorAngel);
            statsOptionService.save(statsOption);
        }

        namesAngel.forEach(name -> {
            name.setPlaybookCreator(playbookCreatorAngel);
            nameService.save(name);
            playbookCreatorAngel.getNames().add(name);
        });

        looksAngel.forEach(look -> {
            look.setPlaybookCreator(playbookCreatorAngel);
            lookService.save(look);
            playbookCreatorAngel.getLooks().add(look);
        });

        playbookAngel.setCreator(playbookCreatorAngel);
        playbookCreatorAngel.setPlaybook(playbookAngel);
        playbookCreatorService.save(playbookCreatorAngel);
        playbookService.save(playbookAngel);

        Set<StatsOption> statsOptions = playbookAngel.getCreator().getStatsOptions();

        System.out.println("|-------------- ANGEL STAT OPTIONS --------------|");
        statsOptions.forEach(statsOption -> {

            System.out.println("PB: " + statsOption.getPlaybookType());
            System.out.println("COOL: " + statsOption.getCOOL());
            System.out.println("HARD: " + statsOption.getHARD());
            System.out.println("HOT: " + statsOption.getHOT());
            System.out.println("SHARP: " + statsOption.getSHARP());
            System.out.println("WEIRD: " + statsOption.getWEIRD() + "\n");
        });


        // -------------------------------------- Set up mock Users -------------------------------------- //
        User mockUser1 = new User();

        mockUser1.setDiscordId(DISCORD_USER_ID_1);

        User mockUser2 = new User();
        mockUser2.setDiscordId(DISCORD_USER_ID_2);

        userService.save(mockUser1);
        userService.save(mockUser2);

        // ------------------------------ Set up mock Game 1 with Game Roles ----------------------------- //
        Game mockGame1 = new Game(DISCORD_TEXT_CHANNEL_ID_1, DISCORD_VOICE_CHANNEL_ID_1, "Mock Game 1");

        GameRole daveAsMC = new GameRole(Roles.MC, mockGame1, mockUser1);
        Npc mockNpc1 = new Npc(daveAsMC, "Vision", "Badass truck driver");
        Npc mockNpc2 = new Npc(daveAsMC, "Nbeke");
        daveAsMC.getNpcs().add(mockNpc1);
        daveAsMC.getNpcs().add(mockNpc2);
        Threat mockThreat1 = new Threat(daveAsMC, "Tum Tum", Threats.WARLORD, "Slaver: to own and sell people");
        Threat mockThreat2 = new Threat(daveAsMC, "Gnarly", Threats.GROTESQUE, "Cannibal: craves satiety and plenty");
        daveAsMC.getThreats().add(mockThreat1);
        daveAsMC.getThreats().add(mockThreat2);

        mockGame1.getGameRoles().add(daveAsMC);
        mockUser1.getGameRoles().add(daveAsMC);

        GameRole sarahAsPlayer = new GameRole(Roles.PLAYER, mockGame1, mockUser2);
        mockGame1.getGameRoles().add(sarahAsPlayer);
        mockUser2.getGameRoles().add(sarahAsPlayer);

        gameService.save(mockGame1);

        // ------------------------------ Set up mock Game 2 with Game Roles ----------------------------- //
        Game mockGame2 = new Game(DISCORD_TEXT_CHANNEL_ID_2, DISCORD_VOICE_CHANNEL_ID_2, "Mock Game 2");

        GameRole daveAsPlayer = new GameRole(Roles.PLAYER, mockGame2, mockUser1);
        mockGame2.getGameRoles().add(daveAsPlayer);
        mockUser1.getGameRoles().add(daveAsPlayer);

        GameRole sarahAsMC = new GameRole(Roles.MC, mockGame2, mockUser2);
        Npc mockNpc3 = new Npc(sarahAsMC, "Batty", "Overly polite gun for hire");
        Npc mockNpc4 = new Npc(sarahAsMC, "Farley");
        sarahAsMC.getNpcs().add(mockNpc3);
        sarahAsMC.getNpcs().add(mockNpc4);
        Threat mockThreat3 = new Threat(sarahAsMC, "Fleece", Threats.BRUTE, "Hunting pack: to victimize anyone vulnerable");
        Threat mockThreat4 = new Threat(sarahAsMC, "Wet Rot", Threats.AFFLICTION, "Condition: to expose people to danger");
        sarahAsMC.getThreats().add(mockThreat3);
        sarahAsMC.getThreats().add(mockThreat4);

        mockGame2.getGameRoles().add(sarahAsMC);
        mockUser2.getGameRoles().add(sarahAsMC);

        gameService.save(mockGame2);

        // ---------------------------------- Add Characters to Players --------------------------------- //
        Character mockCharacter1 = new Character("October", sarahAsPlayer, Playbooks.ANGEL, "not much gear");
        sarahAsPlayer.getCharacters().add(mockCharacter1);

        Character mockCharacter2 = new Character("Leah", daveAsPlayer, Playbooks.SAVVYHEAD, "workshop with tools");
        daveAsPlayer.getCharacters().add(mockCharacter2);

        characterService.save(mockCharacter1);
        characterService.save(mockCharacter2);

        // ----------------------------------------------------------------------------------------------- //
        mockUser1.getGames().add(mockGame1);
        mockUser1.getGames().add(mockGame2);
        mockUser2.getGames().add(mockGame1);
        mockUser2.getGames().add(mockGame2);
        npcService.save(mockNpc1);
        npcService.save(mockNpc2);
        npcService.save(mockNpc3);
        npcService.save(mockNpc4);
        threatService.save(mockThreat1);
        threatService.save(mockThreat2);
        threatService.save(mockThreat3);
        threatService.save(mockThreat4);


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
            Set<Npc> npcs = role.getNpcs();
            Set<Threat> threats = role.getThreats();
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
            Set<Character> characters = role.getCharacters();
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
        System.out.println("Discord ID: " + user.getDiscordId());
        Set<Game> davesGames = user.getGames();
        System.out.println(user.getId() + " is playing in " + davesGames.size() + " games");
        printUsersGame(davesGames);
        System.out.println("\n");
    }

    private void printUsersGame(Set<Game> games) {
        int i = 1;
        for (Game game : games) {
            System.out.println("\t ********** Game " + i + " **********");
            i++;
            System.out.println("\t * Game ID : " + game.getId());
            System.out.println("\t * Game text channel: " + game.getTextChannelId());
            System.out.println("\t * Game voice channel: " + game.getVoiceChannelId());
            System.out.println("\t * Game name: " + game.getName());
            if (i != games.size()) {
                System.out.println("\t ****************************");
            }
        }
    }
}
