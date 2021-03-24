package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Game;
import com.mersiades.awcdata.models.GameRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GameRepository extends MongoRepository<Game, String> {


    @Query(value = "{ _id : ?0 }", fields = "{ gameMessages: {$slice: [?1, ?2]}}")
    Game findById(String gameId, Integer skip, Integer limit);

    Game findByGameRoles(GameRole gameRole);

    List<Game> findAllByInviteesContaining(String email);
}
