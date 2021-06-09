package com.socialbox.service.impl;

import com.google.common.collect.Lists;
import com.socialbox.dto.GroupDTO;
import com.socialbox.model.Group;
import com.socialbox.model.User;
import com.socialbox.repository.GroupRepository;
import com.socialbox.repository.UserRepository;
import com.socialbox.service.GroupService;
import com.socialbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final UserService userService;

  @Autowired
  public GroupServiceImpl(GroupRepository groupRepository, UserService userService) {
    this.groupRepository = groupRepository;
    this.userService = userService;
  }

  @Override
  public List<GroupDTO> getAllGroups(List<String> groupIds) {
    List<Group> groupList = Lists.newArrayList(this.groupRepository.findAllById(groupIds));
    List<GroupDTO> groupDTOList = new ArrayList<>();

    for (Group group : groupList) {
      GroupDTO groupDTO =
          GroupDTO.builder()
              .groupId(group.getGroupId())
              .groupName(group.getGroupName())
              .groupPhotoURL(group.getGroupPhotoURL())
              .memberCount(group.getMemberCount())
              .build();
      groupDTOList.add(groupDTO);
    }
    return groupDTOList;
  }

  @Override
  public Group getGroup(String id) {
    Optional<Group> groupOptional = this.groupRepository.findById(id);
    return groupOptional.orElse(null);
  }

  @Override
  public Group saveGroup(Group group) {
    return this.groupRepository.save(group);
  }

  @Override
  public Group setAdmin(Group group) {
    Group currentGroup = this.saveGroup(group);

    User currentUser = this.userService.getUserById(currentGroup.getGroupAdminId());
    if (currentUser.getGroupsId() == null) currentUser.setGroupsId(new HashSet<>());

    currentUser.getGroupsId().add(currentGroup.getGroupId());

    this.userService.saveUser(currentUser);

    currentGroup.setMemberCount(1);
    this.groupRepository.save(currentGroup);

    return currentGroup;
  }
}
