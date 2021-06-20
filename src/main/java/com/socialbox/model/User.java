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
public class User {
  @Id private String id;
  private String name;
  private String password;
  private List<String> personalMovieList;
  private List<String> sharedMovieList;
  private HashSet<String> groupsId;
  private String photoURL;
  private String email;
  private String username;
}
