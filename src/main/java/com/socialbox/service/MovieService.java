package com.socialbox.service;

import com.socialbox.dto.MovieDTO;
import java.util.List;

public interface MovieService {

  List<MovieDTO> getAllMovies();

  List<MovieDTO> getMoviesByIds(List<Integer> movieIds);

  MovieDTO getMovie(Integer id);

  MovieDTO saveMovie(MovieDTO movie);

  List<MovieDTO> searchMovie(String name);
}
