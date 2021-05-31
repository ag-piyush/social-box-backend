package com.socialbox.service.impl;

import com.socialbox.model.User;
import com.socialbox.repository.UserRepository;
import com.socialbox.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return this.userRepository.findAll();
  }

  public User getUserById(String id) {
<<<<<<< HEAD
    Optional<User> userOptional = this.userRepository.findById(id);
    return userOptional.orElse(null);
=======
    return this.userRepository.findById(id).get();
>>>>>>> 9912b23a2462f2bc6e7e12a423ab067fdb78eaa2
  }

  public User saveUser(User user) {
    return this.userRepository.save(user);
  }
}
