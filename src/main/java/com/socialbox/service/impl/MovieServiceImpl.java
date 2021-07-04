package com.socialbox.service.impl;

import com.google.common.collect.Lists;
import com.socialbox.config.TmdbConfig;
import com.socialbox.dto.MovieDTO;
import com.socialbox.dto.TmdbDTO;
import com.socialbox.model.Movie;
import com.socialbox.model.Tmdb;
import com.socialbox.repository.MovieRepository;
import com.socialbox.service.MovieService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

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

  @Override
  public List<Movie> searchMovie(String name) {
    String url =
        tmdbConfig.getBaseUrl() + "/search/movie?api_key=" + tmdbConfig.getKey() + "&query=" + name;
    RestTemplate restTemplate = new RestTemplate();
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

    return this.movieRepository.saveAll(movieList);
  }
}
