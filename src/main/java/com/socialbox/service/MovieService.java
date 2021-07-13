package com.socialbox.service;

import com.socialbox.dto.MovieDTO;
import com.socialbox.model.Movie;

import java.util.List;

public interface MovieService {

  List<MovieDTO> getAllMovies();

  List<Movie> getMoviesByIds(List<Integer> movieIds);

  Movie getMovie(Integer id);

  Movie saveMovie(Movie movie);

  List<Movie> searchMovie(String name);
}
