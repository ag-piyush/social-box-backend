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
  @Id private String listId;
  private String linkedUserId;
  private String linkedGroupId;
  private List<String> moviesId;
  private List<String> sharedUserIds;
}
