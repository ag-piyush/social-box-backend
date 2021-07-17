package com.socialbox.repository;

import com.socialbox.enums.Genre;
import com.socialbox.model.Movie;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

  @Query("select m from Movie m where :genre MEMBER OF m.genre")
  Page<Movie> getMoviesByGenreWithPagination(Genre genre, Pageable pageable);

  @Query("select m from Movie m where :genre MEMBER OF m.genre AND m.name LIKE :name%")
  List<Movie> getMoviesByGenreAndName(Genre genre, String name);
}
