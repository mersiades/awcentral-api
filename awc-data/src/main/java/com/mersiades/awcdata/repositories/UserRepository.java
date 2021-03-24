package com.mersiades.awcdata.repositories;

import com.mersiades.awcdata.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
