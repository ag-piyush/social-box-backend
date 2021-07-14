package com.socialbox.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "group_movie")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
// Todo: This entity is not being stored separately
public class GroupMovie {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @OneToMany(targetEntity = Review.class, mappedBy = "groupMovie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Review> reviews;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "photo_url")
  private String photoURL;

  @Column(name = "rating", nullable = false)
  private double rating;

  @Column(name = "votes", nullable = false)
  private int votes;

  @ManyToOne
  @JoinColumn(name = "groups_id", nullable = false)
  @ToString.Exclude
  private Group group;
}
