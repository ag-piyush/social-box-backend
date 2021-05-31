package com.socialbox.model;

import java.util.HashSet;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
  @Id private String groupId;
  private String groupName;
  private List<String> groupMovieList;
  private HashSet<String> usersId;
}
