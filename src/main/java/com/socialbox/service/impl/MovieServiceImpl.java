package com.socialbox.service.impl;

import com.socialbox.dto.MovieDTO;
import com.socialbox.model.Movie;
import com.socialbox.repository.MovieRepository;
import com.socialbox.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movieList= this.movieRepository.findAll();
        List<MovieDTO> movieDTOList = new ArrayList<>();

        for(Movie movie : movieList) {
            MovieDTO movieDTO = new MovieDTO(movie.getMovieId(), movie.getMovieName(), movie.getMoviePhotoURL(), movie.getMovieRating());
            movieDTOList.add(movieDTO);
        }
        return movieDTOList;
    }

    @Override
    public List<Movie> getMoviesByIds(List<String> movieIds) {

        List<Movie> movieList = new ArrayList<>();

        for(String movieId : movieIds){
            Optional<Movie> movieOptional = this.movieRepository.findById(movieId);
            movieList.add(movieOptional.orElse(null));
        }
        return movieList;
    }

    @Override
    public Movie getMovie(String id){
        Optional<Movie> movieOptional = this.movieRepository.findById(id);
        return movieOptional.orElse(null);
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return this.movieRepository.save(movie);
    }
}
