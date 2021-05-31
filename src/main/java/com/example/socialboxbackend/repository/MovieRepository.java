package com.example.socialboxbackend.repository;

import com.example.socialboxbackend.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
