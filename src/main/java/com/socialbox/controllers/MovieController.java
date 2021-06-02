package com.socialbox.controllers;

import com.socialbox.dto.MovieDTO;
import com.socialbox.model.Movie;
import com.socialbox.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
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
  public Movie getMovie(@PathVariable("id") String id) {
    return this.movieService.getMovie(id);
  }

  @PostMapping
  public Movie saveMovie(@RequestBody Movie movie) {
    return this.movieService.saveMovie(movie);
  }
}
