package com.socialbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tmdb {
  private boolean adult;
  private String backdrop_path;
  private List<Integer> genre_ids;
  private String id;
  private String original_language;
  private String original_title;
  private String overview;
  private String popularity;
  private String poster_path;
  private String release_date;
  private String title;
  private boolean video;
  private double vote_average;
  private int vote_count;
}
