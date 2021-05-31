package com.example.socialboxbackend.repository;

import com.example.socialboxbackend.model.MovieList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieListRepository extends MongoRepository<MovieList, String> {
}
