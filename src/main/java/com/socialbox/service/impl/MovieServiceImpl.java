package com.socialbox.service.impl;

import com.google.common.collect.Lists;
import com.socialbox.dto.MovieDTO;
import com.socialbox.model.Movie;
import com.socialbox.repository.MovieRepository;
import com.socialbox.service.MovieService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

  private final MovieRepository movieRepository;

  @Autowired
  public MovieServiceImpl(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public List<MovieDTO> getAllMovies() {
    List<Movie> movieList = this.movieRepository.findAll();
    List<MovieDTO> movieDTOList = new ArrayList<>();

    for (Movie movie : movieList) {
      MovieDTO movieDTO =
          MovieDTO.builder()
              .id(movie.getId())
              .name(movie.getName())
              .movieRating(movie.getRating())
              .build();
      movieDTOList.add(movieDTO);
    }
    return movieDTOList;
  }

  @Override
  public List<Movie> getMoviesByIds(List<String> movieIds) {
    return Lists.newArrayList(this.movieRepository.findAllById(movieIds));
  }

  @Override
  public Movie getMovie(String id) {
    Optional<Movie> movieOptional = this.movieRepository.findById(id);
    return movieOptional.orElse(null);
  }

  @Override
  public Movie saveMovie(Movie movie) {
    return this.movieRepository.save(movie);
  }
}
