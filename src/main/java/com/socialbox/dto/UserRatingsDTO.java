package com.socialbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRatingsDTO {
  private Integer id;

  private double ratings;

  private Integer userDTO;

  private Integer movieDTO;
}
