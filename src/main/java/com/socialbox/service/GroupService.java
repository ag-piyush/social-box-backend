package com.socialbox.service;

import com.socialbox.dto.GroupDTO;
import com.socialbox.dto.InviteDTO;
import com.socialbox.model.Group;
import com.socialbox.model.GroupMovie;

import java.util.List;

public interface GroupService {
  List<GroupDTO> getAllGroups(List<String> groupIds);

  Group getGroup(String id);

  Group saveGroup(Group group);

  Group createGroup(Group group);

  List<GroupMovie> saveMovie(List<GroupMovie> groupMovies);

  InviteDTO sendInvite(String groupId, String userId);
}
