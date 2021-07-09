package com.socialbox.service.impl;

import com.socialbox.dto.UserMovieDTO;
import com.socialbox.model.Group;
import com.socialbox.model.Movie;
import com.socialbox.model.User;
import com.socialbox.repository.UserRepository;
import com.socialbox.service.GroupService;
import com.socialbox.service.MovieService;
import com.socialbox.service.UserService;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final MovieService movieService;
  private final GroupService groupService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, MovieService movieService,
      GroupService groupService) {
    this.userRepository = userRepository;
    this.movieService = movieService;
    this.groupService = groupService;
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
  public User loginUser(User user) {

    Optional<User> userOptional = this.userRepository.findById(user.getId());

    return userOptional.orElseGet(() -> this.userRepository.save(user));
  }

  @Override
  public User saveUser(User user) {
    return this.userRepository.save(user);
  }

  @Override
  public List<UserMovieDTO> getMovies(String id) {
    Optional<User> userOptional = this.userRepository.findById(id);
    User currentUser = userOptional.orElse(null);

    if (currentUser == null) {
      log.error("User not found.");
      return null;
    }

    List<Movie> movieList = this.movieService.getMoviesByIds(currentUser.getPersonalMovieList());
    List<UserMovieDTO> movieDTOS = new ArrayList<>();

    for (Movie movie : movieList) {
      UserMovieDTO movieDTO =
          UserMovieDTO.builder()
              .userId(id)
              .id(movie.getId())
              .name(movie.getName())
              .photoURL(movie.getPhotoURL())
              .rating(movie.getRating())
              .userRating(5) // Todo: Change User Ratings
              .votes(movie.getVotes())
              .build();

      movieDTOS.add(movieDTO);
    }

    return movieDTOS;
  }

  @Override public Group addUserToGroup(String groupId, String userId) {
    Optional<User> userOptional = this.userRepository.findById(userId);
    Group group = this.groupService.getGroup(groupId);

    if (!userOptional.isPresent()) {
      log.error("Invalid userId: {}", userId);
      return null;
    }
    if (group == null) {
      log.error("Invalid groupId: {}", groupId);
      return null;
    }

    User user = userOptional.get();

    if (user.getGroupsId() == null) {
      log.info("Creating a new groupList for user: {}", userId);
      user.setGroupsId(new HashSet<>());
    }
    user.getGroupsId().add(groupId);
    this.userRepository.save(user);

    if (group.getUsersId() == null) {
      log.info("Creating a new userList for group: {}", groupId);
      group.setUsersId(new HashSet<>());
    }
    group.getUsersId().add(userId);
    return this.groupService.saveGroup(group);
  }

  @Override public Group removeUserFromGroup(String groupId, String userId) {
    Optional<User> userOptional = this.userRepository.findById(userId);
    Group group = this.groupService.getGroup(groupId);

    if (!userOptional.isPresent()) {
      log.error("Invalid userId: {}", userId);
      return null;
    }
    if (group == null) {
      log.error("Invalid groupId: {}", groupId);
      return null;
    }

    User user = userOptional.get();

    if (user.getGroupsId() == null
        || !user.getGroupsId().contains(groupId)
        || group.getUsersId() == null
        || !group.getUsersId().contains(userId)) {
      log.error("Invalid userId or groupId");
      return null;
    }

    user.getGroupsId().remove(groupId);
    this.userRepository.save(user);

    group.getUsersId().remove(userId);
    return this.groupService.saveGroup(group);
  }
}
