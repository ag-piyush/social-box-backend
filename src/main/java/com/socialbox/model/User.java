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
  @Id private String userId;
  private String userName;
  private List<String> personalMovieList;
  private List<String> sharedMovieList;
  private HashSet<String> groupsId;
  private String photoURL;
  private String userEmail;
}
