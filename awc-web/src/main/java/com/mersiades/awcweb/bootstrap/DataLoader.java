package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.Npc;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.GameRoleService;
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
    private final GameRoleService gameRoleService;
    private final NpcService npcService;

    public DataLoader(UserService userService, GameService gameService, GameRoleService gameRoleService,
                      NpcService npcService) {
        this.userService = userService;
        this.gameService = gameService;
        this.gameRoleService = gameRoleService;
        this.npcService = npcService;
    }

    @Override
    public void run(String... args) {
        loadData();
    }

    private void loadData() {
        User mockUser1 = new User(DISCORD_USER_ID_1);
        mockUser1.setUserName("Dave");
        User mockUser2 = new User(DISCORD_USER_ID_2);
        mockUser2.setUserName("Sarah");

        Game mockGame1 = new Game(DISCORD_TEXT_CHANNEL_ID_1, DISCORD_VOICE_CHANNEL_ID_1,"Mock Game 1");

        GameRole daveAsMC = new GameRole(GameRole.Role.MC, mockGame1, mockUser1);
        Npc mockNpc1 = new Npc(daveAsMC,"Vision", "Badass truck driver");
        Npc mockNpc2 = new Npc(daveAsMC,"Nbeke");
        daveAsMC.getNpcs().add(mockNpc1);
        daveAsMC.getNpcs().add(mockNpc2);
        mockGame1.getGameRoles().add(daveAsMC);
        mockUser1.getGameRoles().add(daveAsMC);

        GameRole sarahAsPlayer = new GameRole(GameRole.Role.PLAYER, mockGame1, mockUser2);
        mockGame1.getGameRoles().add(sarahAsPlayer);
        mockUser2.getGameRoles().add(sarahAsPlayer);

        Game mockGame2 = new Game(DISCORD_TEXT_CHANNEL_ID_2, DISCORD_VOICE_CHANNEL_ID_2,"Mock Game 2");

        GameRole daveAsPlayer = new GameRole(GameRole.Role.PLAYER, mockGame2, mockUser1);
        mockGame2.getGameRoles().add(daveAsPlayer);
        mockUser1.getGameRoles().add(daveAsPlayer);

        GameRole sarahAsMC = new GameRole(GameRole.Role.MC, mockGame2, mockUser2);
        Npc mockNpc3 = new Npc(sarahAsMC,"Batty", "Overly polite gun for hire");
        Npc mockNpc4 = new Npc(sarahAsMC, "Farley");
        sarahAsMC.getNpcs().add(mockNpc3);
        sarahAsMC.getNpcs().add(mockNpc4);
        mockGame2.getGameRoles().add(sarahAsMC);
        mockUser2.getGameRoles().add(sarahAsMC);

        mockUser1.getGames().add(mockGame1);
        mockUser1.getGames().add(mockGame2);
        mockUser2.getGames().add(mockGame1);
        mockUser2.getGames().add(mockGame2);

        npcService.save(mockNpc1);
        npcService.save(mockNpc2);
        npcService.save(mockNpc3);
        npcService.save(mockNpc4);
        userService.save(mockUser1);
        userService.save(mockUser2);
        gameService.save(mockGame2);
        gameService.save(mockGame1);



        // ----------------------------------- Print MockUser1 ----------------------------------- //
        System.out.println("| ------------- " + mockUser1.getUserName().toUpperCase() + " -------------- |");
        System.out.println("ID: " + mockUser1.getId());
        Set<Game> davesGames = mockUser1.getGames();
        System.out.println(mockUser1.getUserName() + " is playing in " + davesGames.size() + " games");
        int i = 1;
            for (Game game : davesGames) {
//                GameRole gameRole = game.getGameRoles().stream()
//                        .filter(role -> role.getUser().getId() == mockUser1.getId()).collect(Collectors);
                System.out.println("\t ********** Game " + i + " **********");
                i++;
                System.out.println("\t * Game ID & text channel: " + game.getTextChannelId() );
                System.out.println("\t * Game voice channel: " + game.getVoiceChannelId() );
                System.out.println("\t * Game name: " + game.getName() );
//                System.out.println("\t * " + mockUser1.getUserName() + "'s role: " + . );
                if (i != davesGames.size()) {
                    System.out.println("\t ****************************");
                }
            }
            System.out.println("\n");
        }
        // ----------------------------------- Print MockUser2 ----------------------------------- //
        // ----------------------------------- Print MockGame1 ----------------------------------- //
        // ----------------------------------- Print MockGame2 ----------------------------------- //



        // Print User info
//        for (MockDiscordUser mockUser : mockUsers) {
//            System.out.println("| ------------- " + mockUser.getUserName().toUpperCase() + " -------------- |");
//            System.out.println("ID: " + mockUser.getId());
//            Set<GameRole> roles = gameRoleService.findByUserId(mockUser.getId());
//            System.out.println(mockUser.getUserName() + " is playing in " + roles.size() + " games");
//            int i = 1;
//            for (GameRole role : roles) {
//                Game game = gameService.findById(role.getGameId());
//                System.out.println("\t ********** Game " + i + " **********");
//                i++;
//                System.out.println("\t * Game ID & text channel: " + game.getTextChannelId() );
//                System.out.println("\t * Game voice channel: " + game.getVoiceChannelId() );
//                System.out.println("\t * Game name: " + game.getName() );
//                System.out.println("\t * " + mockUser.getUserName() + "'s role: " + role.getRole() );
//                if (i != roles.size()) {
//                    System.out.println("\t ****************************");
//                }
//            }
//            System.out.println("\n");
//        }
    }
