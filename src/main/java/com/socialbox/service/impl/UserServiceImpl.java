package com.socialbox.service.impl;

import com.socialbox.dto.GroupDTO;
import com.socialbox.dto.GroupMovieDTO;
import com.socialbox.dto.MovieDTO;
import com.socialbox.dto.ReviewDTO;
import com.socialbox.dto.UserDTO;
import com.socialbox.dto.UserMovieDTO;
import com.socialbox.model.User;
import com.socialbox.repository.UserRepository;
import com.socialbox.service.MovieService;
import com.socialbox.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final MovieService movieService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, MovieService movieService) {
    this.userRepository = userRepository;
    this.movieService = movieService;
  }

  @Override
  public List<UserDTO> getAllUsers() {
    return this.userRepository.findAll()
        .stream()
        .map(u -> UserDTO.builder()
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
    Optional<User> userOptional = this.userRepository.findByName(userDTO.getEmail());

    if (!userOptional.isPresent()) {
      log.error("User not found with id: {}", userDTO.getId());
      User save = this.userRepository.save(User.builder()
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
        .groups(user.getGroups().stream().map(g -> GroupDTO.builder()
            .id(g.getId())
            .adminId(g.getAdmin().getUserId())
            .memberCount(g.getMemberCount())
            .photoURL(g.getPhotoURL())
            .groupMovieDTOList(
                g.getMovieList()
                    .stream()
                    .map(gM -> GroupMovieDTO.builder()
                        .id(gM.getId())
                        .groupId(gM.getGroup().getId())
                        .votes(gM.getVotes())
                        .name(gM.getName())
                        .rating(gM.getRating())
                        .reviews(gM.getReviews()
                            .stream()
                            .map(r -> ReviewDTO.builder()
                                .id(r.getId())
                                .userReviews(r.getUserReviews())
                                .movieId(r.getMovie().getId())
                                .groupMovieId(r.getGroupMovie().getId())
                                .build())
                            .collect(
                                Collectors.toList()))
                        .build())
                    .collect(Collectors.toList()))
            .build()).collect(Collectors.toList()))
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
        this.movieService.getMoviesByIds(currentUser.getPersonalMovieList()
            .stream()
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

  @Override public User updateUser(User user) {
    return this.userRepository.save(user);
  }

  @Override public UserDTO saveSettingsForUser(UserDTO userDTO, Integer id) {
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
}
