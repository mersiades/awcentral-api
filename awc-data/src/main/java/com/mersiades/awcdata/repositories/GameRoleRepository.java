package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.GameRole;
import com.mersiades.awcdata.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameRoleRepository extends MongoRepository<GameRole, String> {

    List<GameRole> findAllByUser(User user);

    List<GameRole> findAllByUserId(String userid);

//    Flux<GameRole> findAllByGame(Game game);
//
//    Mono<GameRole> findByGameIdAndUserId(String gameId, String userId);
}
