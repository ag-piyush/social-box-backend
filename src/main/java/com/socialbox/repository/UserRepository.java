package com.socialbox.repository;

import com.socialbox.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  User findByUserEmail(String email);
}
