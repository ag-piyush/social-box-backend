package com.socialbox.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "group_movie")
public class GroupMovie {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @OneToMany
  private List<Review> reviews;

  @Column(name = "name")
  private String name;

  @Column(name = "photoUrl")
  private String photoURL;

  @Column(name = "rating")
  private double rating;

  @Column(name = "votes")
  private int votes;

  @ManyToOne
  @JoinColumn(name = "group_id")
  private Group group;
}
