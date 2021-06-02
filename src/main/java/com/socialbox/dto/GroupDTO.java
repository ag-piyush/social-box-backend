package com.socialbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDTO {
    private String groupId;
    private String groupName;
    private String groupPhotoURL;
    private int memberCount;
}
