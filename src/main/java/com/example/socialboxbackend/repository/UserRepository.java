package com.example.socialboxbackend.repository;

import com.example.socialboxbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
