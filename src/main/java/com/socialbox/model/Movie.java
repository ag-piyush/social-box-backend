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
  @Id private String id;
  private String name;
  private double rating;
  private List<String> reviews;
  private String photoURL;
  private int votes;
  private String tmdbId;
}
