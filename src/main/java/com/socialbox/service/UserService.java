package com.socialbox.service;

import com.socialbox.dto.*;
import com.socialbox.model.User;
import com.socialbox.model.UserRatings;

import java.util.List;
import java.util.Set;

public interface UserService {

  List<UserDTO> getAllUsers();

  UserDTO getUserById(Integer id);

  User getUser(Integer id);

  UserDTO loginUser(UserDTO user);

  List<UserMovieDTO> getMovies(Integer id);

  User updateUser(User user);

  UserDTO saveSettingsForUser(UserDTO userDTO, Integer id);

  List<GroupDTO> getAllGroups(Integer id);

  List<UserRatingsDTO> userRatingsToUserRatingsDTO(Set<UserRatings> userRatings);

  UserDTO addMovieToUser(Integer userId, Integer movieId);
}
