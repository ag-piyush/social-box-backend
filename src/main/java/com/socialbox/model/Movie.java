package com.socialbox.model;

import com.socialbox.enums.Genre;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movie")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "rating", nullable = false)
  private double rating;

  @Column(name = "photo_url")
  private String photoURL;

  @Column(name = "votes", nullable = false)
  private int votes;

  @Enumerated(EnumType.STRING)
  @ElementCollection(targetClass = Genre.class)
  @CollectionTable(name = "Movie_genre", joinColumns = @JoinColumn(name = "movie_id"))
  private List<Genre> genre;

  @Column(name = "tmdb_id")
  private String tmdbId;

  @OneToMany(targetEntity = Review.class, mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @EqualsAndHashCode.Exclude
  private Set<Review> reviews;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "user_ratings_id")
  private UserRatings userRatings;
}
