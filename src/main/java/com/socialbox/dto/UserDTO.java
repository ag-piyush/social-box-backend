package com.socialbox.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
  private Integer id;
  private String name;
  private String displayName;
  private String email;
  private String photoURL;
  private List<UserRatingsDTO> personalMovieList;
  private List<GroupDTO> groups;
}
