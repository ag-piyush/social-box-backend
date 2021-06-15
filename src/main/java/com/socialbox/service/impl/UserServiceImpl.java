package com.socialbox.service.impl;

import com.socialbox.dto.UserMovieDTO;
import com.socialbox.model.Movie;
import com.socialbox.model.User;
import com.socialbox.repository.UserRepository;
import com.socialbox.service.MovieService;
import com.socialbox.service.UserService;
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

  @Autowired
  public UserServiceImpl(UserRepository userRepository, MovieService movieService) {
    this.userRepository = userRepository;
    this.movieService = movieService;
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
  public User saveUser(User user) {
    User existingUser = this.userRepository.findByUserEmail(user.getUserEmail());
    log.info("User found: {}", existingUser);

    if (existingUser != null && user.getUserPassword().equals(existingUser.getUserPassword())) {
      log.info("User authenticated.");
      return this.userRepository.save(existingUser);
    }

    log.error("Login credentials incorrect.");
    return null;
  }

  @Override
  public List<UserMovieDTO> getMovies(String id) {
    Optional<User> userOptional = this.userRepository.findById(id);
    User currentUser = userOptional.orElse(null);

    if(currentUser == null)
      return (new ArrayList<>());

    List<Movie> movieList =  this.movieService.getMoviesByIds(currentUser.getPersonalMovieList());
    List<UserMovieDTO> movieDTOS = new ArrayList<>();

    for(Movie movie : movieList){
      UserMovieDTO movieDTO = UserMovieDTO.builder()
              .userId(id)
              .id(movie.getMovieId())
              .name(movie.getMovieName())
              .photoURL(movie.getMoviePhotoURL())
              .rating(movie.getMovieRating())
              .userRating(5) //Todo: Change User Ratings
              .votes(movie.getVotes())
              .build();

      movieDTOS.add(movieDTO);
    }

    return movieDTOS;
  }
}
