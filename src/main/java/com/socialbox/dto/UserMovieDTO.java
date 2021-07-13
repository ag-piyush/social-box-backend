package com.socialbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMovieDTO {
  private Integer id;
  private Integer userId;
  private String name;
  private String photoURL;
  private double rating;
  private double userRating;
  private int votes;
}
