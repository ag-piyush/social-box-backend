package com.example.socialboxbackend.repository;

import com.example.socialboxbackend.model.SocialBox;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SocialBoxRepository extends MongoRepository<SocialBox, String> {
 }
