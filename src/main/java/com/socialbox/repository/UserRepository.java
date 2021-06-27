package com.socialbox.repository;

import com.socialbox.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  User findByEmail(String email);
}
