package com.socialbox.service;

import com.socialbox.model.User;

import java.util.List;

public interface UserService {

  List<User> getAllUsers();

  User getUserById(String id);

  User saveUser(User user);
}
