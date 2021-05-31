package com.socialbox.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
  @Id
  public String movieId;
  public String movieName;
  public double movieRating;
  public List<String> movieReviews;
}
