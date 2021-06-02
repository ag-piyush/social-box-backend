package com.socialbox.service;

import com.socialbox.dto.MovieDTO;
import com.socialbox.model.Movie;

import java.util.List;

public interface MovieService {

    List<MovieDTO> getAllMovies();

    List<Movie> getMoviesByIds(List<String> movieIds);

    Movie getMovie(String id);

    Movie saveMovie(Movie movie);
}
