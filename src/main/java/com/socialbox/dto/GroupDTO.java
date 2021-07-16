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
public class GroupDTO {
  private Integer id;
  private String name;
  private String photoURL;
  private int memberCount;
  private Integer adminId;
  private List<GroupMovieDTO> groupMovieDTOList;
}
