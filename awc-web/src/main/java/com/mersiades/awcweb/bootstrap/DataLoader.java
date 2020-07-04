package com.mersiades.awcweb.bootstrap;

import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;

    public DataLoader(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("testing testing");

        User mockUser1 = new User("mockUser1");
        userService.save(mockUser1);

        User mockUser2 = new User("mockUser2");
        userService.save(mockUser2);

        Set<User> users = userService.findAll();

        for (User user : users) {
            System.out.println("username: " + user.getUsername());
        }

    }
}
