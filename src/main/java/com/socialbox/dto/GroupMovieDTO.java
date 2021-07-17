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
public class GroupMovieDTO {
  private Integer id;

  private List<ReviewDTO> reviews;

  private String name;

  private String photoURL;

  private double rating;

  private int votes;

  private Integer groupId;
}
