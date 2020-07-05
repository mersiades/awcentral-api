package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.GameRoleService;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;
    private final GameRoleService gameRoleService;

    public DataLoader(UserService userService, GameService gameService, GameRoleService gameRoleService) {
        this.userService = userService;
        this.gameService = gameService;
        this.gameRoleService = gameRoleService;
    }


    @Override
    public void run(String... args) throws Exception {

        User mockUser1 = new User("Mock User 1");
        userService.save(mockUser1);

        User mockUser2 = new User("Mock User 2");
        userService.save(mockUser2);

        Game mockGame1 = new Game("Mock Game 1");
        gameService.save(mockGame1);

        Game mockGame2 = new Game("Mock Game 2");
        gameService.save(mockGame2);

        Set<Game> games = gameService.findAll();
        Set<User> users = userService.findAll();

        for (Game game : games) {
            boolean hasSetMC = false;
            for (User user : users) {
                GameRole gameRole;
                if (!hasSetMC) {
                    gameRole = gameService.addGameRole(user.getId(), game.getId(), GameRole.Role.MC);
                    hasSetMC = true;
                } else {
                    gameRole = gameService.addGameRole(user.getId(), game.getId(), GameRole.Role.PLAYER);
                }
                gameRoleService.save(gameRole);
            }
        }

        // Print User info
        for (User user : users) {
            System.out.println("| ------------- " + user.getUsername().toUpperCase() + " -------------- |");
            System.out.println("ID: " + user.getId());
            Set<GameRole> roles = gameRoleService.findByUserId(user.getId());
            System.out.println(user.getUsername() + " is playing in " + roles.size() + " games");
            int i = 1;
            for (GameRole role : roles) {
                System.out.println("\t ********** Game " + i + " **********");
                i++;
                System.out.println("\t * Game ID: " + role.getGame() );
                System.out.println("\t * " + user.getUsername() + "'s role: " + role.getRole() );
                if (i != roles.size()) {
                    System.out.println("\t ****************************");
                }
            }
            System.out.println("\n");
        }




    }
}
