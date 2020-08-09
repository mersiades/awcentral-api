package com.mersiades.awcdata.services.map;

import com.mersiades.awcdata.models.User;
import com.mersiades.awcdata.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
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
}
