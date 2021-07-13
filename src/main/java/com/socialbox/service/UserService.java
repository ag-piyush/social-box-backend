package com.socialbox.service;

import com.socialbox.dto.UserMovieDTO;
import com.socialbox.model.User;

import java.util.List;

public interface UserService {

  List<User> getAllUsers();

  User getUserById(Integer id);

  User loginUser(User user);

  User saveUser(User user);

  List<UserMovieDTO> getMovies(Integer id);
}
