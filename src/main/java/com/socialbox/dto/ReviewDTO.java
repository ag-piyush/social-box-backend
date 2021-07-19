package com.socialbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
  private Integer id;

  private String userReviews;

  private Integer groupMovieId;

  private Integer movieId;
}
