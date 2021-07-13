package com.socialbox.controllers;

import com.socialbox.dto.MovieDTO;
import com.socialbox.model.Movie;
import com.socialbox.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/movie")
@RestController
public class MovieController {

  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping
  public List<MovieDTO> getAllMovies() {
    return this.movieService.getAllMovies();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Movie> getMovie(@PathVariable("id") Integer id) {

    Movie foundMovie = this.movieService.getMovie(id);

    if (foundMovie == null) return ResponseEntity.status(401).build();

    return ResponseEntity.ok(foundMovie);
  }

  @PostMapping
  public Movie saveMovie(@RequestBody Movie movie) {
    return this.movieService.saveMovie(movie);
  }

  @GetMapping("/search")
  public List<Movie> searchMovie(@RequestParam("name") String name) {
    return this.movieService.searchMovie(name);
  }
}
