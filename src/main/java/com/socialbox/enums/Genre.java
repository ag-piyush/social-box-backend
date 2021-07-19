package com.socialbox.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Genre {
  PERSONAL,
  LATEST,
  SCI_FI,
  ACTION,
  COMEDY,
  UNKNOWN,
  ADVENTURE,
  WESTERN,
  ANIMATION,
  CRIME,
  DOCUMENTARY,
  DRAMA,
  FAMILY,
  FANTASY,
  HISTORY,
  HORROR,
  MUSIC,
  MYSTERY,
  ROMANCE,
  TV_MOVIE,
  THRILLER,
  WAR;

  public static Map<Integer, Genre> genreMap = new HashMap<>();

  static {
    genreMap.put(1, PERSONAL);
    genreMap.put(2, LATEST);
    genreMap.put(28, ACTION);
    genreMap.put(12, ADVENTURE);
    genreMap.put(35, COMEDY);
    genreMap.put(16, ANIMATION);
    genreMap.put(80, CRIME);
    genreMap.put(99, DOCUMENTARY);
    genreMap.put(18, DRAMA);
    genreMap.put(10751, FAMILY);
    genreMap.put(14, FANTASY);
    genreMap.put(36, HISTORY);
    genreMap.put(27, HORROR);
    genreMap.put(10402, MUSIC);
    genreMap.put(9648, MYSTERY);
    genreMap.put(10749, ROMANCE);
    genreMap.put(10770, TV_MOVIE);
    genreMap.put(53, THRILLER);
    genreMap.put(10752, WAR);
    genreMap.put(37, WESTERN);
    genreMap.put(878, SCI_FI);
    genreMap.put(99999, UNKNOWN);
  }

  public static Genre getGenre(int id) {
    return genreMap.getOrDefault(id, UNKNOWN);
  }

  public static Genre getGenre(String name) {
    for (Map.Entry<Integer, Genre> entry : genreMap.entrySet()) {
      if (entry.getValue().name().toLowerCase(Locale.ROOT).equals(name)) {
        return entry.getValue();
      }
    }
    return UNKNOWN;
  }
}
