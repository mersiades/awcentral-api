package com.mersiades.awcdata.services.jpa;

import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.UserRepository;
import com.mersiades.awcdata.services.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class UserJpaService implements UserService {

    private final UserRepository userRepository;

    public UserJpaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<User> findAll() {
        Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User findById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByDiscordId(String discordId) {
        Optional<User> optionalUser = userRepository.findByDiscordId(discordId);
        return optionalUser.orElseGet(() -> {
            User newUser = new User();
            newUser.setDiscordId(discordId);
            userRepository.save(newUser);
            return newUser;
        });
    }
}
