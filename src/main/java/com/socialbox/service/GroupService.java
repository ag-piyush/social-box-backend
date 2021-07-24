package com.socialbox.service;

import com.socialbox.dto.GroupDTO;
import com.socialbox.dto.GroupMovieDTO;
import com.socialbox.dto.InviteDTO;
import com.socialbox.dto.UserDTO;
import com.socialbox.model.GroupMovie;

import java.util.List;
import java.util.Set;

public interface GroupService {
  List<GroupDTO> getAllGroups(List<Integer> groupIds);

  GroupDTO getGroupById(Integer id);

  GroupDTO saveGroup(GroupDTO group);

  List<GroupMovieDTO> saveMovie(List<GroupMovieDTO> groupMovies);

  InviteDTO sendInvite(Integer groupId, Integer userId);

  GroupDTO addUserToGroup(Integer groupId, Integer userId);

  GroupDTO removeUserFromGroup(Integer groupId, Integer userId);

  List<GroupMovieDTO> groupMovieTOGroupMovieDTO(Set<GroupMovie> groupMovies);

  List<UserDTO> getAllUsers(Integer id);
}
