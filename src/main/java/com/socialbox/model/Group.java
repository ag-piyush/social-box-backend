package com.socialbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
  @Id private String id;
  private String name;
  private List<GroupMovie> movieList;
  private HashSet<String> usersId;
  private int memberCount;
  private String photoURL;
  private String adminId;
}
