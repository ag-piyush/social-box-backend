package com.socialbox.service.impl;

import com.socialbox.config.TmdbConfig;
import com.socialbox.dto.MovieDTO;
import com.socialbox.dto.ReviewDTO;
import com.socialbox.dto.Tmdb;
import com.socialbox.dto.TmdbDTO;
import com.socialbox.model.Movie;
import com.socialbox.repository.MovieRepository;
import com.socialbox.service.MovieService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

  private final MovieRepository movieRepository;
  private final TmdbConfig tmdbConfig;

  @Autowired
  public MovieServiceImpl(MovieRepository movieRepository, TmdbConfig tmdbConfig) {
    this.movieRepository = movieRepository;
    this.tmdbConfig = tmdbConfig;
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
  public List<MovieDTO> getMoviesByIds(List<Integer> movieIds) {
    return this.movieRepository.findAllById(movieIds).stream().map(m -> MovieDTO.builder()
        .id(m.getId())
        .votes(m.getVotes())
        .name(m.getName())
        .photoURL(m.getPhotoURL())
        .movieRating(m.getRating())
        .reviews(m.getReviews()
            .stream()
            .map(r -> ReviewDTO.builder()
                .id(r.getId())
                .groupMovieId(r.getGroupMovie().getId())
                .userReviews(r.getUserReviews())
                .movieId(r.getMovie().getId())
                .build())
            .collect(Collectors.toList()))
        .build()).
        collect(Collectors.toList());
  }

  @Override
  public MovieDTO getMovie(Integer id) {
    Optional<Movie> movieOptional = this.movieRepository.findById(id);
    if (!movieOptional.isPresent()) {
      log.debug("Movie not found with id: {}", id);
      return null;
    }

    Movie movie = movieOptional.get();
    return MovieDTO.builder()
        .id(movie.getId())
        .votes(movie.getVotes())
        .name(movie.getName())
        .photoURL(movie.getPhotoURL())
        .movieRating(movie.getRating())
        .reviews(movie.getReviews()
            .stream()
            .map(r -> ReviewDTO.builder()
                .id(r.getId())
                .groupMovieId(r.getGroupMovie().getId())
                .userReviews(r.getUserReviews())
                .movieId(r.getMovie().getId())
                .build())
            .collect(
                Collectors.toList()))
        .build();
  }

  @Override
  public MovieDTO saveMovie(MovieDTO movieDTO) {
    Movie movie = this.movieRepository.save(Movie.builder()
        .name(movieDTO.getName())
        .photoURL(movieDTO.getPhotoURL())
        .rating(movieDTO.getMovieRating())
        .votes(movieDTO.getVotes())
        .build());
    movieDTO.setId(movie.getId());
    return movieDTO;
  }

  @Override
  public List<MovieDTO> searchMovie(String name) {
    String url =
        tmdbConfig.getBaseUrl() + "/search/movie?api_key=" + tmdbConfig.getKey() + "&query=" + name;
    RestTemplate restTemplate = new RestTemplate();
    log.info("Making request to {}", url);
    ResponseEntity<TmdbDTO> tmdbDTOResponseEntity =
        restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, null), TmdbDTO.class);

    if (tmdbDTOResponseEntity.getBody() == null) {
      log.debug("No movie found, status: {}", tmdbDTOResponseEntity.getStatusCodeValue());
      return new ArrayList<>();
    }

    List<Tmdb> results = tmdbDTOResponseEntity.getBody().getResults();
    List<Movie> movieList = new ArrayList<>();

    for (Tmdb tmdb : results) {
      Movie tempMovie =
          Movie.builder()
              .name(tmdb.getTitle())
              .photoURL(tmdb.getPoster_path())
              .rating(tmdb.getVote_average())
              .votes(tmdb.getVote_count())
              .reviews(new ArrayList<>())
              .tmdbId(tmdb.getId())
              .build();

      movieList.add(tempMovie);
    }

    List<Movie> movies = this.movieRepository.saveAll(movieList);
    return movies.stream().map(m -> MovieDTO.builder()
        .id(m.getId())
        .votes(m.getVotes())
        .name(m.getName())
        .photoURL(m.getPhotoURL())
        .movieRating(m.getRating())
        .reviews(m.getReviews()
            .stream()
            .map(r -> ReviewDTO.builder()
                .id(r.getId())
                .groupMovieId(r.getGroupMovie().getId())
                .userReviews(r.getUserReviews())
                .movieId(r.getMovie().getId())
                .build())
            .collect(Collectors.toList()))
        .build()).
        collect(Collectors.toList());
  }
}
