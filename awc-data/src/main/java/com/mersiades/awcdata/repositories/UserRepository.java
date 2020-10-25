package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByDiscordId(String discordId);
}
