package com.socialbox.service;

import com.socialbox.dto.GroupDTO;
import com.socialbox.dto.InviteDTO;
import com.socialbox.model.Group;
import com.socialbox.model.GroupMovie;

import java.util.List;

public interface GroupService {
  List<GroupDTO> getAllGroups(List<Integer> groupIds);

  Group getGroup(Integer id);

  Group saveGroup(Group group);

  Group createGroup(Group group);

  List<GroupMovie> saveMovie(List<GroupMovie> groupMovies);

  InviteDTO sendInvite(Integer groupId, Integer userId);

  Group addUserToGroup(Integer groupId, Integer userId);

  Group removeUserFromGroup(Integer groupId, Integer userId);
}
