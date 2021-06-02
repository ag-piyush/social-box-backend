package com.socialbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
  @Id private String movieId;
  private String movieName;
  private double movieRating;
  private List<String> movieReviews;
  private String moviePhotoURL;
}
