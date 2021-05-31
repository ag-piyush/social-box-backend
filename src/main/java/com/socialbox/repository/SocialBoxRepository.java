package com.socialbox.repository;

import com.socialbox.model.SocialBox;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SocialBoxRepository extends MongoRepository<SocialBox, String> {
 }
