package com.socialbox.service.impl;

import com.socialbox.model.User;
import com.socialbox.repository.UserRepository;
import com.socialbox.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAllUsers() {
    return this.userRepository.findAll();
  }

  @Override
  public User getUserById(String id) {
    Optional<User> userOptional = this.userRepository.findById(id);
    userOptional.ifPresent(user -> log.info("Found user: {}", user));
    if (!userOptional.isPresent()) {
      log.error("User not found with id: {}", id);
    }

    return userOptional.orElse(null);
  }

  @Override
  public User saveUser(User user) {
    User existingUser = this.userRepository.findByUserEmail(user.getUserEmail());
    log.info("User found: {}", existingUser);

    if (existingUser != null && user.getUserPassword().equals(existingUser.getUserPassword())) {
      log.info("User authenticated.");
      return this.userRepository.save(existingUser);
    }

    log.error("Login credentials incorrect.");
    return null;
  }
}
