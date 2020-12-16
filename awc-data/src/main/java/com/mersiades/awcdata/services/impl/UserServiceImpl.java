package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.UserRepository;
import com.mersiades.awcdata.services.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

//    @Override
//    public Mono<User> findByDiscordId(String discordId) {
//        User newUser = User.builder().id(UUID.randomUUID().toString()).discordId(discordId).build();
//        return userRepository.findByDiscordId(discordId)
//                // If user doesn't already exist, return the newUser and save it to db
//                .switchIfEmpty(Mono.just(newUser).flatMap(userRepository::save));
//
//    }
}
