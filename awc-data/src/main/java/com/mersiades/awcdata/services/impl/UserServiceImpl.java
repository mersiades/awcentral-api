package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.UserRepository;
import com.mersiades.awcdata.services.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Flux<User> saveAll(Flux<User> users) {
        return userRepository.saveAll(users);
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
    public User addGameroleToUser(String userId, GameRole gameRole) throws Exception {
        User user = userRepository.findById(userId).blockOptional().orElseThrow(NoSuchElementException::new);
        System.out.println("Adding gamerole to user");
        user.getGameRoles().add(gameRole);
        return userRepository.save(user).block();
    }

    @Override
    public User findOrCreateUser(String userId, String displayName, String email) {
        Optional<User> userOptional = this.findById(userId).blockOptional();

        User user;
        if (userOptional.isEmpty()) {
            User newUser = User.builder().id(userId).displayName(displayName).email(email).build();
            user = this.save(newUser).block();
        } else {
            user = userOptional.get();
        }
        return user;
    }
}
