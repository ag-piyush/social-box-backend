package com.socialbox.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private String userId;

  @Column(name = "name")
  private String name;

  @Column(name = "password")
  private String password;

  @Column(name = "photo_url")
  private String photoURL;

  @Column(name = "email")
  private String email;

  @OneToMany
  private List<UserRatings> personalMovieList;

  @OneToMany
  private List<Movie> sharedMovieList;

  @ManyToMany(mappedBy = "users")
  private List<Group> groups;

  @OneToMany
  private List<Group> owningGroup;
}
