package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class UserMapService implements UserService {

    private Map<Long, User> usersMap = new HashMap<>();

    @Override
    public Set<User> findAll() {
        return new HashSet<>(usersMap.values());
    }

    @Override
    public User findById(Long id) {
        return usersMap.get(id);
    }

    @Override
    public User save(User user) {
        if (user != null) {
            if (user.getId() == null) {
                throw new RuntimeException("User must have id");
            }
            usersMap.put(user.getId(), user);
        } else {
            throw new RuntimeException("User cannot be null");
        }
        return user;
    }

    @Override
    public void delete(User user) {
        usersMap.entrySet().removeIf((entry -> entry.getValue() == user));
    }

    @Override
    public void deleteById(Long id) {
        usersMap.remove(id);
    }
}
