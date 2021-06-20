package com.socialbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieList {
  @Id private String id;
  private String linkedUserId;
  private String linkedGroupId;
  private List<String> moviesId;
  private List<String> sharedUserIds;
}
