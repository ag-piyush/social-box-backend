package com.example.socialboxbackend.repository;

import com.example.socialboxbackend.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
}
