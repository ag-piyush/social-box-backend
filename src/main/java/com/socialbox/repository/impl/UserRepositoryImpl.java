package com.socialbox.repository.impl;

import com.socialbox.model.User;
import com.socialbox.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private final MongoTemplate mongoTemplate;

  @Autowired
  public UserRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override public User findByUserEmail(String email) {
    Query query = new Query(Criteria.where("userEmail").is(email));
    return this.mongoTemplate.findOne(query, User.class);
  }

  @Override public List<User> findAll() {
    return this.mongoTemplate.findAll(User.class);
  }

  @Override public Optional<User> findById(String id) {
    Query query = new Query(Criteria.where("_id").is(id));
    User user = this.mongoTemplate.findOne(query, User.class);
    return Optional.ofNullable(user);
  }

  @Override public User save(User user) {
    return this.mongoTemplate.save(user);
  }
}
