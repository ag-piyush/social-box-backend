package com.socialbox.dto;

import com.socialbox.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDTO {
  private String id;
  private String name;
  private String photoURL;
  private int memberCount;
  private User admin;
}
