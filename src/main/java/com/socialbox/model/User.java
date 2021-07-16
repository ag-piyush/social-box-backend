package com.socialbox.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId", scope = User.class)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Integer userId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "display_name", nullable = false, unique = true)
  private String displayName;

  @Column(name = "photo_url")
  private String photoURL;

  @Column(name = "email", nullable = false)
  private String email;

  @OneToMany(targetEntity = UserRatings.class, mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<UserRatings> personalMovieList;

  @OneToMany(targetEntity = Movie.class, mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Movie> sharedMovieList;

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      mappedBy = "users")
  @ToString.Exclude
  private List<Group> groups;

  @OneToMany(targetEntity = Group.class, mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<Group> owningGroup;
}
