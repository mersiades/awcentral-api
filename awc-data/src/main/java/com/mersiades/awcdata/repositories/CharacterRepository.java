package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.Character;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<Character, String> {
}
