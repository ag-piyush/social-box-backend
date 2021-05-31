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
  @Id
  public String groupId;
  public String groupName;
  public List<String> groupMovieList;
  public HashSet<String> usersId;
}
