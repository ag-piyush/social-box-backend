package com.socialbox.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {
  private Integer id;
  private String name;
  private String photoURL;
  private double movieRating;
  private int votes;
  private List<ReviewDTO> reviews;
}
