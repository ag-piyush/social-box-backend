package com.socialbox.model;

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
public class MovieList {
  @Id
  public String listId;
  public String linkedUserId;
  public String linkedGroupId;
  public List<String> moviesId;
  public List<String> sharedUserIds;
}
