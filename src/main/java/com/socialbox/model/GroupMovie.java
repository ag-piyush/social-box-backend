package com.socialbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMovie {
    @Id
    private String id;
    private String groupId;
    private String name;
    private String photoURL;
    private double rating;
    private int votes;
}
