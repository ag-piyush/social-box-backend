package com.socialbox.service.impl;

import com.socialbox.model.User;
import com.socialbox.repository.UserRepository;
import com.socialbox.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

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
    return userOptional.orElse(null);
  }

  @Override
  public User saveUser(User user) {
    return this.userRepository.save(user);
  }
}
