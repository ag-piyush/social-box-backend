package com.socialbox.service;

import com.socialbox.dto.UserDTO;
import com.socialbox.dto.UserMovieDTO;
import com.socialbox.model.User;

import java.util.List;

public interface UserService {

  List<User> getAllUsers();

  User getUserById(String id);

  User loginUser(UserDTO user);

  User saveUser(User user);

  List<UserMovieDTO> getMovies(String id);
}
