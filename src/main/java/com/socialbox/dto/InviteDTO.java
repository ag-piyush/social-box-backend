package com.socialbox.dto;

import com.socialbox.model.InviteLink;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteDTO {
    private InviteLink link;
    private String content;
}
