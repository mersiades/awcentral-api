package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {
}
