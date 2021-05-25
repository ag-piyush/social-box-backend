package com.example.socialboxbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    public String userId;
    public String userName;
    public List<String> personalMovieList;
    public List<String> sharedMovieList;
    public HashSet<String> groupsId;
    public String photoURL;
}
