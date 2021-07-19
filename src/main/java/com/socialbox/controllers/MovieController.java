package com.socialbox.controllers;

import com.socialbox.dto.MovieDTO;
import com.socialbox.enums.Genre;
import com.socialbox.service.MovieService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/movie")
@RestController
public class MovieController {

  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping
  public ResponseEntity<List<MovieDTO>> getAllMovies(@RequestParam("genre") Genre movies) {
    return ResponseEntity.ok(this.movieService.getAllMovies(movies));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MovieDTO> getMovie(@PathVariable("id") Integer id) {

    MovieDTO foundMovie = this.movieService.getMovie(id);

    if (foundMovie == null) return ResponseEntity.status(401).build();

    return ResponseEntity.ok(foundMovie);
  }

  @PostMapping
  public ResponseEntity<MovieDTO> saveMovie(@RequestBody MovieDTO movie) {
    return ResponseEntity.ok(this.movieService.saveMovie(movie));
  }

  @GetMapping("/search")
  public ResponseEntity<List<MovieDTO>> searchMovie(@RequestParam(value = "name") String name,
      @RequestParam(value = "genre", required = false) Genre genre) {
    if (genre != null) {
      return ResponseEntity.ok(this.movieService.searchMovieByGenre(genre, name));
    }

    return ResponseEntity.ok(this.movieService.searchMovie(name));
  }
}
