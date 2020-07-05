package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.GameService;
import com.mersiades.awcdata.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;

    public DataLoader(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("testing testing");

        User mockUser1 = new User("Mock User 1");
        userService.save(mockUser1);

        User mockUser2 = new User("Mock User 2");
        userService.save(mockUser2);

        Game mockGame1 = new Game("Mock Game 1");
        gameService.save(mockGame1);

        Game mockGame2 = new Game("Mock Game 2");
        gameService.save(mockGame2);

        Set<User> users = userService.findAll();

        for (User user : users) {
            System.out.println("username: " + user.getUsername());
        }

        Set<Game> games = gameService.findAll();

        for (Game game : games) {
            System.out.println("game: " + game.getName());
        }

    }
}
