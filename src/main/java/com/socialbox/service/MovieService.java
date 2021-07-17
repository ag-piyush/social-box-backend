package com.socialbox.service;

import com.socialbox.dto.MovieDTO;
import com.socialbox.enums.Genre;
import com.socialbox.model.Movie;
import java.util.List;

public interface MovieService {

  List<MovieDTO> getAllMovies(Genre movies);

  List<MovieDTO> getMoviesByIds(List<Integer> movieIds);

  MovieDTO getMovie(Integer id);

  MovieDTO saveMovie(MovieDTO movie);

  List<MovieDTO> searchMovie(String name);

  List<MovieDTO> searchMovieByGenre(Genre genre, String name);
}
