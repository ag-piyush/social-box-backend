package com.socialbox.service;

import com.socialbox.dto.UserMovieDTO;
import com.socialbox.model.Group;
import com.socialbox.model.User;

import java.util.List;

public interface UserService {

  List<User> getAllUsers();

  User getUserById(String id);

  User loginUser(User user);

  User saveUser(User user);

  List<UserMovieDTO> getMovies(String id);

  Group addUserToGroup(String groupId, String userId);

  Group removeUserFromGroup(String groupId, String userId);
}
