package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.Npc;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.NpcService;
import com.mersiades.awcdata.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    final Long DISCORD_TEXT_CHANNEL_ID_1 = Long.parseLong("741573502452105236");
    final Long DISCORD_TEXT_CHANNEL_ID_2 = Long.parseLong("823458920374529070");
    final Long DISCORD_VOICE_CHANNEL_ID_1 = Long.parseLong("741573503710527498");
    final Long DISCORD_VOICE_CHANNEL_ID_2 = Long.parseLong("123876129847590347");
    final Long DISCORD_USER_ID_1 = Long.parseLong("696484065859076146");
    final Long DISCORD_USER_ID_2 = Long.parseLong("134523465246534532");

    private final UserService userService;
    private final GameService gameService;
    private final NpcService npcService;

    public DataLoader(UserService userService, GameService gameService,
                      NpcService npcService) {
        this.userService = userService;
        this.gameService = gameService;
        this.npcService = npcService;
    }

    @Override
    public void run(String... args) {
        loadData();
    }

    private void loadData() {
        // -------------------------------------- Set up mock Users -------------------------------------- //
        User mockUser1 = new User();
        mockUser1.setUsername("Dave");
        mockUser1.setDiscourseID(DISCORD_USER_ID_1);

        User mockUser2 = new User();
        mockUser2.setUsername("Sarah");
        mockUser2.setDiscourseID(DISCORD_USER_ID_2);

        userService.save(mockUser1);
        userService.save(mockUser2);

        // ------------------------------ Set up mock Game 1 with Game Roles ----------------------------- //
        Game mockGame1 = new Game(DISCORD_TEXT_CHANNEL_ID_1, DISCORD_VOICE_CHANNEL_ID_1, "Mock Game 1");

        GameRole daveAsMC = new GameRole(GameRole.Role.MC, mockGame1, mockUser1);
        Npc mockNpc1 = new Npc(daveAsMC, "Vision", "Badass truck driver");
        Npc mockNpc2 = new Npc(daveAsMC, "Nbeke");
        daveAsMC.getNpcs().add(mockNpc1);
        daveAsMC.getNpcs().add(mockNpc2);
        mockGame1.getGameRoles().add(daveAsMC);
        mockUser1.getGameRoles().add(daveAsMC);

        GameRole sarahAsPlayer = new GameRole(GameRole.Role.PLAYER, mockGame1, mockUser2);
        mockGame1.getGameRoles().add(sarahAsPlayer);
        mockUser2.getGameRoles().add(sarahAsPlayer);

        gameService.save(mockGame1);

        // ------------------------------ Set up mock Game 2 with Game Roles ----------------------------- //
        Game mockGame2 = new Game(DISCORD_TEXT_CHANNEL_ID_2, DISCORD_VOICE_CHANNEL_ID_2, "Mock Game 2");

        GameRole daveAsPlayer = new GameRole(GameRole.Role.PLAYER, mockGame2, mockUser1);
        mockGame2.getGameRoles().add(daveAsPlayer);
        mockUser1.getGameRoles().add(daveAsPlayer);

        GameRole sarahAsMC = new GameRole(GameRole.Role.MC, mockGame2, mockUser2);
        Npc mockNpc3 = new Npc(sarahAsMC, "Batty", "Overly polite gun for hire");
        Npc mockNpc4 = new Npc(sarahAsMC, "Farley");
        sarahAsMC.getNpcs().add(mockNpc3);
        sarahAsMC.getNpcs().add(mockNpc4);
        mockGame2.getGameRoles().add(sarahAsMC);
        mockUser2.getGameRoles().add(sarahAsMC);

        gameService.save(mockGame2);

        // ----------------------------------------------------------------------------------------------- //
        mockUser1.getGames().add(mockGame1);
        mockUser1.getGames().add(mockGame2);
        mockUser2.getGames().add(mockGame1);
        mockUser2.getGames().add(mockGame2);
        npcService.save(mockNpc1);
        npcService.save(mockNpc2);
        npcService.save(mockNpc3);
        npcService.save(mockNpc4);

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
        GameRole.Role roleType = role.getRole();
        System.out.println("\t Role: " + role.getRole());
        if (roleType == GameRole.Role.MC) {
            Set<Npc> npcs = role.getNpcs();
            System.out.println("\t This role has " + npcs.size() + " NPCs");
            if (npcs.size() > 0) {
                for (Npc npc: npcs) {
                    System.out.println("\t\t NPC name: " + npc.getName());
                    if (npc.getDescription() != null) {
                        System.out.println("\t\t NPC description: " + npc.getDescription());
                    }
                    System.out.println("\n");
                }
            }
        }
    }

    private void printUser(User user) {
        System.out.println("| ------------- " + user.getUsername().toUpperCase() + " -------------- |");
        System.out.println("ID: " + user.getId());
        System.out.println("Discord ID: " + user.getDiscourseID());
        Set<Game> davesGames = user.getGames();
        System.out.println(user.getUsername() + " is playing in " + davesGames.size() + " games");
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
