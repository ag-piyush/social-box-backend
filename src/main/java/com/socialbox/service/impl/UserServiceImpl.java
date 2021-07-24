package com.socialbox.service.impl;

import com.socialbox.dto.*;
import com.socialbox.model.Group;
import com.socialbox.model.Movie;
import com.socialbox.model.User;
import com.socialbox.model.UserRatings;
import com.socialbox.repository.MovieRepository;
import com.socialbox.repository.UserRepository;
import com.socialbox.service.GroupService;
import com.socialbox.service.MovieService;
import com.socialbox.service.UserService;

import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final MovieRepository movieRepository;
  private final MovieService movieService;
  private final GroupService groupService;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      MovieService movieService,
      GroupService groupService,
      MovieRepository movieRepository) {
    this.userRepository = userRepository;
    this.movieRepository = movieRepository;
    this.movieService = movieService;
    this.groupService = groupService;
  }

  @Override
  public List<UserDTO> getAllUsers() {
    return this.userRepository.findAll().stream()
        .map(
            u ->
                UserDTO.builder()
                    .id(u.getUserId())
                    .name(u.getName())
                    .displayName(u.getDisplayName())
                    .email(u.getEmail())
                    .photoURL(u.getPhotoURL())
                    .build())
        .collect(Collectors.toList());
  }

  @Override
  public UserDTO getUserById(Integer id) {
    Optional<User> userOptional = this.userRepository.findById(id);
    userOptional.ifPresent(user -> log.info("Found user: {}", user));
    if (!userOptional.isPresent()) {
      log.error("User not found with id: {}", id);
      return null;
    }

    User user = userOptional.get();
    return UserDTO.builder()
        .id(user.getUserId())
        .name(user.getName())
        .displayName(user.getDisplayName())
        .email(user.getEmail())
        .photoURL(user.getPhotoURL())
        .build();
  }

  @Override
  public User getUser(Integer id) {
    Optional<User> userOptional = this.userRepository.findById(id);
    userOptional.ifPresent(user -> log.info("Found user: {}", user));
    if (!userOptional.isPresent()) {
      log.error("User not found with id: {}", id);
    }

    return userOptional.orElse(null);
  }

  @Override
  public UserDTO loginUser(UserDTO userDTO) {
    Optional<User> userOptional = this.userRepository.findByEmail(userDTO.getEmail());

    if (!userOptional.isPresent()) {
      log.debug("Creating new user: {}", userDTO.getId());
      User save =
          this.userRepository.save(
              User.builder()
                  .userId(userDTO.getId())
                  .name(userDTO.getName())
                  .email(userDTO.getEmail())
                  .photoURL(userDTO.getPhotoURL())
                  .build());
      userDTO.setId(save.getUserId());
      return userDTO;
    }

    User user = userOptional.get();
    return UserDTO.builder()
        .id(user.getUserId())
        .name(user.getName())
        .displayName(user.getDisplayName())
        .email(user.getEmail())
        .photoURL(user.getPhotoURL())
        .groups(
            user.getGroups().stream()
                .map(
                    g ->
                        GroupDTO.builder()
                            .id(g.getId())
                            .adminId(g.getAdmin().getUserId())
                            .memberCount(g.getMemberCount())
                            .photoURL(g.getPhotoURL())
                            .groupMovieDTOList(
                                this.groupService.groupMovieTOGroupMovieDTO(g.getMovieList()))
                            .build())
                .collect(Collectors.toList()))
        .build();
  }

  @Override
  public List<UserMovieDTO> getMovies(Integer id) {
    Optional<User> userOptional = this.userRepository.findById(id);
    User currentUser = userOptional.orElse(null);

    if (currentUser == null) {
      log.error("User not found.");
      return null;
    }

    List<MovieDTO> movieList =
        this.movieService.getMoviesByIds(
            currentUser.getPersonalMovieList().stream()
                .map(userRatings -> userRatings.getMovie().getId())
                .collect(Collectors.toList()));
    List<UserMovieDTO> movieDTOS = new ArrayList<>();

    for (MovieDTO movie : movieList) {
      UserMovieDTO movieDTO =
          UserMovieDTO.builder()
              .userId(id)
              .id(movie.getId())
              .name(movie.getName())
              .photoURL(movie.getPhotoURL())
              .rating(movie.getMovieRating())
              .userRating(5) // Todo: Change User Ratings
              .votes(movie.getVotes())
              .build();

      movieDTOS.add(movieDTO);
    }

    return movieDTOS;
  }

  @Override
  public User updateUser(User user) {
    return this.userRepository.save(user);
  }

  @Override
  public UserDTO saveSettingsForUser(UserDTO userDTO, Integer id) {
    Optional<User> userOptional = this.userRepository.findById(id);
    if (!userOptional.isPresent()) {
      log.debug("User not found with id: {}", id);
      return null;
    }

    User user = userOptional.get();
    user.setDisplayName(userDTO.getDisplayName());
    user.setPhotoURL(userDTO.getPhotoURL());

    try {
      this.userRepository.save(user);
    } catch (Exception e) {
      log.error("Not an unique name with exception:", e);
      return null;
    }

    return userDTO;
  }

  @Override
  public List<GroupDTO> getAllGroups(Integer id) {
    Optional<User> userOptional = this.userRepository.findById(id);
    if (!userOptional.isPresent()) {
      log.debug("User not found with id: {}", id);
      return null;
    }

    User user = userOptional.get();

    Set<Group> groupSet = user.getGroups();
    List<GroupDTO> groupDTOS = new ArrayList<>();

    for (Group group : groupSet) {
      GroupDTO groupDTO =
          GroupDTO.builder()
              .id(group.getId())
              .name(group.getName())
              .adminId(group.getAdmin().getUserId())
              .memberCount(group.getMemberCount())
              .photoURL(group.getPhotoURL())
              .groupMovieDTOList(this.groupService.groupMovieTOGroupMovieDTO(group.getMovieList()))
              .build();

      groupDTOS.add(groupDTO);
    }

    return groupDTOS;
  }

  @Override
  public List<UserRatingsDTO> userRatingsToUserRatingsDTO(Set<UserRatings> userRatings) {
    return userRatings.stream()
        .map(
            m ->
                UserRatingsDTO.builder()
                    .id(m.getId())
                    .ratings(m.getRatings())
                    .userId(m.getUser().getUserId())
                    .movieId(m.getMovie().getId())
                    .build())
        .collect(Collectors.toList());
  }

  @Override
  public UserDTO addMovieToUser(Integer userId, Integer movieId) {
    Optional<Movie> movieOptional = this.movieRepository.findById(movieId);
    if (!movieOptional.isPresent()) {
      log.error("Invalid movieId: {}", movieId);
      return null;
    }

    Optional<User> userOptional = this.userRepository.findById(userId);
    if (!userOptional.isPresent()) {
      log.error("Invalid userId: {}", userId);
      return null;
    }

    Movie movie = movieOptional.get();
    User user = userOptional.get();

    if (user.getSharedMovieList() == null) {
      log.info("Creating a new sharedMovieList for user: {}", userId);
      user.setSharedMovieList(new HashSet<>());
    }

    user.getSharedMovieList().add(movie);
    user = this.updateUser(user);

    return UserDTO.builder()
        .id(user.getUserId())
        .name(user.getName())
        .displayName(user.getDisplayName())
        .email(user.getEmail())
        .photoURL(user.getPhotoURL())
        .personalMovieList(userRatingsToUserRatingsDTO(user.getPersonalMovieList()))
        .groups(
            user.getGroups().stream()
                .map(
                    m ->
                        GroupDTO.builder()
                            .id(m.getId())
                            .name(m.getName())
                            .adminId(m.getAdmin().getUserId())
                            .memberCount(m.getMemberCount())
                            .photoURL(m.getPhotoURL())
                            .groupMovieDTOList(
                                this.groupService.groupMovieTOGroupMovieDTO(m.getMovieList()))
                            .build())
                .collect(Collectors.toList()))
        .build();
  }
}
