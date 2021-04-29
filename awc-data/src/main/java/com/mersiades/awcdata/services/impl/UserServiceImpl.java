package com.mersiades.awcdata.services.impl;

import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.repositories.UserRepository;
import com.mersiades.awcdata.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> saveAll(List<User> users) {
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
    public User addGameroleToUser(String userId, GameRole gameRole) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        user.getGameRoles().add(gameRole);
        return userRepository.save(user);
    }

    @Override
    public User findOrCreateUser(String userId, String displayName, String email) {
        Optional<User> userOptional = userRepository.findById(userId);

        User user;
        if (userOptional.isEmpty()) {
            User newUser = User.builder().id(userId).displayName(displayName).email(email).build();
            user = userRepository.save(newUser);
        } else {
            user = userOptional.get();
        }
        return user;
    }

    @Override
    public void removeGameroleFromUser(String userId, String gameId) {
        User user = userRepository.findById(userId).orElseThrow();
        assert user != null;
        Optional<GameRole> gameRoleOptional = user.getGameRoles().stream()
                .filter(gameRole -> gameRole.getGameId().equals(gameId))
                .findFirst();

        gameRoleOptional.ifPresent(gameRole -> user.getGameRoles().remove(gameRole));
        userRepository.save(user);
    }
}
