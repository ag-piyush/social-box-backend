package com.socialbox.repository;

import com.socialbox.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository {
    User findByUserEmail(String email);

    List<User> findAll();

    Optional<User> findById(String id);

    User save(User user);
}
