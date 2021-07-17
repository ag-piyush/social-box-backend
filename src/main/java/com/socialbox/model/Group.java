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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "room")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "member_count", nullable = false)
  private int memberCount;

  @Column(name = "photo_url")
  private String photoURL;

  @OneToMany(targetEntity = GroupMovie.class, mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<GroupMovie> movieList;

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = CascadeType.ALL)
  @JoinTable(
      name = "user_groups",
      joinColumns = @JoinColumn(name = "groups_id"),
      inverseJoinColumns = @JoinColumn(name = "users_id"))
  @ToString.Exclude
  private List<User> users;

  @ManyToOne(cascade = {
      CascadeType.MERGE,
      CascadeType.REFRESH
  })
  @JoinColumn(name = "admin_id", nullable = false)
  @ToString.Exclude
  private User admin;
}
