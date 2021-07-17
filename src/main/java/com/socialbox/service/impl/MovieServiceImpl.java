package com.socialbox.service.impl;

import com.socialbox.config.TmdbConfig;
import com.socialbox.dto.MovieDTO;
import com.socialbox.dto.ReviewDTO;
import com.socialbox.dto.Tmdb;
import com.socialbox.dto.TmdbDTO;
import com.socialbox.enums.Genre;
import com.socialbox.model.Movie;
import com.socialbox.repository.MovieRepository;
import com.socialbox.service.MovieService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  public List<MovieDTO> getAllMovies(Genre genre) {
    Pageable firstPage = PageRequest.of(0, 20);
    Page<Movie> movieList = this.movieRepository.getMoviesByGenreWithPagination(genre, firstPage);
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
    log.info("Fetching movies for ids: {}", movieIds);
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
    log.info("Saving movie with id: {}", movieDTO.getId());
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
      List<Genre> genres =
          tmdb.getGenre_ids()
              .stream()
              .map(Genre::getGenre)
              .collect(Collectors.toList());
      Movie tempMovie =
          Movie.builder()
              .name(tmdb.getTitle())
              .photoURL(tmdb.getPoster_path())
              .rating(tmdb.getVote_average())
              .votes(tmdb.getVote_count())
              .genre(genres)
              .reviews(new HashSet<>())
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

  @Override public List<MovieDTO> searchMovieByGenre(Genre genre, String name) {
    List<Movie> movieList = this.movieRepository.getMoviesByGenreAndName(genre, name);
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
}
