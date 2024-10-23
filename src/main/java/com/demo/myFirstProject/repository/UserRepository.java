package com.demo.myFirstProject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import com.demo.myFirstProject.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}

