package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final GameService gameService;
    private final GameRoleService gameRoleService;

    public DataLoader(GameService gameService, GameRoleService gameRoleService) {
        this.gameService = gameService;
        this.gameRoleService = gameRoleService;
    }

    public class MockDiscordUser {
        private Long id;
        private String userName;

        public MockDiscordUser(Long id, String userName) {
            this.id = id;
            this.userName = userName;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }


    @Override
    public void run(String... args) throws Exception {

        final Long DISCORD_TEXT_CHANNEL_ID_1 = Long.parseLong("741573502452105236");
        final Long DISCORD_TEXT_CHANNEL_ID_2 = Long.parseLong("823458920374529070");

        MockDiscordUser discordUser1 = new MockDiscordUser(Long.parseLong("696484065859076146"), "Mr Mock Discord User 1");
        MockDiscordUser discordUser2 = new MockDiscordUser(Long.parseLong("134523465246534532"), "Ms Mock Discord User 2");

        Set<MockDiscordUser> mockUsers = new HashSet<>();

        mockUsers.add(discordUser1);
        mockUsers.add(discordUser2);

        Game mockGame1 = new Game(DISCORD_TEXT_CHANNEL_ID_1, "Mock Game 1");
        gameService.save(mockGame1);

        Game mockGame2 = new Game(DISCORD_TEXT_CHANNEL_ID_2, "Mock Game 2");
        gameService.save(mockGame2);

        Set<Game> games = gameService.findAll();

        for (Game game : games) {
            boolean hasSetMC = false;
            for (MockDiscordUser mockUser : mockUsers) {
                GameRole gameRole;
                if (!hasSetMC) {
                    gameRole = gameService.addGameRole(mockUser.getId(), game.getId(), GameRole.Role.MC);
                    hasSetMC = true;
                } else {
                    gameRole = gameService.addGameRole(mockUser.getId(), game.getId(), GameRole.Role.PLAYER);
                }
                gameRoleService.save(gameRole);
            }
        }

        // Print User info
        for (MockDiscordUser mockUser : mockUsers) {
            System.out.println("| ------------- " + mockUser.getUserName().toUpperCase() + " -------------- |");
            System.out.println("ID: " + mockUser.getId());
            Set<GameRole> roles = gameRoleService.findByUserId(mockUser.getId());
            System.out.println(mockUser.getUserName() + " is playing in " + roles.size() + " games");
            int i = 1;
            for (GameRole role : roles) {
                System.out.println("\t ********** Game " + i + " **********");
                i++;
                System.out.println("\t * Game ID: " + role.getGameId() );
                System.out.println("\t * " + mockUser.getUserName() + "'s role: " + role.getRole() );
                if (i != roles.size()) {
                    System.out.println("\t ****************************");
                }
            }
            System.out.println("\n");
        }




    }
}
