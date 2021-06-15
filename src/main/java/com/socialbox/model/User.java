package com.socialbox.model;

import com.socialbox.dto.UserMovieDTO;
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
  private String userPassword;
  private List<UserMovieDTO> personalMovieList;
  private List<String> sharedMovieList;
  private HashSet<String> groupsId;
  private String userPhotoURL;
  private String userEmail;
}
