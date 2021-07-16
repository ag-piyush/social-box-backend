package com.socialbox.service;

import com.socialbox.dto.GroupDTO;
import com.socialbox.dto.GroupMovieDTO;
import com.socialbox.dto.InviteDTO;
import java.util.List;

public interface GroupService {
  List<GroupDTO> getAllGroups(List<Integer> groupIds);

  GroupDTO getGroupById(Integer id);

  GroupDTO saveGroup(GroupDTO group);

  List<GroupMovieDTO> saveMovie(List<GroupMovieDTO> groupMovies);

  InviteDTO sendInvite(Integer groupId, Integer userId);

  GroupDTO addUserToGroup(Integer groupId, Integer userId);

  GroupDTO removeUserFromGroup(Integer groupId, Integer userId);
}
