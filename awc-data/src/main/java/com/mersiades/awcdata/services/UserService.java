package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.User;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveCrudService<User, String> {
    Mono<User> findByDiscordId(String discordId);
}
