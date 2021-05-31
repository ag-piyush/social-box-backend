package com.socialbox.repository;

import com.socialbox.model.MovieList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieListRepository extends MongoRepository<MovieList, String> {}
