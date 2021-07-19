package com.socialbox.service;

import com.socialbox.dto.UserDTO;
import com.socialbox.dto.UserMovieDTO;
import com.socialbox.model.User;
import java.util.List;

public interface UserService {

  List<UserDTO> getAllUsers();

  UserDTO getUserById(Integer id);

  User getUser(Integer id);

  UserDTO loginUser(UserDTO user);

  List<UserMovieDTO> getMovies(Integer id);

  User updateUser(User user);

  UserDTO saveSettingsForUser(UserDTO userDTO, Integer id);
}
