package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Profile({ "default", "map"})
public class UserMapService extends AbstractMapService<User, Long> implements UserService {

    @Override
    public Set<User> findAll() {
        return super.findAll();
    }

    @Override
    public User findById(Long id) {
        return super.findById(id);
    }

    @Override
    public User save(User user) {
        return super.save(user);
    }

    @Override
    public void delete(User user) {
        super.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public User findByDiscordId(String discordId) {
        Optional<User> optionalUser = this.findAll().stream()
                .filter(user -> user.getDiscordId().equals(discordId)).findFirst();
        return  optionalUser.orElseGet(() -> {
            User newUser = new User();
            newUser.setDiscordId(discordId);
            this.save(newUser);
            return newUser;
        });
    }
}
