package com.socialbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMovieDTO {
  @Id private String id;
  private String userId;
  private String name;
  private String photoURL;
  private double rating;
  private double userRating;
  private int votes;
}
